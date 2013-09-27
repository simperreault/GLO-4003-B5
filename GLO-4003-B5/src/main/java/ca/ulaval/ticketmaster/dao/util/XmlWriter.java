/**
 * 
 * @author Mathieu Bolduc
 *	
 */

package ca.ulaval.ticketmaster.dao.util;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import ca.ulaval.ticketmaster.model.Event;
import ca.ulaval.ticketmaster.model.Ticket;
import ca.ulaval.ticketmaster.model.User;
import ca.ulaval.ticketmaster.model.Event.Sport;
import ca.ulaval.ticketmaster.model.Ticket.ticketType;

public class XmlWriter {
public static final String DATA_FILE="src/main/resources/TestDataProjetUniversite.xml";
private File xmlFile;
private Document xmlDoc;

	public XmlWriter(){
		connect(DATA_FILE);
	}
	
	public boolean connect(String _filePath){
		xmlFile = new File(_filePath);
		if (xmlFile.canRead() & xmlFile.canWrite()){
			return true;
		}
		return false;
	}
	
	public static void main(String [] args)
	{
		Event event = new Event(4,true,100,100,Event.Sport.Football,"M","Rouge et or","Vert et or","Québec","Laval",new Date(),new Date());
		List<String> sectionList = new ArrayList<String>() {{add("A1");add("B6");add("F7");}};
		List<Ticket> ticketList = new ArrayList<Ticket>();
		for (int i = 1 ; i <= 100 ; i++){
			Ticket t = new Ticket(i,event);
			t.setOwner("");
			t.setPrice(80.69);
			t.setResellprice(0);
			t.setSection("L01");
			t.setSeat("90");
			t.setType(ticketType.SEASON);
			ticketList.add(t);
		}
		event.setSectionList(sectionList);
		event.setTicketList(ticketList);
		User user = new User("BobTheMaster","lolpass","Bob","Desbois","Bob123@hotmail.com",User.AccessLevel.User,"Basketball","M",ticketType.GENERAL,"Sherbrooke");
		List<Pair<Integer,Integer>> userTickets = new ArrayList<Pair<Integer, Integer>>();
		userTickets.add(new Pair<Integer,Integer>(1,1));
		userTickets.add(new Pair<Integer,Integer>(1,8));
		userTickets.add(new Pair<Integer,Integer>(2,5));
		userTickets.add(new Pair<Integer,Integer>(2,14));
		userTickets.add(new Pair<Integer,Integer>(3,58));
		user.setUserTickets(userTickets);
		XmlWriter writer = new XmlWriter();
		//writer.writeEvent(event);
		//writer.writeUser(user);
		//List<Pair<Integer,Integer>> userTickets2 = new ArrayList<Pair<Integer, Integer>>();
		//userTickets2.add(new Pair<Integer,Integer>(6,7));
		//user.setEmail("wowttttttbo@barnak.ca");
		//user.setPassword("bro");
		//user.setUserTickets(userTickets2);
		//writer.modifyUser(user);
		//writer.writeTicketsToEvent(2, ticketList);
		//writer.deleteUser("CarloBoutet");
		//writer.deleteEvent(2);
		//writer.deleteTicket(1, 1);
		//Ticket test = event.getTicketList().get(0);
		//test.setOwner("carloBoutet");
		//test.setResellprice(50);
		//writer.modifyTicket(test);
		//event.setGender("F");
		//event.setSport("Basketball");
		//event.setStadium("Honco");
		//writer.modifyEvent(event);
	}
	
	private void writeTicket(Ticket _ticket, Element _ticketListElement){
		Element ticketElement = xmlDoc.createElement("Ticket");
		ticketElement.setAttribute("id", Integer.toString(_ticket.getId()));
		ticketElement.setAttribute("type", _ticket.getType().name());
		ticketElement.setAttribute("section", _ticket.getSection());
		ticketElement.setAttribute("seat", _ticket.getSeat());
		ticketElement.setAttribute("owner", _ticket.getOwner());
		ticketElement.setAttribute("price", Double.toString(_ticket.getPrice()));
		ticketElement.setAttribute("resellPrice", Double.toString(_ticket.getResellprice()));
		_ticketListElement.appendChild(ticketElement);
	}
	
	public boolean writeTicketToEvent(int _eventId, Ticket _ticket){
		List<Ticket> list = new ArrayList<Ticket>();
		list.add(_ticket);
		return writeTicketsToEvent(_eventId , list);
	}
	
