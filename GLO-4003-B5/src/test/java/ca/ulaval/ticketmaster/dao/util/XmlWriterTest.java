/**
 * 
 * @author Mathieu Bolduc
 * 
 * */

package ca.ulaval.ticketmaster.dao.util;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.ticketmaster.model.Event;
import ca.ulaval.ticketmaster.model.Ticket;
import ca.ulaval.ticketmaster.model.User;
import ca.ulaval.ticketmaster.model.User.AccessLevel;
import ca.ulaval.ticketmaster.model.enums.SportType;
import ca.ulaval.ticketmaster.model.enums.TicketType;

@RunWith(MockitoJUnitRunner.class)
public class XmlWriterTest {
    
    public static final String DATA_FILE = "src/test/resources/testDataXmlWriter.xml";
    //ces ids sont utilisées pour avoir des tests consistant pour garder le fichier xml intact
    private UUID eventId = UUID.fromString("f295a8f5-767c-4c16-993f-671f5f0a5ae1");
    private String username = "xmlTestsUser";

    @Test
    public void TestXmlWriter() {
    	XmlWriter w = new XmlWriter();
    	assertNotNull(w);
    }

    @Test
    public void TestConnectError() {
    	XmlWriter w = new XmlWriter();
    	assertEquals(w.connect("notrealfile"), false);
    }

    @Test
    public void TestConnect() {
    	XmlWriter w = new XmlWriter();
    	assertEquals(w.connect(DATA_FILE), true);
    }
    
    @Test
    public void TestCreateEditDeleteEventToXml() throws ParseException {
	
	    // Créer une event de test
	    Date date = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH).parse("30/09/2013");
	    Date time = new SimpleDateFormat("H:mm").parse("13:30");
	    Event event = EventFactory.CreateExistingEvent(eventId, true, SportType.FOOTBALL, "M", "Rouge et or", "Vert et or", "Québec", "Bell", date, time, 0, 0);
		
		List<String> sectionList = new ArrayList<String>() {
		    {
			add("A1");
			add("B6");
			add("F7");
		    }
		};
		
		List<Ticket> ticketList = new ArrayList<Ticket>();
		for (int i = 1; i <= 100; i++) {
		    Ticket t = TicketFactory.CreateTicket(event, TicketType.GENERAL, "", "", "", 30.00, 0);
		    ticketList.add(t);
		    event.setTicketsTotal(event.getTicketsTotal() + 1);
		    event.setTicketsAvailable(event.getTicketsAvailable() + 1);
		}
		
		event.setSectionList(sectionList);
		event.setTicketList(ticketList);
		XmlWriter writer = new XmlWriter();
		writer.connect(DATA_FILE);
		writer.writeEvent(event);
	
		XmlReader reader = new XmlReader();
		reader.connect(DATA_FILE);
		Event readEvent = reader.loadEvent(eventId, true);
		assertEquals(event.toString(), readEvent.toString());
	
		//Edit event test
		event.setGender("F");
		event.setSport(SportType.RUGBY);
		event.setHomeTeam("Test1");
		event.setVisitorsTeam("Test2");
		event.setLocation("Montréal");
		event.setStadium("Stadium");
		writer.modifyEvent(event);
		
		reader.connect(DATA_FILE);
		Event editedEvent = reader.loadEvent(eventId, true);
		assertEquals(event.toString(), editedEvent.toString());
		
		//Delete event test
		writer.deleteEvent(eventId);
		
		reader.connect(DATA_FILE);
		Event deletedEvent = reader.loadEvent(eventId, true);
		assertNull(deletedEvent);
		
