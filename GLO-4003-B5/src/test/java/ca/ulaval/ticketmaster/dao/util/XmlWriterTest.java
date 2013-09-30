/**
 * 
 * @author Mathieu Bolduc
 * 
 * */

package ca.ulaval.ticketmaster.dao.util;

import static org.junit.Assert.assertEquals;

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
import ca.ulaval.ticketmaster.model.Ticket.ticketType;

@RunWith(MockitoJUnitRunner.class)
public class XmlWriterTest {
	/*
	Event event = new Event(4,true,100,100,Event.Sport.Football,"M","Rouge et or","Vert et or","Qué¥•ec","Laval",new Date(),new Date());
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
	*/
	
	public static final String DATA_FILE="src/test/resources/testData.xml";
	private UUID eventId = UUID.fromString("f295a8f5-767c-4c16-993f-671f5f0a5ae1");
	private UUID ticketId1 = UUID.fromString("94b85660-524d-4a5e-8061-6f32dd8f0a06");
	private UUID ticketId2 = UUID.fromString("108417d5-d0b4-4005-a357-0cf82ebd066d");
	private UUID ticketId3 = UUID.fromString("fd371ceb-12fa-4700-870f-e5baaa45b725");
	
	@Test
	public void TestWriteNewEventToXml() throws ParseException{
		//Créer une event de test
		Event event = new Event(eventId);
		event.setSport(Event.Sport.Football);
		event.setGender("M");
		event.setLocation("Québec");
		event.setStadium("Bell");
		event.setHomeTeam("Rouge et or");
		event.setVisitorsTeam("Vert et or");
		Date date = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH).parse("30/09/2013");
		event.setDate(date);
		Date time = new SimpleDateFormat("H:mm").parse("13:30");
		event.setTime(time);
		event.setOpen(true);
		
		List<String> sectionList = new ArrayList<String>() {{add("A1");add("B6");add("F7");}};
		List<Ticket> ticketList = new ArrayList<Ticket>();
		for (int i = 1 ; i <= 2 ; i++){
			Ticket t;
			if(i == 1){
				t = new Ticket(ticketId1,event);
			}
			else {
				t = new Ticket(ticketId2,event);
			}
			t.setOwner("");
			t.setPrice(30.00);
			t.setResellprice(0);
			t.setType(ticketType.AdmissionGenerale);
			ticketList.add(t);
			event.setTicketsTotal(event.getTicketsTotal()+ 1);
			event.setTicketsAvailable(event.getTicketsAvailable()+ 1);
		}
		event.setSectionList(sectionList);
		event.setTicketList(ticketList);
		XmlWriter writer = new XmlWriter();
		writer.connect(DATA_FILE);
		writer.writeEvent(event);
		
		XmlReader reader = new XmlReader();
		reader.connect(DATA_FILE);
		Event readEvent = reader.loadEvent(eventId,true);
		assertEquals(event,readEvent);
		
		//Ticket add
		
		Ticket t = new Ticket(readEvent);
		t.setOwner("");
		t.setPrice(30.00);
		t.setResellprice(0);
		t.setType(ticketType.AdmissionGenerale);
		writer.writeTicketToEvent(eventId,t);
		Ticket readT = reader.loadTicket(eventId, ticketId3);
		assertEquals(t,readT);
	}
	
}
