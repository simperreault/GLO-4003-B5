package ca.ulaval.ticketmaster.users;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import ca.ulaval.ticketmaster.dao.util.DataManager;
import ca.ulaval.ticketmaster.exceptions.InvalidFormExceptions;
import ca.ulaval.ticketmaster.exceptions.UnauthorizedException;
import ca.ulaval.ticketmaster.springproxy.ProxyHttpSession;
import ca.ulaval.ticketmaster.springproxy.ProxyModel;
import ca.ulaval.ticketmaster.users.BLUser;
import ca.ulaval.ticketmaster.users.model.User;
import ca.ulaval.ticketmaster.users.model.User.AccessLevel;
import ca.ulaval.ticketmaster.users.model.UserViewModel;

public class TestBLUser {
	private ProxyHttpSession session;
	private BLUser blUser;
	private User user ;

	@Mock
	private DataManager dataManager;
	

	@Mock
	ProxyModel model;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		dataManager = Mockito.mock(DataManager.class);
		blUser = new BLUser(dataManager);
		session = ProxyHttpSession.create(new MockHttpSession());
		model = ProxyModel.create(Mockito.mock(Model.class));
		user = new User("");
	}
	
	private void setAdmin(ProxyHttpSession session) {
		session.setAttribute("sesacceslevel", "Admin");
	}
	
	@Test(expected = InvalidFormExceptions.class)
	public void testCreateUserInvalidFormExceptions() throws InvalidFormExceptions
	{
		UserViewModel viewModel = Mockito.mock(UserViewModel.class);
		BindingResult result  = Mockito.mock(BindingResult.class);
		when(result.hasErrors()).thenReturn(true);
		blUser.createUser(user, viewModel, model, result, session);
	}
	@Test(expected = InvalidFormExceptions.class)
	public void testCreateUserInvalidFormExceptions2() throws InvalidFormExceptions
	{
		UserViewModel viewModel = Mockito.mock(UserViewModel.class);
		BindingResult result  = Mockito.mock(BindingResult.class);
		blUser.createUser(user, viewModel, model, result, session);
	}
	@Test
	public void testCreateUser() throws InvalidFormExceptions
	{
		User user = new User("");
		user.setAccessLevel(AccessLevel.Admin);
		UserViewModel viewModel = Mockito.mock(UserViewModel.class);
		BindingResult result  = Mockito.mock(BindingResult.class);
		when(dataManager.saveUser(user)).thenReturn(true);
		blUser.createUser(user, viewModel, model, result, session);
		assertEquals(AccessLevel.Admin.toString(),session.getAttribute("sesacceslevel"));
	}
	@Test(expected = UnauthorizedException.class)
	public void testSetUserInfoUnauthorizedException() throws UnauthorizedException
	{
		blUser.setUserInfo(model, session);
	}
	@Test 
	public void testSetUserInfo() throws UnauthorizedException
	{
		session.setAttribute("sesusername", "bob");
		this.setAdmin(session);
		when(dataManager.findUser("bob")).thenReturn(user);
		blUser.setUserInfo(model, session);
	}
	@Test(expected = InvalidFormExceptions.class)
	public void testeditUserInvalidFormExceptions() throws InvalidFormExceptions
	{
		UserViewModel viewModel = Mockito.mock(UserViewModel.class);
		BindingResult result  = Mockito.mock(BindingResult.class);
		when(result.hasErrors()).thenReturn(true);
		blUser.editUser(viewModel, result, model, session);
	}
	public void testeditUser() throws InvalidFormExceptions
	{
		UserViewModel viewModel = Mockito.mock(UserViewModel.class);
		BindingResult result  = Mockito.mock(BindingResult.class);
		blUser.editUser(viewModel, result, model, session);
	}
}
