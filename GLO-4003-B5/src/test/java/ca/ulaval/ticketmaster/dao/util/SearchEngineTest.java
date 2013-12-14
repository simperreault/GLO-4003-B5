package ca.ulaval.ticketmaster.dao.util;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.ticketmaster.events.model.Event;
import ca.ulaval.ticketmaster.events.model.SportType;
import ca.ulaval.ticketmaster.events.tickets.model.Ticket;
import ca.ulaval.ticketmaster.events.tickets.model.TicketType;

@RunWith(MockitoJUnitRunner.class)
public class SearchEngineTest {
    @Mock
    public DataManager dataManager;

    @InjectMocks
    public SearchEngine mockedSearchEngine = new SearchEngine();

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
	when(dataManager.findAllEventsWithAllTickets()).thenReturn(e);

	assertEquals(mockedSearchEngine.SearchWithCriterias(SportType.BASKETBALL, 1, "huhuhu"), e);
    }

    @Test
    public void testFindAllTickets() throws Exception {
	UUID _eventId = UUID.randomUUID();
	Event _event = new Event();
	when(dataManager.findEvent(_eventId)).thenReturn(_event);
	assertEquals(mockedSearchEngine.findAllTickets(_eventId), new ArrayList<Ticket>());
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
	when(dataManager.findAllEventsWithAllTickets()).thenReturn(e);
	List<String> l = new ArrayList<String>();
	l.add("huhuhu");
	assertEquals(mockedSearchEngine.GetAllTeams(), l);
    }

    @Test
    public void testFindAllEvents() throws Exception {
	List<Event> e = new ArrayList<Event>();
	when(dataManager.findAllEventsWithAllTickets()).thenReturn(e);
	assertEquals(mockedSearchEngine.findAllEvents(), e);

    }

    @Test
    public void testFindSimilarTickets() throws Exception {
	List<Ticket> returnList = new ArrayList<Ticket>();
	Event e = new Event();
	returnList.add(TicketFactory.CreateTicket(e, TicketType.SIMPLE, "1", "1", "", 3, 0));
	returnList.add(TicketFactory.CreateTicket(e, TicketType.SIMPLE, "1", "1", "", 3, 0));

	assertEquals(mockedSearchEngine.findSimilarTickets(returnList, TicketType.SIMPLE, "1"), returnList);
    }

    @Test
    public void testCountSimilarTickets() throws Exception {
	List<Ticket> returnList = new ArrayList<Ticket>();
	Event e = new Event();
	returnList.add(TicketFactory.CreateTicket(e, TicketType.SIMPLE, "1", "1", "", 3, 0));
	returnList.add(TicketFactory.CreateTicket(e, TicketType.SIMPLE, "1", "1", "", 3, 0));

	assertEquals(mockedSearchEngine.countSimilarTickets(returnList, TicketType.SIMPLE, "1"), 2);
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
	when(dataManager.findEvent(e.getId())).thenReturn(e);
	assertEquals(someList, mockedSearchEngine.regroupSimilarTickets(e.getId()));
    }

    @Test
    public void testRegroupByEvent() throws Exception {
	ArrayList<Ticket> returnList = new ArrayList<Ticket>();
	ArrayList<Ticket> returnList2 = new ArrayList<Ticket>();
	Event e = new Event();
	Event e2 = new Event();
	returnList.add(TicketFactory.CreateTicket(e, TicketType.SIMPLE, "1", "1", "", 3, 0));
	returnList.add(TicketFactory.CreateTicket(e, TicketType.SIMPLE, "1", "1", "", 3, 0));
	returnList2.add(TicketFactory.CreateTicket(e2, TicketType.SIMPLE, "1", "1", "", 3, 0));
	returnList2.add(TicketFactory.CreateTicket(e2, TicketType.SIMPLE, "1", "1", "", 3, 0));
	e.setTicketList(returnList);
	e2.setTicketList(returnList2);
	ArrayList<Ticket> testlist = new ArrayList<Ticket>();
	testlist.addAll(returnList);
	testlist.addAll(returnList2);
	assertEquals(2, mockedSearchEngine.regroupSimilarTicketsByEvents(testlist).size());

    }

    @Test
    public void testRegroupSamePriceTickets() {
	ArrayList<Ticket> returnList = new ArrayList<Ticket>();
	List<ArrayList<Ticket>> someList = new ArrayList<ArrayList<Ticket>>();
	Event e = new Event();
	returnList.add(TicketFactory.CreateTicket(e, TicketType.SIMPLE, "1", "1", "", 3, 0));
	returnList.add(TicketFactory.CreateTicket(e, TicketType.SIMPLE, "1", "1", "", 3, 0));
	e.setTicketList(returnList);
	someList.add(returnList);
	when(dataManager.findEvent(e.getId())).thenReturn(e);
	assertEquals(someList, mockedSearchEngine.regroupSamePriceTickets(e.getId()));
    }

    @Test
    public void listwithlisttest() {
	ArrayList<Ticket> returnList = new ArrayList<Ticket>();
	ArrayList<Ticket> returnList2 = new ArrayList<Ticket>();
	ArrayList<Ticket> somelist = new ArrayList<Ticket>();
	Event e = new Event();
	Event e2 = new Event();
	returnList.add(TicketFactory.CreateTicket(e, TicketType.SIMPLE, "1", "1", "", 3, 0));
	returnList.add(TicketFactory.CreateTicket(e, TicketType.SIMPLE, "1", "1", "", 3, 0));
	returnList2.add(TicketFactory.CreateTicket(e2, TicketType.SIMPLE, "1", "1", "", 3, 0));
	returnList2.add(TicketFactory.CreateTicket(e2, TicketType.SIMPLE, "1", "1", "", 3, 0));
	somelist.addAll(returnList2);
	somelist.addAll(returnList);
	assertEquals(returnList2, mockedSearchEngine.filterListWithList(somelist, returnList));
    }
}
