/**
 * 
 * @author Mathieu Bolduc
 * 
 * */

package ca.ulaval.ticketmaster.dao.util;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import ca.ulaval.ticketmaster.model.Event;
import ca.ulaval.ticketmaster.model.Ticket;
import ca.ulaval.ticketmaster.model.User;
import ca.ulaval.ticketmaster.model.Ticket.ticketType;

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
	
	@Test
	public void TestWriteNewEventToXml(){
		//Créer une event de test
		Event event = new Event(1,true,100,100,Event.Sport.Football,"M","Rouge et or","Vert et or","Québec","Laval",new Date(),new Date());
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
		writer.connect(DATA_FILE);
		writer.writeEvent(event);
		
		//assertEquals(event, );
		
		//XmlReader
	}
	

}
