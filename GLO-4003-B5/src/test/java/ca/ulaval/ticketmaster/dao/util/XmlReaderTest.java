package ca.ulaval.ticketmaster.dao.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.ticketmaster.model.Event;
import ca.ulaval.ticketmaster.model.Ticket;
import ca.ulaval.ticketmaster.model.User;
import ca.ulaval.ticketmaster.model.User.AccessLevel;

@RunWith(MockitoJUnitRunner.class)
public class XmlReaderTest {
	
	@Test
	public void TestXmlReader(){
		XmlReader x = new XmlReader();
		assertNotNull(x);
	}
	
	@Test
	public void TestConnectError(){
		XmlReader x = new XmlReader();
		assertEquals(x.connect("notrealfile"),false);
	}
	@Test
	public void TestConnect(){
		XmlReader x = new XmlReader();
		assertEquals(x.connect(XmlReader.DATA_FILE),true);

	}
	@Test
	public void TestStartup(){
		XmlReader x = new XmlReader();
		assertEquals(x.connect("src/test/resources/testDataXmlReader.xml"),true);
		int[] result = x.readStartupInformation();
		assertEquals(result[0],1);
		assertEquals(result[1],1);
		assertEquals(result[2],2);
	}
	@Test
	public void TestLoadEvent(){
		XmlReader x = new XmlReader();
		assertEquals(x.connect("src/test/resources/testDataXmlReader.xml"),true);
		Event e = x.loadEvent(1, false);
		assertNotNull(e);
		assertEquals(e.getId(),1);
		assertEquals(e.getStadium(),"Telus");
		assertEquals(e.getTicketList().size(),0);
		e = x.loadEvent(1);
		assertEquals(e.getTicketList().get(2).getSeat(),"qweqweqw");
		assertEquals(e.getSectionList().size(),1);
		assertEquals(e.getSectionList().get(0),"B");
		assertEquals(e.getTicketList().size(),3);
		e = x.loadEvent(0);
		assertNull(e);
	}
	@Test
	public void TestLoadTicket(){
		XmlReader x = new XmlReader();
		assertEquals(x.connect("src/test/resources/testDataXmlReader.xml"),true);
		Ticket t = x.loadTicket(1, 1);
		assertNotNull(t);
		assertEquals(t.getId(),1);
		assertEquals(t.getEvent().getSectionList().get(0),"B");
		 t = x.loadTicket(0, 1);
		 assertNull(t);
	}
	
	@Test
	public void TestLoadEvents(){
		XmlReader x = new XmlReader();
		assertEquals(x.connect("src/test/resources/testDataXmlReader.xml"),true);
		List<Event> l = x.loadEvents();
		assertEquals(l.size(), 1);
	}
	
	@Test
	public void TestLoadUser(){
		XmlReader x = new XmlReader();
		assertEquals(x.connect("src/test/resources/testDataXmlReader.xml"),true);
		User u = x.userAuthenticate("CarloBoutet");
		assertEquals(u.getPassword(),"123");
		assertEquals(u.getAccessLevel(), AccessLevel.Admin);
		assertNull(x.userAuthenticate("bobthenonexistinguser"));
		
	}
	
	@Test
	public void TestLoadUsers(){
		XmlReader x = new XmlReader();
		assertEquals(x.connect("src/test/resources/testDataXmlReader.xml"),true);
		List<User> l = x.loadUsers();
		assertEquals(l.size(),2);
		assertEquals(l.get(0).getAccessLevel(), AccessLevel.Admin);
		
	}
	
}
