package ca.ulaval.ticketmaster.users;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.support.BindingAwareModelMap;

import ca.ulaval.ticketmaster.dao.util.DataManager;
import ca.ulaval.ticketmaster.dao.util.EventFactory;
import ca.ulaval.ticketmaster.dao.util.TicketFactory;
import ca.ulaval.ticketmaster.events.model.Event;
import ca.ulaval.ticketmaster.events.model.SportType;
import ca.ulaval.ticketmaster.events.tickets.model.Ticket;
import ca.ulaval.ticketmaster.events.tickets.model.TicketType;
import ca.ulaval.ticketmaster.execptions.InvalidFormExceptions;
import ca.ulaval.ticketmaster.execptions.InvalidPurchaseException;
import ca.ulaval.ticketmaster.execptions.UnauthenticatedException;
import ca.ulaval.ticketmaster.springproxy.ProxyHttpSession;
import ca.ulaval.ticketmaster.springproxy.ProxyModel;
import ca.ulaval.ticketmaster.users.model.User;
import ca.ulaval.ticketmaster.users.model.UserConverter;
import ca.ulaval.ticketmaster.users.model.UserViewModel;
import ca.ulaval.ticketmaster.users.model.User.AccessLevel;

@RunWith(MockitoJUnitRunner.class)
@SuppressWarnings("unchecked")
public class TestBLUser {
	 @Mock
	    public DataManager datamanager;

	    @InjectMocks
	    public BLUser domaine;

	    private BindingAwareModelMap realModel;
	    private ProxyModel model;

	    @Before
	    public void setUp() {
		realModel = new BindingAwareModelMap();
		model = ProxyModel.create(realModel);
	    }

	    // Certaines fonctions sont deja testees par HomeControllerTest

	    @Test
	    public void testCreateUserPOST() {
		// Je test directement dans le domaine, puisque le controlleur ne fait
		// qu'un appel .. ?

		ProxyHttpSession httpSession = ProxyHttpSession.create(new MockHttpSession());

		UserViewModel viewModel = new UserViewModel();
		viewModel.setUsername("bob");

		BindingResult result = mock(BindingResult.class);

		User user = UserConverter.convert(viewModel);

		when(datamanager.saveUser(user)).thenReturn(true);

		String ret = domaine.createUser(user, viewModel, model, result, httpSession);

		// assertEquals(model.get("sesusername"), viewModel.getUsername());
		assertEquals(httpSession.getAttribute("sesusername"), user.getUsername());
		assertEquals(ret, "Home");

	    }

	    @Test
	    public void testCreateUserAlreadyExistPOST() {
		// Je test directement dans le domaine, puisque le controlleur ne fait
		// qu'un appel .. ?

		ProxyHttpSession httpSession = ProxyHttpSession.create(new MockHttpSession());

		UserViewModel viewModel = new UserViewModel();
		viewModel.setUsername("bob");

		BindingResult result = mock(BindingResult.class);

		User user = UserConverter.convert(viewModel);

		when(datamanager.saveUser(user)).thenReturn(false);

		String ret = domaine.createUser(user, viewModel, model, result, httpSession);

		// assertEquals(model.get("sesusername"), viewModel.getUsername());
		assertNull(httpSession.getAttribute("sesusername"));
		assertEquals(ret, "CreateUser");

	    }

	    @Test
	    public void testCreateUserWithErrorsPOST() {
		// Je test directement dans le domaine, puisque le controlleur ne fait
		// qu'un appel .. ?

		ProxyHttpSession httpSession = ProxyHttpSession.create(new MockHttpSession());

		UserViewModel viewModel = new UserViewModel();
		viewModel.setUsername("bob");

		BindingResult result = mock(BindingResult.class);

		User user = UserConverter.convert(viewModel);

		when(result.hasErrors()).thenReturn(true);

		String ret = domaine.createUser(user, viewModel, model, result, httpSession);

		assertEquals(ret, "CreateUser");

	    }
}