	public boolean writeTicketsToEvent(int _eventId, List<Ticket> _ticketsToAdd){
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		dbFactory.setIgnoringElementContentWhitespace(true);
		try {
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			//xmlDoc = dBuilder.newDocument();
			xmlDoc = dBuilder.parse(xmlFile);
			//Trouver l'emplacement pour ajouter les tickets
			Element myEventElement = findEvent(_eventId);
			if(myEventElement == null){
				throw new Exception("Event non existant dans le fichier");
			}
			//on a trouvï¿½un event valide alors on ajoute les billets
			Element ticketListElement = (Element) myEventElement.getElementsByTagName("TicketList").item(0);
			for(Iterator<Ticket> it = _ticketsToAdd.iterator(); it.hasNext();)
			{
				writeTicket(it.next(),ticketListElement);
			}
			//changer le nombre total et disponible de billets dans le fichier
			updateTicketCounts(myEventElement,_ticketsToAdd.size());
			//é¦—rire le contenu au fichier xml physique
			saveDataToFile();
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	private boolean saveDataToFile(){
		//é¦—rire le contenu au fichier xml physique
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer;
		try {
			transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(xmlDoc);
			StreamResult result = new StreamResult(xmlFile);
			//proprié¨�é¦¥s pour l'indentation du fichier
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			
			//transformer le stream en é¦—riture sur le fichier
			transformer.transform(source, result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean writeEvent(Event _event){
	
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		try {
			
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			//xmlDoc = dBuilder.newDocument();
			xmlDoc = dBuilder.parse(xmlFile);
			//Trouver l'emplacement pour ajouter l'event
			Element rootElementList = (Element)(xmlDoc.getElementsByTagName("EventList").item(0));
			//Cré¦¥r le header de l'é¨…é§­ement
			Element rootEventElement = xmlDoc.createElement("Event");
			rootEventElement.setAttribute("id", Integer.toString(_event.getId()));
			rootEventElement.setAttribute("open", Boolean.toString(_event.isOpen()));
			rootEventElement.setAttribute("ticketsTotal", Integer.toString(_event.getTicketsTotal()));
			rootEventElement.setAttribute("ticketsAvailable",Integer.toString(_event.getTicketsAvailable()));
			
			//Cré¦¥r le contenu de l'event
			Element sectionListElement = xmlDoc.createElement("SectionList");
			//liste de sections
			for(Iterator<String> it = _event.getSectionList().iterator(); it.hasNext();)
			{
				Element sectionElement = xmlDoc.createElement("Section");
				sectionElement.appendChild(xmlDoc.createTextNode(it.next()));
				sectionListElement.appendChild(sectionElement);
			}
			rootEventElement.appendChild(sectionListElement);
			//sport
			Element sportElement = xmlDoc.createElement("Sport");
			sportElement.setAttribute("name", _event.getSport().toString());
			sportElement.setAttribute("gender", _event.getGender());
			rootEventElement.appendChild(sportElement);
			//teams 
			Element teamElement = xmlDoc.createElement("Teams");
			teamElement.setAttribute("home", _event.getHomeTeam());
			teamElement.setAttribute("visitors", _event.getVisitorsTeam());
			rootEventElement.appendChild(teamElement);
			//location
			Element locationElement = xmlDoc.createElement("Location");
			locationElement.setAttribute("city", _event.getLocation());
			locationElement.setAttribute("stadium", _event.getStadium());
			locationElement.setAttribute("date", new SimpleDateFormat("dd/MM/yyyy").format(_event.getDate()));
			locationElement.setAttribute("time", new SimpleDateFormat("H:mm").format(_event.getTime()));
			rootEventElement.appendChild(locationElement);
			//TicketList
			Element ticketListElement = xmlDoc.createElement("TicketList");
			for(Iterator<Ticket> it = _event.getTicketList().iterator(); it.hasNext();)
			{
				writeTicket(it.next(),ticketListElement);
			}
			rootEventElement.appendChild(ticketListElement);
			//ajouter l,event ï¿½la liste
			rootElementList.appendChild(rootEventElement);
			
			//update du nombre total d'event et du dernier id
			int newTotal = Integer.parseInt(rootElementList.getAttribute("total")) + 1;
			rootElementList.setAttribute("total",Integer.toString(newTotal));
			rootElementList.setAttribute("lastId",Integer.toString(_event.getId()));
			//é¦—rire le contenu au fichier xml physique
			saveDataToFile();
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public boolean writeUser(User _user){
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			xmlDoc = dBuilder.parse(xmlFile);
			//Trouver l'emplacement pour ajouter le user
			Element rootElementList = (Element)(xmlDoc.getElementsByTagName("UserList").item(0));
			//Cré¦¥r le header de l'é¨…é§­ement
			Element rootUserElement = xmlDoc.createElement("User");
			rootUserElement.setAttribute("username", _user.getUsername());
			rootElementList.appendChild(rootUserElement);
			//Cré¦¥r le contenu du user
			//personal data
			Element dataElement = xmlDoc.createElement("PersonalData");
			dataElement.setAttribute("accessLevel", _user.getAccessLevel().toString());
			dataElement.setAttribute("email", _user.getEmail());
			dataElement.setAttribute("firstname", _user.getFirstName());
			dataElement.setAttribute("lastName", _user.getLastName());
			dataElement.setAttribute("password", _user.getPassword());
			rootUserElement.appendChild(dataElement);
			Element userTicketListElement = xmlDoc.createElement("UserTickets");
			//liste des tickets appartenant au user
			for(Iterator<Pair<Integer, Integer>> it = _user.getUserTickets().iterator(); it.hasNext();)
			{
				Pair<Integer, Integer> pair = it.next();
				Element ticketElement = xmlDoc.createElement("Ticket");
				ticketElement.setAttribute("matchId",Integer.toString(pair.getLeft()));
				ticketElement.setAttribute("ticketId",Integer.toString(pair.getRight()));
				userTicketListElement.appendChild(ticketElement);
			}
			rootUserElement.appendChild(userTicketListElement);
			//search preferences
			Element searchElement = xmlDoc.createElement("SearchPreferences");
			searchElement.setAttribute("city", _user.getFavLocation());
			searchElement.setAttribute("gender", _user.getFavGender());
			searchElement.setAttribute("sport", _user.getFavSport());
			searchElement.setAttribute("type", _user.getFavType().toString());
			rootUserElement.appendChild(searchElement);
			//update du nombre total de user
			int newTotal = Integer.parseInt(rootElementList.getAttribute("total")) + 1;
			rootElementList.setAttribute("total",Integer.toString(newTotal));
			//é¦—rire le contenu au fichier xml physique
			saveDataToFile();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	private void removeAllNodes(Node _node) 
    {
		NodeList nl = _node.getChildNodes();
        while(nl.getLength() > 0){
        	Node n = nl.item(0);
            if(n.hasChildNodes()){
              removeAllNodes(n);
              _node.removeChild(n);
            }
            else
              _node.removeChild(n);
        }
    }
	
	public boolean modifyEvent(Event _event){
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			xmlDoc = dBuilder.parse(xmlFile);
			//Trouver l'event que l'on cherche
			Element myEventElement = findEvent(_event.getId());
			if(myEventElement == null){
				throw new Exception("Event non existant dans le fichier");
			}
			//Modifier le contenu de l'event
			myEventElement.setAttribute("open", Boolean.toString(_event.isOpen()));
			Element sectionListElement = (Element)(myEventElement.getElementsByTagName("SectionList").item(0));
			//liste de sections
			//enlever les anicennes sections et remplace par les nouvelles
			removeAllNodes((Node)sectionListElement);
			for(Iterator<String> it = _event.getSectionList().iterator(); it.hasNext();)
			{
				Element sectionElement = xmlDoc.createElement("Section");
				sectionElement.appendChild(xmlDoc.createTextNode(it.next()));
				sectionListElement.appendChild(sectionElement);
			}
			//sport
			Element sportElement = (Element)(myEventElement.getElementsByTagName("Sport").item(0));
			sportElement.setAttribute("name", _event.getSport().toString());
			sportElement.setAttribute("gender", _event.getGender());
			//teams 
			Element teamElement = (Element)(myEventElement.getElementsByTagName("Teams").item(0));
			teamElement.setAttribute("home", _event.getHomeTeam());
			teamElement.setAttribute("visitors", _event.getVisitorsTeam());
			//location
			Element locationElement = (Element)(myEventElement.getElementsByTagName("Location").item(0));
			locationElement.setAttribute("city", _event.getLocation());
			locationElement.setAttribute("stadium", _event.getStadium());
			locationElement.setAttribute("date", new SimpleDateFormat("dd/MM/yyyy").format(_event.getDate()));
			locationElement.setAttribute("time", new SimpleDateFormat("H:mm").format(_event.getTime()));
			//é¦—rire le contenu au fichier xml physique
			saveDataToFile();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean modifyTicket(Ticket _ticket){
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			xmlDoc = dBuilder.parse(xmlFile);
			//Trouver l'event que l'on cherche
			Element myEventElement = findEvent(_ticket.getEvent().getId());
			if(myEventElement == null){
				throw new Exception("Event non existant dans le fichier");
			}
			//Trouver le ticket ï¿½l'inté§»ieur de l'event
			Element myTicketElement = findTicketInEvent(myEventElement,_ticket.getId());
			if(myTicketElement == null){
				throw new Exception("Ticket non existant dans le fichier");
			}
			myTicketElement.setAttribute("type", _ticket.getType().name());
			myTicketElement.setAttribute("section", _ticket.getSection());
			myTicketElement.setAttribute("seat", _ticket.getSeat());
			myTicketElement.setAttribute("owner", _ticket.getOwner());
			myTicketElement.setAttribute("price", Double.toString(_ticket.getPrice()));
			myTicketElement.setAttribute("resellPrice", Double.toString(_ticket.getResellprice()));
			//mofifer le count des tickets
			updateTicketCounts(myEventElement,_ticket.getEvent());
			//é¦—rire le contenu au fichier xml physique
			saveDataToFile();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean modifyUser(User _user){
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			xmlDoc = dBuilder.parse(xmlFile);
			//Trouver le user que l'on cherche
			Element myUserElement = findUser(_user.getUsername());
			if(myUserElement == null){
				throw new Exception("User non existant dans le fichier");
			}
			//Modifier le contenu du user
			//personal data
			Element dataElement = (Element)(myUserElement.getElementsByTagName("PersonalData").item(0));
			dataElement.setAttribute("accessLevel", _user.getAccessLevel().toString());
			dataElement.setAttribute("email", _user.getEmail());
			dataElement.setAttribute("firstname", _user.getFirstName());
			dataElement.setAttribute("lastName", _user.getLastName());
			dataElement.setAttribute("password", _user.getPassword());
			
			Element userTicketListElement = (Element)(myUserElement.getElementsByTagName("UserTickets").item(0));
			if(userTicketListElement.hasChildNodes()){
				removeAllNodes((Node)userTicketListElement);
			}
			//ajouter la liste des tickets appartenant au user
			for(Iterator<Pair<Integer, Integer>> it = _user.getUserTickets().iterator(); it.hasNext();)
			{
				Pair<Integer, Integer> pair = it.next();
				Element ticketElement = xmlDoc.createElement("Ticket");
				ticketElement.setAttribute("matchId",Integer.toString(pair.getLeft()));
				ticketElement.setAttribute("ticketId",Integer.toString(pair.getRight()));
				userTicketListElement.appendChild(ticketElement);
			}
			//search preferences
			Element searchElement = (Element)(myUserElement.getElementsByTagName("SearchPreferences").item(0));
			searchElement.setAttribute("city", _user.getFavLocation());
			searchElement.setAttribute("gender", _user.getFavGender());
			searchElement.setAttribute("sport", _user.getFavSport());
			searchElement.setAttribute("type", _user.getFavType().toString());
			//é¦—rire le contenu au fichier xml physique
			saveDataToFile();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean deleteUser(String _username){
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			xmlDoc = dBuilder.parse(xmlFile);
			//Trouver le user que l'on cherche
			Element myUserElement = findUser(_username);
			if(myUserElement == null){
				throw new Exception("User non existant dans le fichier");
			}
			//Supprimmer le user
			Node userListNode = xmlDoc.getElementsByTagName("UserList").item(0);
			removeEmptyLines(myUserElement);
			userListNode.removeChild((Node)myUserElement);
			//update du nombre total de user
			int newTotal = Integer.parseInt(((Element)userListNode).getAttribute("total")) - 1;
			((Element)userListNode).setAttribute("total",Integer.toString(newTotal));
			//é¦—rire le contenu au fichier xml physique
			saveDataToFile();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean deleteEvent(int _eventId){
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			xmlDoc = dBuilder.parse(xmlFile);
			//Trouver l'event que l'on cherche
			Element myEventElement = findEvent(_eventId);
			if(myEventElement == null){
				throw new Exception("Event non existant dans le fichier");
			}
			//Supprimmer l'event
			Node eventListNode = xmlDoc.getElementsByTagName("EventList").item(0);
			removeEmptyLines(myEventElement);
			eventListNode.removeChild((Node)myEventElement);
			//update du nombre total d'event
			int newTotal = Integer.parseInt(((Element)eventListNode).getAttribute("total")) - 1;
			((Element)eventListNode).setAttribute("total",Integer.toString(newTotal));
			//é¦—rire le contenu au fichier xml physique
			saveDataToFile();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean deleteTicket(int _eventId, int _ticketId){
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			xmlDoc = dBuilder.parse(xmlFile);
			//Trouver l'event que l'on cherche
			Element myEventElement = findEvent(_eventId);
			if(myEventElement == null){
				throw new Exception("Event non existant dans le fichier");
			}
			//Trouver le ticket ï¿½l'inté§»ieur de l'event
			Element myTicketElement = findTicketInEvent(myEventElement,_ticketId);
			if(myTicketElement == null){
				throw new Exception("Ticket non existant dans le fichier");
			}
			Node ticketListNode = myEventElement.getElementsByTagName("TicketList").item(0);
			removeEmptyLines(myTicketElement);
			ticketListNode.removeChild((Node)myTicketElement);
			//changer le nombre total et disponible de billets dans le fichier
			updateTicketCounts(myEventElement,-1);
			//é¦—rire le contenu au fichier xml physique
			saveDataToFile();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	private Element findUser(String _username){
		//Trouver le user que l'on cherche
		Element rootElementList = (Element)(xmlDoc.getElementsByTagName("UserList").item(0));
		NodeList userNodeList = rootElementList.getElementsByTagName("User");
		Element myUserElement = null;
		int userIter = 0;
		while (userIter < userNodeList.getLength()) {
			Element elem = (Element)userNodeList.item(userIter);
			if( elem.getAttribute("username").equals(_username)){
				myUserElement = elem;
			}
			userIter++;
		}
		return myUserElement;
	}
	
	private Element findEvent(int _eventId){
		Element rootElementList = (Element)(xmlDoc.getElementsByTagName("EventList").item(0));
		NodeList eventNodeList = rootElementList.getElementsByTagName("Event");
		Element myEventElement = null;
		int eventIter = 0;
		while (eventIter < eventNodeList.getLength()) {
			Element elem = (Element)eventNodeList.item(eventIter);
			if( Integer.parseInt(elem.getAttribute("id")) == _eventId ){
				myEventElement = elem;
			}
			eventIter++;
		}
		return myEventElement;
	}
	
	private Element findTicketInEvent(Element _event, int _ticketId){
		Element rootTicketList = (Element)(_event.getElementsByTagName("TicketList").item(0));
		NodeList ticketNodeList = rootTicketList.getElementsByTagName("Ticket");
		Element myTicketElement = null;
		int ticketIter = 0;
		while (ticketIter < ticketNodeList.getLength()) {
			Element elem = (Element)ticketNodeList.item(ticketIter);
			if( Integer.parseInt(elem.getAttribute("id")) == _ticketId ){
				myTicketElement = elem;
			}
			ticketIter++;
		}
		return myTicketElement;
	}
	
	private void updateTicketCounts(Element _event , int _value){
		int tickeTotalValue = Integer.parseInt(_event.getAttribute("ticketsTotal")) + _value;
		int ticketAvailableValue = Integer.parseInt(_event.getAttribute("ticketsAvailable")) + _value;
		_event.setAttribute("ticketsTotal", Integer.toString(tickeTotalValue));
		_event.setAttribute("ticketsAvailable", Integer.toString(ticketAvailableValue));
	}
	
	private void updateTicketCounts(Element _elemEvent , Event _event){
		int tickeTotalValue = _event.getTicketsTotal();
		int ticketAvailableValue = _event.getTicketsAvailable();
		_elemEvent.setAttribute("ticketsTotal", Integer.toString(tickeTotalValue));
		_elemEvent.setAttribute("ticketsAvailable", Integer.toString(ticketAvailableValue));
	}
	
	private void removeEmptyLines(Element _curElement){
		Node prev = _curElement.getPreviousSibling();
		 if (prev != null && 
		     prev.getNodeType() == Node.TEXT_NODE &&
		     prev.getNodeValue().trim().length() == 0) {
			 _curElement.getParentNode().removeChild(prev);
		 }
	}
}
