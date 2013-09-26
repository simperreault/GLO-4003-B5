package ca.ulaval.ticketmaster.web;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Matchers;
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
	public TicketController controller;

	public static final int DEFAULT_EVENT_ID = 1;

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
		
		BindingResult result = mock(BindingResult.class);
		when(result.hasErrors()).thenReturn(false);
		when(datamanager.saveTicket(new Ticket(1))).thenReturn(true);

		String redirect = controller.create(DEFAULT_EVENT_ID, viewModel,
				result, model);
		
		assertEquals("redirect:/event/list", redirect);
	}

}
