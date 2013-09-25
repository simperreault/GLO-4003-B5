/**
 * 
 * @author CP
 *	La javadoc, la javadoc !
 */


package ca.ulaval.ticketmaster.dao.util;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import ca.ulaval.ticketmaster.model.Event;
import ca.ulaval.ticketmaster.model.Ticket;
import ca.ulaval.ticketmaster.model.User;
import ca.ulaval.ticketmaster.model.Event.Sport;
import ca.ulaval.ticketmaster.model.Ticket.ticketType;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class XmlReader {
	public static final String DATA_FILE = "src/main/resources/TestDataProjetUniversite.xml";
	private File fXmlFile;
	private Document doc;

	public XmlReader() {}

	private boolean connect() {
		try {
			fXmlFile = new File(DATA_FILE);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	/*
	 * read some basic info
	 *  0 is total of Events
	 *  1 is last Event id
	 *  2 is total of users
	 */
	public int[] readStartupInformation(){
		int output[] = new int[] {-1,-1,-1};
		if (!connect()) {
			System.out.println("Could not connect!");
			return output;
		}
		Element elElement = ((Element)doc.getElementsByTagName("EventList").item(0));
		output[0] = Integer.parseInt(elElement.getAttribute("total"));
		output[1] = Integer.parseInt(elElement.getAttribute("lastId"));
		output[2] = Integer.parseInt(((Element)doc.getElementsByTagName("UserList").item(0)).getAttribute("total"));
		return output;
	}
	
	/*
	 * @param[in] _andTickets if tickets are to be loaded or not in the ticket list
	 * Loads a single event with or without tickets if it exists, returns null otherwise
	 */
	public Event loadEvent(int _eventId, Boolean _andTickets){
		if (!connect()) {
			System.out.println("Could not connect!");
			return null;
		}

		NodeList EventNodeList = doc.getElementsByTagName("Event");
		for (int eventIter = 0; eventIter < EventNodeList.getLength(); eventIter++) {
			Element eElement = (Element)EventNodeList.item(eventIter);
			if(Integer.parseInt(eElement.getAttribute("id")) == _eventId ){ // if event exists
				// load event and returns it
				Event tempEvent = new Event(Integer.parseInt(eElement.getAttribute("id")));
				tempEvent.setOpen(Boolean.parseBoolean(eElement.getAttribute("open")));
				tempEvent.setTicketsTotal(Integer.parseInt(eElement.getAttribute("ticketsTotal")));
				tempEvent.setTicketsAvailable(Integer.parseInt(eElement.getAttribute("ticketsAvailable")));
				tempEvent.setSport(Sport.valueOf(((Element)eElement.getElementsByTagName("Sport").item(0)).getAttribute("name")));
				tempEvent.setGender(((Element)eElement.getElementsByTagName("Sport").item(0)).getAttribute("gender"));
				tempEvent.setHomeTeam(((Element)eElement.getElementsByTagName("Teams").item(0)).getAttribute("home"));
				tempEvent.setVisitorsTeam(((Element)eElement.getElementsByTagName("Teams").item(0)).getAttribute("visitors"));
				tempEvent.setLocation(((Element)eElement.getElementsByTagName("Location").item(0)).getAttribute("city"));
				tempEvent.setStadium(((Element)eElement.getElementsByTagName("Location").item(0)).getAttribute("stadium"));
				try {
					tempEvent.setDate( new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH).parse(((Element)eElement.getElementsByTagName("Location").item(0)).getAttribute("date")));
					tempEvent.setTime( new SimpleDateFormat("HH:mm").parse(((Element)eElement.getElementsByTagName("Location").item(0)).getAttribute("time")));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				//Load Section List
				NodeList sNodeList = ((Element)eElement.getElementsByTagName("SectionList").item(0)).getElementsByTagName("Section");
				for (int secIter = 0; secIter < sNodeList.getLength(); secIter++) {
					Element sElement = (Element)sNodeList.item(secIter);
					tempEvent.getSectionList().add(sElement.getTextContent());
				}

				//Load Ticket List
				if (_andTickets){
					NodeList tNodeList = ((Element)eElement.getElementsByTagName("TicketList").item(0)).getElementsByTagName("Ticket");
					Ticket tempTicket;
					for (int tickIter = 0; tickIter < tNodeList.getLength(); tickIter++) {
						Element tElement = (Element)tNodeList.item(tickIter);
						tempTicket = new Ticket(Integer.parseInt(tElement.getAttribute("id")),tempEvent);
						tempTicket.setType(ticketType.valueOf(tElement.getAttribute("type").toUpperCase()));
						tempTicket.setSection(tElement.getAttribute("section"));
						if (!tElement.getAttribute("seat").isEmpty())
							tempTicket.setSeat(tElement.getAttribute("seat"));
						tempTicket.setOwner(tElement.getAttribute("owner"));
						tempTicket.setPrice(Double.parseDouble(tElement.getAttribute("price")));
						tempTicket.setResellprice(Double.parseDouble(tElement.getAttribute("resellPrice")));
						tempEvent.getTicketList().add(tempTicket);	
					}
				}
				// return found event
				//System.out.println(tempEvent.toString());
				//System.out.println(tempEvent.getTicketList().toString());
				return tempEvent;
			}
		}
		// event not existing, return null
		return null;

	}
	
	
	/*
	 * Loads a single event with all tickets if it exists, returns null otherwise
	 */
	public Event loadEvent(int _eventId){
		return loadEvent(_eventId, true);
	}
	
	
	/*
	 * Loads a ticket if the event and the ticket exists, returns null otherwise
	 * Note that the Event linked to the ticket will only contain this ticket in his ticket list
	 */
	public Ticket loadTicket(int _eventId, int _ticketId){
		if (!connect()) {
			System.out.println("Could not connect!");
			return null;
		}
		NodeList EventNodeList = doc.getElementsByTagName("Event");
		for (int eventIter = 0; eventIter < EventNodeList.getLength(); eventIter++) {
			Element eElement = (Element)EventNodeList.item(eventIter);
			if(Integer.parseInt(eElement.getAttribute("id")) == _eventId ){ 
				// if event exists, check if ticket exists
				NodeList tNodeList = ((Element)eElement.getElementsByTagName("TicketList").item(0)).getElementsByTagName("Ticket");
				for (int tickIter = 0; tickIter < tNodeList.getLength(); tickIter++) {
					Element tElement = (Element)tNodeList.item(tickIter);
					if (Integer.parseInt(tElement.getAttribute("id")) == _ticketId){
						//ticket exists, load data
						//Event data to link to ticket
						Event tempEvent = new Event(Integer.parseInt(eElement.getAttribute("id")));
						tempEvent.setOpen(Boolean.parseBoolean(eElement.getAttribute("open")));
						tempEvent.setTicketsTotal(Integer.parseInt(eElement.getAttribute("ticketsTotal")));
						tempEvent.setTicketsAvailable(Integer.parseInt(eElement.getAttribute("ticketsAvailable")));
						tempEvent.setSport(Sport.valueOf(((Element)eElement.getElementsByTagName("Sport").item(0)).getAttribute("name")));
						tempEvent.setGender(((Element)eElement.getElementsByTagName("Sport").item(0)).getAttribute("gender"));
						tempEvent.setHomeTeam(((Element)eElement.getElementsByTagName("Teams").item(0)).getAttribute("home"));
						tempEvent.setVisitorsTeam(((Element)eElement.getElementsByTagName("Teams").item(0)).getAttribute("visitors"));
						tempEvent.setLocation(((Element)eElement.getElementsByTagName("Location").item(0)).getAttribute("city"));
						tempEvent.setStadium(((Element)eElement.getElementsByTagName("Location").item(0)).getAttribute("stadium"));
						try {
							tempEvent.setDate( new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH).parse(((Element)eElement.getElementsByTagName("Location").item(0)).getAttribute("date")));
							tempEvent.setTime( new SimpleDateFormat("HH:mm").parse(((Element)eElement.getElementsByTagName("Location").item(0)).getAttribute("time")));
						} catch (ParseException e) {
							e.printStackTrace();
						}
						//Load Section List
						NodeList sNodeList = ((Element)eElement.getElementsByTagName("SectionList").item(0)).getElementsByTagName("Section");
						for (int secIter = 0; secIter < sNodeList.getLength(); secIter++) {
							Element sElement = (Element)sNodeList.item(secIter);
							tempEvent.getSectionList().add(sElement.getTextContent());
						}
						// Single ticket data
						Ticket tempTicket = new Ticket(Integer.parseInt(tElement.getAttribute("id")),tempEvent);
						tempTicket.setType(ticketType.valueOf(tElement.getAttribute("type").toUpperCase()));
						tempTicket.setSection(tElement.getAttribute("section"));
						if (!tElement.getAttribute("seat").isEmpty())
							tempTicket.setSeat(tElement.getAttribute("seat"));
						tempTicket.setOwner(tElement.getAttribute("owner"));
						tempTicket.setPrice(Double.parseDouble(tElement.getAttribute("price")));
						tempTicket.setResellprice(Double.parseDouble(tElement.getAttribute("resellPrice")));
						tempEvent.getTicketList().add(tempTicket);
						//return the single ticket
						//System.out.println(tempTicket.toString());
						return tempTicket;
					}
				}
			}
		}
		//Event doesnt exist, return null
		return null;
	}



	public List<Event> loadEvents() {
		if (!connect()) {
			System.out.println("Could not connect!");
			return null;
		}
		List<Event> returnList = new ArrayList<Event>();
		NodeList EventNodeList = doc.getElementsByTagName("Event");
		Event tempEvent;
		for (int eventIter = 0; eventIter < EventNodeList.getLength(); eventIter++) {
			Node nNode = EventNodeList.item(eventIter);

			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;



				// Load Event Attributes
				tempEvent = new Event(Integer.parseInt(eElement.getAttribute("id")));
				tempEvent.setOpen(Boolean.parseBoolean(eElement.getAttribute("open")));
				tempEvent.setTicketsTotal(Integer.parseInt(eElement.getAttribute("ticketsTotal")));
				tempEvent.setTicketsAvailable(Integer.parseInt(eElement.getAttribute("ticketsAvailable")));
				tempEvent.setSport(Sport.valueOf(((Element)eElement.getElementsByTagName("Sport").item(0)).getAttribute("name")));
				tempEvent.setGender(((Element)eElement.getElementsByTagName("Sport").item(0)).getAttribute("gender"));
				tempEvent.setHomeTeam(((Element)eElement.getElementsByTagName("Teams").item(0)).getAttribute("home"));
				tempEvent.setVisitorsTeam(((Element)eElement.getElementsByTagName("Teams").item(0)).getAttribute("visitors"));
				tempEvent.setLocation(((Element)eElement.getElementsByTagName("Location").item(0)).getAttribute("city"));
				tempEvent.setStadium(((Element)eElement.getElementsByTagName("Location").item(0)).getAttribute("stadium"));
				try {
					tempEvent.setDate( new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH).parse(((Element)eElement.getElementsByTagName("Location").item(0)).getAttribute("date")));
					tempEvent.setTime( new SimpleDateFormat("HH:mm").parse(((Element)eElement.getElementsByTagName("Location").item(0)).getAttribute("time")));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				//Load Section List
				NodeList sNodeList = ((Element)eElement.getElementsByTagName("SectionList").item(0)).getElementsByTagName("Section");
				for (int secIter = 0; secIter < sNodeList.getLength(); secIter++) {
					Element sElement = (Element)sNodeList.item(secIter);
					tempEvent.getSectionList().add(sElement.getTextContent());
				}

				//Load Ticket List
				NodeList tNodeList = ((Element)eElement.getElementsByTagName("TicketList").item(0)).getElementsByTagName("Ticket");
				Ticket tempTicket;
				for (int tickIter = 0; tickIter < tNodeList.getLength(); tickIter++) {
					Element tElement = (Element)tNodeList.item(tickIter);
					tempTicket = new Ticket(Integer.parseInt(tElement.getAttribute("id")),tempEvent);
					tempTicket.setType(ticketType.valueOf(tElement.getAttribute("type").toUpperCase()));
					tempTicket.setSection(tElement.getAttribute("section"));
					if (!tElement.getAttribute("seat").isEmpty())
						tempTicket.setSeat(tElement.getAttribute("seat"));
					tempTicket.setOwner(tElement.getAttribute("owner"));
					tempTicket.setPrice(Double.parseDouble(tElement.getAttribute("price")));
					tempTicket.setResellprice(Double.parseDouble(tElement.getAttribute("resellPrice")));
					tempEvent.getTicketList().add(tempTicket);	
				}


				// Debug info
				/*System.out.println("Event id : " + eElement.getAttribute("id"));
				System.out.println("Event open : " + eElement.getAttribute("open"));
				System.out.println("Event ticketsTotal : " + eElement.getAttribute("ticketsTotal"));
				System.out.println("Event ticketsAvailable : " + eElement.getAttribute("ticketsAvailable"));
				System.out.println("Event Sport Name : " + ((Element)eElement.getElementsByTagName("Sport").item(0)).getAttribute("name"));
				System.out.println("Event Sport Gender : " + ((Element)eElement.getElementsByTagName("Sport").item(0)).getAttribute("gender"));
				System.out.println("Event Sport HomeTeam : " + ((Element)eElement.getElementsByTagName("Teams").item(0)).getAttribute("home"));
				System.out.println("Event Sport VisitorsTeam : " + ((Element)eElement.getElementsByTagName("Teams").item(0)).getAttribute("visitors"));
				System.out.println("Event Location City : " + ((Element)eElement.getElementsByTagName("Location").item(0)).getAttribute("city"));
				System.out.println("Event Location Stadium : " + ((Element)eElement.getElementsByTagName("Location").item(0)).getAttribute("stadium"));
				System.out.println("Event Date : " + ((Element)eElement.getElementsByTagName("Location").item(0)).getAttribute("date"));
				System.out.println("Event Date : " + tempEvent.getTime().toString());
				System.out.println("Event Section List" + tempEvent.getSectionList().toString());
				System.out.println("Event Ticket List" + tempEvent.getTicketList().toString());*/

				//add tempEvent to EventList
				returnList.add(tempEvent);
			}
		}
		return returnList;

	}

	/*
	 * Returns a user if it exists, null otherwise
	 */
	public User userAuthenticate(String _username){
		if (!connect()) {
			System.out.println("Could not connect!");
			return null;
		} 

		NodeList UserNodeList = doc.getElementsByTagName("User");
		for (int userIter = 0; userIter < UserNodeList.getLength(); userIter++) {
			//Search for wanted username
			Element uElement = (Element) UserNodeList.item(userIter);
			if ((uElement.getAttribute("username")).toLowerCase().equals(_username.toLowerCase())) {
				//load user and returns it
				User tempuser = new User(uElement.getAttribute("username"));
				tempuser.setPassword(((Element)uElement.getElementsByTagName("PersonalData").item(0)).getAttribute("password"));
				tempuser.setFirstName(((Element)uElement.getElementsByTagName("PersonalData").item(0)).getAttribute("firstName"));
				tempuser.setLastName(((Element)uElement.getElementsByTagName("PersonalData").item(0)).getAttribute("lastName"));
				tempuser.setEmail(((Element)uElement.getElementsByTagName("PersonalData").item(0)).getAttribute("email"));
				tempuser.setAccessLevel(((Element)uElement.getElementsByTagName("PersonalData").item(0)).getAttribute("firstName"));
				tempuser.setFavSport(((Element)uElement.getElementsByTagName("SearchPreferences").item(0)).getAttribute("sport"));
				tempuser.setFavGender(((Element)uElement.getElementsByTagName("SearchPreferences").item(0)).getAttribute("gender"));
				tempuser.setFavType(ticketType.valueOf(((Element)uElement.getElementsByTagName("SearchPreferences").item(0)).getAttribute("type").toUpperCase()));
				tempuser.setFavLocation(((Element)uElement.getElementsByTagName("SearchPreferences").item(0)).getAttribute("city"));

				NodeList tNodeList = ((Element)uElement.getElementsByTagName("UserTickets").item(0)).getElementsByTagName("Ticket");
				for (int tickIter = 0; tickIter < tNodeList.getLength(); tickIter++) {
					Element tElement = (Element)tNodeList.item(tickIter);
					tempuser.getUserTickets().add(new Pair(Integer.parseInt(tElement.getAttribute("matchId")), Integer.parseInt(tElement.getAttribute("ticketId"))));
				}
				//returns found user
				//System.out.println(tempuser.toString());
				return tempuser;
			}
		}

		//if not found yet, returns null
		return null;
	}

	public List<User> loadUsers() {
		if (!connect()) {
			System.out.println("Could not connect!");
			return null;
		} 

		NodeList UserNodeList = doc.getElementsByTagName("User");
		List<User> returnList = new ArrayList<User>();
		User tempuser;
		for (int userIter = 0; userIter < UserNodeList.getLength(); userIter++) {
			// Load User Attributes
			Element uElement = (Element) UserNodeList.item(userIter);
			tempuser = new User(uElement.getAttribute("username"));
			tempuser.setPassword(((Element)uElement.getElementsByTagName("PersonalData").item(0)).getAttribute("password"));
			tempuser.setFirstName(((Element)uElement.getElementsByTagName("PersonalData").item(0)).getAttribute("firstName"));
			tempuser.setLastName(((Element)uElement.getElementsByTagName("PersonalData").item(0)).getAttribute("lastName"));
			tempuser.setEmail(((Element)uElement.getElementsByTagName("PersonalData").item(0)).getAttribute("email"));
			tempuser.setAccessLevel(((Element)uElement.getElementsByTagName("PersonalData").item(0)).getAttribute("firstName"));
			tempuser.setFavSport(((Element)uElement.getElementsByTagName("SearchPreferences").item(0)).getAttribute("sport"));
			tempuser.setFavGender(((Element)uElement.getElementsByTagName("SearchPreferences").item(0)).getAttribute("gender"));
			tempuser.setFavType(ticketType.valueOf(((Element)uElement.getElementsByTagName("SearchPreferences").item(0)).getAttribute("type").toUpperCase()));
			tempuser.setFavLocation(((Element)uElement.getElementsByTagName("SearchPreferences").item(0)).getAttribute("city"));

			NodeList tNodeList = ((Element)uElement.getElementsByTagName("UserTickets").item(0)).getElementsByTagName("Ticket");
			for (int tickIter = 0; tickIter < tNodeList.getLength(); tickIter++) {
				Element tElement = (Element)tNodeList.item(tickIter);
				//tempuser.getUserTickets().put(Integer.parseInt(tElement.getAttribute("matchId")), Integer.parseInt(tElement.getAttribute("ticketId")));
			}
			returnList.add(tempuser);
		}
		return returnList;
	}

	public static void main(String[] args) {
		XmlReader xmlreader = new XmlReader();
		xmlreader.loadEvents();
		xmlreader.loadUsers();
		xmlreader.userAuthenticate("carloboutet");
		xmlreader.loadEvent(2);
		xmlreader.loadTicket(2, 4);
	}

}
