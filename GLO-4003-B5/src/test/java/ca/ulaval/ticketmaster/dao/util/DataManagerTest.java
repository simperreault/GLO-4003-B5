package ca.ulaval.ticketmaster.dao.util;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.when;

import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.ticketmaster.model.Event;
import ca.ulaval.ticketmaster.model.Ticket;
import ca.ulaval.ticketmaster.model.User;
import ca.ulaval.ticketmaster.model.User.AccessLevel;
import ca.ulaval.ticketmaster.model.enums.SportType;
import ca.ulaval.ticketmaster.model.enums.TicketType;

@RunWith(MockitoJUnitRunner.class)
public class DataManagerTest {
	public  String DATA_FILE = "src/test/resources/testDataXmlReader.xml";
	private DataManager dataManager = new DataManager();
	
	@Mock
	public XmlWriter xmlWriter;
	
	@Mock
	public XmlReader xmlReader;
	
	@InjectMocks
	public DataManager mockedDataManager = new DataManager();

	@Test
	public void testReconnect(){
	assertTrue(dataManager.reconnect(DATA_FILE));
	assertFalse(dataManager.reconnect("doesntexist"));
	}

	@Test
	public void testGetTotalUsers()  {
		dataManager.setTotalUsers(0);
		assertEquals(dataManager.getTotalUsers(),0);
	}

	@Test
	public void testSetTotalUsers()  {
		dataManager.setTotalUsers(0);
		assertEquals(dataManager.getTotalUsers(),0);
	}

	@Test
	public void testGetTotalEvents()  {
		dataManager.setTotalEvents(0);
		assertEquals(dataManager.getTotalEvents(),0);
	}

	@Test
	public void testSetTotalEvents() {
		dataManager.setTotalEvents(0);
		assertEquals(dataManager.getTotalEvents(),0);
	}

	@Test
	public void testDataManager()  {
		DataManager d = new DataManager();
		assertNotNull(d);
	}

	@Test
	public void testFindEvent() {
		assertEquals(dataManager.reconnect("src/test/resources/testDataXmlReader.xml"), true);
		Event e = dataManager.findEvent(UUID.fromString("d05696d9-8cae-4aca-a088-1501576e8187"));
		assertNotNull(e);
		assertEquals(e.getId(), UUID.fromString("d05696d9-8cae-4aca-a088-1501576e8187"));
		assertEquals(e.getStadium(), "Telus");
		assertEquals(e.getTicketList().size(), 3);
		
	}

	@Test
	public void testFindTicket()  {
		assertEquals(dataManager.reconnect("src/test/resources/testDataXmlReader.xml"), true);
		Ticket t = dataManager.findTicket(UUID.fromString("d05696d9-8cae-4aca-a088-1501576e8187"),
				UUID.fromString("108417d5-d0b4-4005-a357-0cf82ebd066d"));
			assertNotNull(t);
			assertEquals(t.getId(), UUID.fromString("108417d5-d0b4-4005-a357-0cf82ebd066d"));
			assertEquals(t.getEvent().getSectionList().get(0), "B");
			t = dataManager.findTicket(UUID.randomUUID(), UUID.randomUUID());
			assertNull(t);
	}

	@Test
	public void testFindUser()  {
		assertEquals(dataManager.reconnect("src/test/resources/testDataXmlReader.xml"), true);
		User u = dataManager.findUser("CarloBoutet");
		assertEquals(u.getPassword(), "123");
		assertEquals(u.getAccessLevel(), AccessLevel.Admin);
		assertNull(dataManager.findUser("bobthenonexistinguser"));
	}

	@Test
	public void testSaveEvent() {
		Event _event = new Event();
		when(xmlWriter.writeEvent(_event)).thenReturn(true);
		assertTrue(mockedDataManager.saveEvent(_event));
		when(xmlWriter.writeEvent(_event)).thenReturn(false);
		assertFalse(mockedDataManager.saveEvent(_event));
		
	}

	@Test
	public void testSaveTicket() {
		Ticket _ticket = TicketFactory.CreateTicket();
		Event event = new Event();
		_ticket.setEvent(event);
		when(xmlWriter.writeTicketToEvent(event.getId(), _ticket)).thenReturn(true);
		assertTrue(mockedDataManager.saveTicket(_ticket));
		_ticket.setEvent(null);
		assertFalse(mockedDataManager.saveTicket(_ticket));
	}

