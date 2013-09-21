package ca.ulaval.billet.dataUtil;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import ca.ulaval.billet.model.Event;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class XmlReader {
	public static final String DATA_FILE = "src/main/resources/TestDataProjetUniversite.xml";
	private File fXmlFile;
	private Document doc;

	public XmlReader() {

	}

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

	private boolean disconnect() {
		return false;
	}

	public void loadEvents() {
		if (!connect()) {
			System.out.println("Could not connect!");
			return;
		} else {
			System.out.println("File found!");
		}
		System.out.println("Root element :"	+ doc.getDocumentElement().getNodeName());
		NodeList EventNodeList = doc.getElementsByTagName("Event");
		Event tempEvent;
		for (int eventIter = 0; eventIter < EventNodeList.getLength(); eventIter++) {
			Node nNode = EventNodeList.item(eventIter);
	 
			System.out.println("\nCurrent Element :" + nNode.getNodeName());
	 
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				
				
				
				// Load Event Attributes
				tempEvent = new Event(Integer.parseInt(eElement.getAttribute("id")));
				tempEvent.setOpen(Boolean.parseBoolean(eElement.getAttribute("open")));
				tempEvent.setTicketsTotal(Integer.parseInt(eElement.getAttribute("ticketsTotal")));
				tempEvent.setTicketsAvailable(Integer.parseInt(eElement.getAttribute("ticketsAvailable")));
				tempEvent.setSport(((Element)eElement.getElementsByTagName("Sport").item(0)).getAttribute("name"));
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
				
				System.out.println("Event id : " + eElement.getAttribute("id"));
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



				
			}
		}
		
	}

	public static void main(String[] args) {
		XmlReader xmlreader = new XmlReader();
		xmlreader.loadEvents();

	}

}
