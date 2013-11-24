package ca.ulaval.ticketmaster.web;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.validation.BindingResult;
import org.springframework.validation.support.BindingAwareModelMap;

import ca.ulaval.ticketmaster.dao.util.DataManager;
import ca.ulaval.ticketmaster.events.EventController;
import ca.ulaval.ticketmaster.events.model.Event;
import ca.ulaval.ticketmaster.events.model.EventViewModel;
import ca.ulaval.ticketmaster.events.model.SportType;

@RunWith(MockitoJUnitRunner.class)
public class EventControllerTest {

    @Mock
    public DataManager datamanager;

    @InjectMocks
    public EventController controller;

    public static final UUID DEFAULT_EVENT_ID = UUID.randomUUID();

    private BindingAwareModelMap model;

    @Before
    public void setUp() {
	model = new BindingAwareModelMap();
    }

    @SuppressWarnings("unchecked")
    @Test
    public void listEvent() {
	List<Event> list = new LinkedList<Event>();
	list.add(new Event());
	list.add(new Event());
	list.add(new Event());
	when(datamanager.findAllEvents()).thenReturn(list);
	String view = controller.list(model);
	assertEquals(((List<Event>) model.get("EventList")).size(), 3);
	assertEquals(view, "EventList");
    }

    /*
     * @SuppressWarnings("unchecked")
     * 
     * @Test public void listTicketFormEvent() { List<Ticket> list = new
     * LinkedList<Ticket>(); list.add(new Ticket()); list.add(new Ticket());
     * list.add(new Ticket());
     * when(datamanager.loadAllTickets(DEFAULT_EVENT_ID)).thenReturn(list);
     * 
     * String view = controller.Event(UUID.fromString(DEFAULT_EVENT_ID), model);
     * 
     * assertEquals(((List<Ticket>)model.get("ticketList")).size(), 3);
     * assertEquals(model.get("eventID"), id);
     * assertNotNull(model.get("currentPage"));
     * assertEquals(model.get("currentPage"), "TicketList.jsp");
     * assertEquals(view, "MainFrame"); }
     * 
     * @Test public void detailTicket() { UUID idTicket = UUID.randomUUID();
     * when(datamanager.getTicket(DEFAULT_EVENT_ID, idTicket)).thenReturn(new
     * Ticket(idTicket));
     * 
     * String view = controller.detail(DEFAULT_EVENT_ID, idTicket, model);
     * 
     * assertEquals(((Ticket)model.get("ticket")).getId(), 1);
     * assertNotNull(model.get("currentPage"));
     * assertEquals(model.get("currentPage"), "detail.jsp"); assertEquals(view,
     * "MainFrame"); }
     */

    @Test
    public void createAddANewViewModelToTheModel() {
	MockHttpSession httpSession = new MockHttpSession();
	httpSession.setAttribute("sesacceslevel", "Admin");

	controller.create(model, httpSession);

	assertNotNull(model.get("event"));
    }

    @Test
    public void createReturnsTheCreateView() {
	// TODO fix the test add https session like this String view =
	// controller.create(model,Http Session);

	// assertEquals(view, "EventAdd");
    }

    @Test
    public void createEventRedirectsToEventList() {
	EventViewModel viewModel = new EventViewModel();
	viewModel.setSport(SportType.FOOTBALL);
	viewModel.setDate("10/10/2013 12:12");

	BindingResult result = mock(BindingResult.class);
	when(result.hasErrors()).thenReturn(false);
	when(datamanager.saveEvent(new Event(DEFAULT_EVENT_ID))).thenReturn(true);
	// TODO voir les autres todo
	// String redirect = controller.create(viewModel, result, model);

	// assertEquals("redirect:/event/list", redirect);
    }

    @Test
    public void createEventRedirectsToCreate() {
	EventViewModel viewModel = new EventViewModel();
	viewModel.setSport(SportType.FOOTBALL);

	BindingResult result = mock(BindingResult.class);
	when(result.hasErrors()).thenReturn(true);
	// TODO voir les autres todo
	// String redirect = controller.create(viewModel, result, model);

	// assertEquals(redirect, "EventAdd");
    }
}