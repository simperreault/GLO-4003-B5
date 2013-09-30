package ca.ulaval.ticketmaster.web;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
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
import ca.ulaval.ticketmaster.web.viewmodels.UserViewModel;

@RunWith(MockitoJUnitRunner.class)
public class HomeControllerTest {
	
	@Mock
	public DataManager datamanager;
	
	@InjectMocks
	public HomeController controller;

	private BindingAwareModelMap model;

	@Before
	public void setUp() {
		model = new BindingAwareModelMap();
	}

	@Test
	public void createAddANewViewModelToTheModelAndCheckReturn() {
		
		String ret = controller.MainFrame(model);

		assertEquals(model.get("currentPage"), "Home.jsp");
		assertEquals(ret, "MainFrame");
	}

	@Test
	public void testHome() {
		String ret = controller.home(model);
		
		assertEquals(model.get("currentPage"), "Home.jsp");
		
		assertEquals(ret, "MainFrame");
		
	}

	@Test
	public void testCreateUserGET() {
		String ret = controller.CreateUser(model);
		
		assertNotNull(model.get("user"));
		assertEquals(model.get("currentPage"), "CreateUser.jsp");
		
		assertEquals(ret, "MainFrame");
		
	}

	@Test
	public void testCreateUserPOST() {
		UserViewModel viewModel = new UserViewModel();
		viewModel.setUsername("bob");
		
		String ret = controller.CreateUser(viewModel, null, model);
		
		assertEquals(model.get("currentPage"), "Home.jsp");
		assertEquals(model.get("username"), viewModel.getUsername());
		
		assertEquals(ret, "MainFrame");
		
	}

	@Test
	public void testLogin() {
		
		String ret = controller.Login(model);
		
		assertEquals(model.get("currentPage"), "Login.jsp");
		
		assertEquals(ret, "MainFrame");
		
	}

	@Test
	public void testDisconnect() {
		
		String ret = controller.Disconnect(model, /*ehm...*/null);

		assertNull(model.get("sesacceslevel"));
		assertNull(model.get("sesusername"));
		
		assertEquals(ret, "MainFrame");
		
	}

}