	@Test
	public void testSaveTicketsToEvent() {
		Ticket _ticket = TicketFactory.CreateTicket();
		Event event = new Event();
		_ticket.setEvent(event);
		List<Ticket> _ticketsToAdd = new ArrayList<Ticket>();
		_ticketsToAdd.add(_ticket);
		_ticketsToAdd.add(_ticket);
		when( xmlWriter.writeTicketToEvent(event.getId(), _ticket)).thenReturn(true);
		when( xmlReader.loadEvent(event.getId())).thenReturn(event);
		assertTrue(mockedDataManager.saveTicketsToEvent(event.getId(), _ticketsToAdd));
		_ticket.setEvent(null);
		assertFalse(mockedDataManager.saveTicketsToEvent(event.getId(), _ticketsToAdd));
	}

	@Test
	public void testSaveUser() {
		User _user = new User(DATA_FILE);
		when( xmlWriter.writeUser(_user)).thenReturn(true);
		assertTrue(mockedDataManager.saveUser(_user));
		when(xmlReader.userAuthenticate(_user.getUsername())).thenReturn(_user);
		assertFalse(mockedDataManager.saveUser(_user));
	}

	@Test
	public void testEditUser() throws Exception {
		Event _user = new Event();
		when( xmlWriter.modifyEvent(_user)).thenReturn(true);
		assertTrue(mockedDataManager.editEvent(_user));
	}

	@Test
	public void testEditEvent() throws Exception {
		User _user = new User(DATA_FILE);
		when( xmlWriter.modifyUser(_user)).thenReturn(true);
		assertTrue(mockedDataManager.editUser(_user));
	}

	@Test
	public void testEditTicket() throws Exception {
		Ticket _user = TicketFactory.CreateTicket();
		when( xmlWriter.modifyTicket(_user)).thenReturn(true);
		assertTrue(mockedDataManager.editTicket(_user));
	}

	@Test
	public void testDeleteTicketUUIDUUID() throws Exception {

		when( xmlWriter.deleteTicket(null,null)).thenReturn(true);
		assertTrue(mockedDataManager.deleteTicket(null, null));
	}

	@Test
	public void testDeleteTicketTicket() throws Exception {
		Ticket _ticket = TicketFactory.CreateTicket();
		_ticket.setEvent(new Event());
		when( xmlWriter.deleteTicket(_ticket.getEvent().getId(), _ticket.getId())).thenReturn(true);
		assertTrue(mockedDataManager.deleteTicket( _ticket));
	}

	@Test
	public void testDeleteUser() throws Exception {
		when(xmlWriter.deleteUser("potato")).thenReturn(true);
		assertTrue(mockedDataManager.deleteUser( "potato"));
	}

	@Test
	public void testDeleteEventEvent() throws Exception {
		Event _event = new Event();
		when(xmlWriter.deleteEvent(_event.getId())).thenReturn(true);
		assertTrue(mockedDataManager.deleteEvent(_event));
	}

	@Test
	public void testDeleteEventUUID() throws Exception {
		UUID u = UUID.randomUUID();
		when(xmlWriter.deleteEvent(u)).thenReturn(true);
		mockedDataManager.deleteEvent(u);
	}

	@Test
	public void testFindAllTickets() throws Exception {
		UUID _eventId = UUID.randomUUID();
		Event _event = new Event();
		when(xmlReader.loadEvent(_eventId)).thenReturn(_event);
		assertEquals(mockedDataManager.findAllTickets(_eventId),new ArrayList<Ticket>());
	}

	@Test
	public void testFindAllUsers() throws Exception {
		when(xmlReader.loadUsers()).thenReturn(new ArrayList<User>());
		assertEquals(mockedDataManager.findAllUsers(),new ArrayList<User>());
	}

	@Test
	public void testSearchWithCriterias() throws Exception {
		List<Event> e = new ArrayList<Event>();
		Event ee = new Event();
		ee.setSport(SportType.BASKETBALL);
		Date d = new Date();
		ee.setDate(d);
		ee.setHomeTeam("huhuhu");
		e.add(ee);
		Ticket t = TicketFactory.CreateTicket();
		ee.addTicketToList(t);
		when(xmlReader.loadEvents()).thenReturn(e);
		assertEquals(mockedDataManager.SearchWithCriterias(SportType.BASKETBALL, 1, "huhuhu"),e);
	}

