/**
 * 
 * @author Mathieu Bolduc
 *	
 */

package ca.ulaval.billet.dataUtil;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import ca.ulaval.billet.model.Event;
import ca.ulaval.billet.model.Ticket;
import ca.ulaval.billet.model.Event.Sport;
import ca.ulaval.billet.model.Ticket.ticketType;

public class XmlWriter {
public static final String DATA_FILE="src/main/resources/TestDataProjetUniversite.xml";
private File xmlFile;
private Document xmlDoc;

	public XmlWriter(){
		
	}
	
	private boolean connect(){
		xmlFile = new File(DATA_FILE);
		if (xmlFile.canRead() & xmlFile.canWrite()){
			return true;
		}
		return false;
	}
	
	public static void main(String [] args)
	{
		Event event = new Event(3,true,100,100,Sport.Football,"M","Rouge et or","Vert et or","Québec","Laval",new Date(),new Date());
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
		XmlWriter writer = new XmlWriter();
		writer.connect();
		//writer.writeEvent(event);
		//writer.writeTicketsToEvent(2, ticketList);
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
	
	public boolean writeTicketsToEvent(int _eventId, List<Ticket> _ticketsToAdd){
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			//xmlDoc = dBuilder.newDocument();
			xmlDoc = dBuilder.parse(xmlFile);
			//Trouver l'emplacement pour ajouter les tickets
			NodeList EventNodeList = xmlDoc.getElementsByTagName("Event");
			Element myEventElement = null;
			int eventIter = 0;
			while (eventIter < EventNodeList.getLength()) {
				Element elem = (Element)EventNodeList.item(eventIter);
				if( Integer.parseInt(elem.getAttribute("id")) == _eventId ){
					myEventElement = elem;
				}
				eventIter++;
			}
			if(myEventElement == null){
				throw new Exception("Event non existant dans le fichier");
			}
			//on a trouvé un event valide alors on ajoute les billets
			Element ticketListElement = (Element) myEventElement.getElementsByTagName("TicketList").item(0);
			for(Iterator<Ticket> it = _ticketsToAdd.iterator(); it.hasNext();)
			{
				writeTicket(it.next(),ticketListElement);
			}
			//changer le nombre total et disponible de billets dans le fichier
			int tickeTotalValue = Integer.parseInt(myEventElement.getAttribute("ticketsTotal")) + _ticketsToAdd.size();
			int ticketAvailableValue = Integer.parseInt(myEventElement.getAttribute("ticketsAvailable")) + _ticketsToAdd.size();
			myEventElement.setAttribute("ticketsTotal", Integer.toString(tickeTotalValue));
			myEventElement.setAttribute("ticketsAvailable", Integer.toString(ticketAvailableValue));
			//écrire le contenu au fichier xml physique
			saveDataToFile();
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	private boolean saveDataToFile(){
		//écrire le contenu au fichier xml physique
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer;
		try {
			transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(xmlDoc);
			StreamResult result = new StreamResult(xmlFile);
			//propriétées pour l'indentation du fichier
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			//transformer le stream en écriture sur le fichier
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
			//Créer le header de l'événement
			Element rootEventElement = xmlDoc.createElement("Event");
			rootEventElement.setAttribute("id", Integer.toString(_event.getId()));
			rootEventElement.setAttribute("open", Boolean.toString(_event.isOpen()));
			rootEventElement.setAttribute("ticketsTotal", Integer.toString(_event.getTicketsTotal()));
			rootEventElement.setAttribute("ticketsAvailable",Integer.toString(_event.getTicketsAvailable()));
			
			//Créer le contenu de l'event
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
			//TODO Boucle de gestion
			for(Iterator<Ticket> it = _event.getTicketList().iterator(); it.hasNext();)
			{
				writeTicket(it.next(),ticketListElement);
			}
			rootEventElement.appendChild(ticketListElement);
			//ajouter l,event à la liste
			rootElementList.appendChild(rootEventElement);
			//écrire le contenu au fichier xml physique
			saveDataToFile();
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
}