		// Ticket add
		/*
		Ticket t = TicketFactory.CreateTicket(readEvent, TicketType.GENERAL, "", "", "", 30.00, 0);
		writer.writeTicketToEvent(eventId, t);
		Ticket readT = reader.loadTicket(eventId, ticketId3);
		assertEquals(t, readT);*/
    }
    
    @Test
    public void TestCreateEditDeleteUserToXml() {
    	//test create user
    	User user = new User(username,"test", "Bob", "Dubois", "testbillets@live.ca",AccessLevel.User,  SportType.FOOTBALL.toString(), "M", TicketType.GENERAL ,"Québec");
    	List<Pair<Integer,Integer>> userTickets = new ArrayList<Pair<Integer,Integer>>(); 
    	userTickets.add(new Pair<Integer,Integer>(1,1));
    	userTickets.add(new Pair<Integer,Integer>(1,8)); 
    	userTickets.add(new Pair<Integer,Integer>(2,5)); 
    	userTickets.add(new Pair<Integer,Integer>(2,14));
    	userTickets.add(new Pair<Integer,Integer>(3,58)); 
    	user.setUserTickets(userTickets);
    	
    	XmlWriter writer = new XmlWriter();
		writer.connect(DATA_FILE);
		writer.writeUser(user);
		
		XmlReader reader = new XmlReader();
		reader.connect(DATA_FILE);
		User readUser = reader.userAuthenticate(username);
		
		assertEquals(user.toString(), readUser.toString());
		
		//test edit user
		user.setEmail("test@lol.com");
		user.setFirstName("Jon");
		user.setLastName("Doe");
		user.setFavLocation("Montréal");
    	userTickets.add(new Pair<Integer,Integer>(18,19));
    	user.setUserTickets(userTickets);
		writer.modifyUser(user);
		
		reader.connect(DATA_FILE);
		User editedUser = reader.userAuthenticate(username);
		assertEquals(user.toString(), editedUser.toString());
		
		//test delete user
		writer.deleteUser(username);
		reader.connect(DATA_FILE);
		User deletedUser = reader.userAuthenticate(username);
		assertNull(deletedUser);
    }
    
    @Test
    public void TestCreateEditDeleteTicketsToXml() throws ParseException {
    	// Créer une event de test
        Date date = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH).parse("30/09/2013");
        Date time = new SimpleDateFormat("H:mm").parse("13:30");
        Event event = EventFactory.CreateExistingEvent(eventId, true, SportType.FOOTBALL, "M", "Rouge et or", "Vert et or", "Québec", "Bell", date, time, 0, 0);
       
        XmlWriter writer = new XmlWriter();
		writer.connect(DATA_FILE);
		writer.writeEvent(event);
		
		Ticket tTest = TicketFactory.CreateTicket(event, TicketType.RESERVED, "A", "16", "", 30.00, 0);
		event.addTicketToList(tTest);
		//test ajouter 1 ticket
		writer.writeTicketToEvent(eventId, tTest);
		
		XmlReader reader = new XmlReader();
		reader.connect(DATA_FILE);
		Event readEvent = reader.loadEvent(eventId, true);
		assertEquals(event.toString(), readEvent.toString());
		assertEquals(event.getTicketList().toString() , readEvent.getTicketList().toString());
		
		// test edit ticket
		event.removeTicketFromList(tTest.getId());
		tTest.setResellprice(90.00);
		tTest.setOwner(username);
		event.addTicketToList(tTest);
		writer.modifyTicket(tTest);
		reader.connect(DATA_FILE);
		Ticket ticketModified = reader.loadTicket(eventId, tTest.getId());
		assertEquals(ticketModified.toString(), ticketModified.toString());
		
		
		//test supprimmer ticket
		event.removeTicketFromList(tTest.getId());
		writer.deleteTicket(eventId, tTest.getId());
		reader.connect(DATA_FILE);
		Event deletedTicketEvent = reader.loadEvent(eventId, true);
		Ticket ticketRead = reader.loadTicket(eventId, tTest.getId());
		assertNull(ticketRead);
		assertEquals(event.toString(), deletedTicketEvent.toString());
		assertEquals(event.getTicketList().toString() , deletedTicketEvent.getTicketList().toString());
	
		//test ajouter plusieurs tickets
		
		List<Ticket> ticketList = new ArrayList<Ticket>();
		for (int i = 1; i <= 100; i++) {
		    Ticket t = TicketFactory.CreateTicket(event, TicketType.GENERAL, "", "", "", 30.00, 0);
		    ticketList.add(t);
		    event.setTicketsTotal(event.getTicketsTotal() + 1);
		    event.setTicketsAvailable(event.getTicketsAvailable() + 1);
		}
		event.setTicketList(ticketList);
		writer.writeTicketsToEvent(eventId, ticketList);
		
		reader.connect(DATA_FILE);
		Event multipleTicketsEvent = reader.loadEvent(eventId, true);
		assertEquals(event.toString(), multipleTicketsEvent.toString());
		assertEquals(event.getTicketList().toString() , multipleTicketsEvent.getTicketList().toString());
		
		//supprimmer tous les tickets + event pour faire le menage
		writer.deleteEvent(eventId);
		reader.connect(DATA_FILE);
		Event deletedEvent = reader.loadEvent(eventId, true);
		assertNull(deletedEvent);
    }
    
 
}
