package ca.ulaval.ticketmaster.web;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.validation.BindingResult;
import org.springframework.validation.support.BindingAwareModelMap;

import ca.ulaval.ticketmaster.dao.util.DataManager;
import ca.ulaval.ticketmaster.model.Ticket;
import ca.ulaval.ticketmaster.web.viewmodels.TicketViewModel;

@RunWith(MockitoJUnitRunner.class)
public class TicketControllerTest {
	
	@Mock
	public DataManager datamanager;
	
	@InjectMocks
	public TicketController controller = new TicketController();

	public static final String DEFAULT_EVENT_ID = UUID.randomUUID().toString();

	private BindingAwareModelMap model;

	@Before
	public void setUp() {
		model = new BindingAwareModelMap();
	}

	@Test
	public void createAddANewViewModelToTheModel() {
		controller.create(DEFAULT_EVENT_ID, model);

		assertNotNull(model.get("ticket"));
		assertNotNull(((TicketViewModel) model.get("ticket")).getEvent());
		assertEquals(
				((TicketViewModel) model.get("ticket")).getEvent().getId(),
				DEFAULT_EVENT_ID);
	}

	@Test
	public void createReturnsTheCreateView() {
		String view = controller.create(DEFAULT_EVENT_ID, model);

		assertNotNull(model.get("currentPage"));
		assertEquals(model.get("currentPage"), "TicketAdd.jsp");
		assertEquals(view, "MainFrame");
	}

	@Test
	public void createTicketRedirectsToTicketList() {
		TicketViewModel viewModel = new TicketViewModel();
		viewModel.setType("SEASON");
		
		BindingResult result = mock(BindingResult.class);
		when(result.hasErrors()).thenReturn(false);
		when(datamanager.saveTicket(new Ticket())).thenReturn(true);

		String redirect = controller.create(DEFAULT_EVENT_ID, viewModel,
				result, model);
		
		assertEquals("redirect:/event/" + DEFAULT_EVENT_ID, redirect);
	}
	/*
	@Test
	public void createTicketRedirectsToCreate() {
		TicketViewModel viewModel = new TicketViewModel();
		viewModel.setType("SEASON");
		
		BindingResult result = mock(BindingResult.class);
		when(result.hasErrors()).thenReturn(true);

		String redirect = controller.create(DEFAULT_EVENT_ID, viewModel,
				result, model);
		
		assertNotNull(model.get("currentPage"));
		assertEquals(model.get("currentPage"), "TicketAdd.jsp");
		assertEquals(redirect, "MainFrame");
	}
*/
}