	@Test
	public void testGetAllTeams() throws Exception {
		List<Event> e = new ArrayList<Event>();
		Event ee = new Event();
		ee.setSport(SportType.BASKETBALL);
		Date d = new Date();
		ee.setDate(d);
		ee.setHomeTeam("huhuhu");
		ee.setVisitorsTeam("huhuhu");
		e.add(ee);
		Ticket t = TicketFactory.CreateTicket();
		ee.addTicketToList(t);
		when(xmlReader.loadEvents()).thenReturn(e);
		List<String> l = new ArrayList<String>();
		l.add("huhuhu");
		assertEquals(mockedDataManager.GetAllTeams(),l);
	}

	@Test
	public void testFindTransaction() throws Exception {
		UUID u = UUID.randomUUID();
		when(xmlReader.readTransaction(u, DATA_FILE)).thenReturn(new ArrayList<Ticket>());
		assertEquals(mockedDataManager.findTransaction(u, DATA_FILE), new ArrayList<Ticket>());
		assertEquals(mockedDataManager.findTransaction(null, null), null);
	}

	@Test
	public void testFindAllEvents() throws Exception {
		List<Event> e = new ArrayList<Event>();
		when(xmlReader.loadEvents()).thenReturn(e);
		assertEquals(mockedDataManager.findAllEvents(),e);

	}

	@Test
	public void testFindSimilarTickets() throws Exception {
    	List<Ticket> returnList = new ArrayList<Ticket>();
    	Event e = new Event();
		returnList.add(TicketFactory.CreateTicket(e, TicketType.SIMPLE, "1", "1", "", 3, 0));
		returnList.add(TicketFactory.CreateTicket(e, TicketType.SIMPLE, "1", "1", "", 3, 0));
		
		assertEquals(mockedDataManager.findSimilarTickets(returnList, TicketType.SIMPLE, "1"),returnList);
	}

	@Test
	public void testCountSimilarTickets() throws Exception {
		List<Ticket> returnList = new ArrayList<Ticket>();
    	Event e = new Event();
		returnList.add(TicketFactory.CreateTicket(e, TicketType.SIMPLE, "1", "1", "", 3, 0));
		returnList.add(TicketFactory.CreateTicket(e, TicketType.SIMPLE, "1", "1", "", 3, 0));
		
		assertEquals(mockedDataManager.countSimilarTickets(returnList, TicketType.SIMPLE, "1"),2);
	}

	@Test
	public void testRegroupSimilarTickets() throws Exception {
		ArrayList<Ticket> returnList = new ArrayList<Ticket>();
		List<ArrayList<Ticket>> someList = new ArrayList<ArrayList<Ticket>>();
    	Event e = new Event();
		returnList.add(TicketFactory.CreateTicket(e, TicketType.SIMPLE, "1", "1", "", 3, 0));
		returnList.add(TicketFactory.CreateTicket(e, TicketType.SIMPLE, "1", "1", "", 3, 0));
		e.setTicketList(returnList);
		someList.add(returnList);
		when(xmlReader.loadEvent(e.getId())).thenReturn(e);
		assertEquals(someList,mockedDataManager.regroupSimilarTickets(e.getId()));
	}
	
	@Test
	public void testRegroupSamePriceTickets(){
		ArrayList<Ticket> returnList = new ArrayList<Ticket>();
		List<ArrayList<Ticket>> someList = new ArrayList<ArrayList<Ticket>>();
    	Event e = new Event();
		returnList.add(TicketFactory.CreateTicket(e, TicketType.SIMPLE, "1", "1", "", 3, 0));
		returnList.add(TicketFactory.CreateTicket(e, TicketType.SIMPLE, "1", "1", "", 3, 0));
		e.setTicketList(returnList);
		someList.add(returnList);
		when(xmlReader.loadEvent(e.getId())).thenReturn(e);
		assertEquals(someList,mockedDataManager.regroupSamePriceTickets(e.getId()));
	}
	
	@Test
	public void testBuyTickets(){
		User user = new User("CarloBoutet");
    	ArrayList<Ticket> testList = new ArrayList<Ticket>();
    	Ticket t = TicketFactory.CreateTicket();
    	t.setPrice(1);
    	t.setType(TicketType.GENERAL);
    	t.setEvent(new Event());
    	testList.add(t);
    	when(xmlWriter.writeTransaction(UUID.randomUUID(),testList , "CarloBoutet")).thenReturn(true);
    	when(xmlReader.userAuthenticate("CarloBoutet")).thenReturn(user);
    	when(xmlWriter.modifyUser(user)).thenReturn(true);
    	when(xmlWriter.modifyTicket(t)).thenReturn(true);
    	mockedDataManager.buyTickets(testList, "CarloBoutet");
	}

}
