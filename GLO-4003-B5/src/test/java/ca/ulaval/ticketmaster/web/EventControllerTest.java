package ca.ulaval.ticketmaster.web;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.validation.BindingResult;
import org.springframework.validation.support.BindingAwareModelMap;

import ca.ulaval.ticketmaster.dao.util.DataManager;
import ca.ulaval.ticketmaster.model.Event;
import ca.ulaval.ticketmaster.model.Ticket;
import ca.ulaval.ticketmaster.web.viewmodels.EventViewModel;
import ca.ulaval.ticketmaster.web.viewmodels.TicketViewModel;

@RunWith(MockitoJUnitRunner.class)
public class EventControllerTest {

	@Mock
	public DataManager datamanager;

	@InjectMocks
	public EventController controller;

	public static final int DEFAULT_EVENT_ID = 1;

	private BindingAwareModelMap model;

	@Before
	public void setUp() {
		model = new BindingAwareModelMap();
	}

	@SuppressWarnings("unchecked")
	@Test
	public void listEvent() {
		List<Event> list = new LinkedList<Event>();
		list.add(new Event(1));
		list.add(new Event(2));
		list.add(new Event(3));
		when(datamanager.getEventList()).thenReturn(list);
		
		String view = controller.detail(model);
		
		assertEquals(((List<Event>)model.get("EventList")).size(), 3);
		assertNotNull(model.get("currentPage"));
		assertEquals(model.get("currentPage"), "EventList.jsp");
		assertEquals(view, "MainFrame");
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void listTicketFormEvent() {
		List<Ticket> list = new LinkedList<Ticket>();
		list.add(new Ticket(1));
		list.add(new Ticket(2));
		list.add(new Ticket(3));
		when(datamanager.loadAllTickets(1)).thenReturn(list);
		
		String view = controller.Event(1, model);
		
		assertEquals(((List<Ticket>)model.get("ticketList")).size(), 3);
		assertEquals(model.get("eventID"), 1);
		assertNotNull(model.get("currentPage"));
		assertEquals(model.get("currentPage"), "TicketList.jsp");
		assertEquals(view, "MainFrame");
	}
	
	@Test
	public void detailTicket() {
		when(datamanager.getTicket(1, 1)).thenReturn(new Ticket(1));
		
		String view = controller.detail(1, 1, model);
	
		assertEquals(((Ticket)model.get("ticket")).getId(), 1);
		assertNotNull(model.get("currentPage"));
		assertEquals(model.get("currentPage"), "detail.jsp");
		assertEquals(view, "MainFrame");
	}
	

	@Test
	public void createAddANewViewModelToTheModel() {
		controller.create(model);

		assertNotNull(model.get("event"));
	}

	@Test
	public void createReturnsTheCreateView() {
		String view = controller.create(model);

		assertNotNull(model.get("currentPage"));
		assertEquals(model.get("currentPage"), "EventAdd.jsp");
		assertEquals(view, "MainFrame");
	}

	@Test
	public void createEventRedirectsToEventList() {
		EventViewModel viewModel = new EventViewModel();
		viewModel.setSport("Football");

		BindingResult result = mock(BindingResult.class);
		when(result.hasErrors()).thenReturn(false);
		when(datamanager.saveEvent(new Event(1))).thenReturn(true);

		String redirect = controller.create(viewModel, result, model);

		assertEquals("redirect:/event/list", redirect);
	}

	@Test
	public void createEventRedirectsToCreate() {
		EventViewModel viewModel = new EventViewModel();
		viewModel.setSport("Football");

		BindingResult result = mock(BindingResult.class);
		when(result.hasErrors()).thenReturn(true);

		String redirect = controller.create(viewModel, result, model);

		assertNotNull(model.get("currentPage"));
		assertEquals(model.get("currentPage"), "EventAdd.jsp");
		assertEquals(redirect, "MainFrame");
	}
}
