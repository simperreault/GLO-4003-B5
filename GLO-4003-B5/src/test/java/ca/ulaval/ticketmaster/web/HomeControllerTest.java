package ca.ulaval.ticketmaster.web;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.validation.support.BindingAwareModelMap;
//import org.springframework.mock.web.MockHttpSession;






import ca.ulaval.ticketmaster.dao.util.DataManager;
import ca.ulaval.ticketmaster.dao.util.TicketFactory;
import ca.ulaval.ticketmaster.model.User;
import ca.ulaval.ticketmaster.model.User.AccessLevel;
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

	assertEquals(ret, "Home");
    }

    @Test
    public void testHome() {
	String ret = controller.home(model);

	assertEquals(ret, "Home");
    }

    @Test
    public void testCreateUserGET() {
	String ret = controller.CreateUser(model);

	assertNotNull(model.get("user"));
	assertEquals(ret, "CreateUser");

    }

    @Test
    public void testCreateUserPOST() {
	UserViewModel viewModel = new UserViewModel();
	viewModel.setUsername("bob");

	String ret = controller.CreateUser(viewModel, null, model);


	assertEquals(model.get("username"), viewModel.getUsername());
	assertEquals(ret, "Home");

    }

    @Test
    public void testLoginGET() {

	String ret = controller.Login(model);

	assertEquals(ret, "Home");

    }

    @Test
    public void testSuccessfulLogin() {

	HttpSession httpSession = Mockito.mock( HttpSession.class );
	
	String adminUsername = "CarloBoutet";
	
	User admin = new User(adminUsername);
	admin.setPassword("123");
	admin.setAccessLevel(AccessLevel.Admin);
	
	when(datamanager.findUser(adminUsername)).thenReturn(admin);
	
	//FOQQWEQEFQOF
	//when(httpSession.setAttribute("sesusername", admin.getAccessLevel().toString()).thenReturn("bob"));

	String ret = controller.Login(adminUsername, admin.getPassword(), model, httpSession);

	assertEquals(ret, "Home");

	assertNotNull(httpSession.getAttribute("sesusername"));
	assertNotNull(httpSession.getAttribute("sesacceslevel"));

	//assertNotNull(model.get("sesacceslevel"));
	//assertNotNull(model.get("sesusername"));

    }

    @Test
    public void testFailLoginWithExistingUser() {

	HttpSession httpSession = Mockito.mock( HttpSession.class );
	
	User admin = new User("CarloBoutet");
	admin.setPassword("123");
	
	when(datamanager.findUser(admin.getUsername())).thenReturn(admin);

	String ret = controller.Login(admin.getUsername(), "NOTTHEPASSWORD", model, httpSession);

	assertEquals(ret, "Home");

	assertNull(httpSession.getAttribute("sesacceslevel"));
	assertNull(httpSession.getAttribute("sesusername"));

	//On ne verifie pas le contenu car ce serait restrictif si on change un message d'erreur
	//@TODO demander au prof
	assertNotNull(model.get("errorMsg"));
	assertNotNull(model.get("currentPage"));

    }

    @Test
    public void testFailLoginWithUnexistingUser() {

	HttpSession httpSession = Mockito.mock( HttpSession.class );

	String ret = controller.Login("HEY!!", "FFFFFFUUUUUUUUU", model, httpSession);

	assertEquals(ret, "Home");

	assertNull(httpSession.getAttribute("sesacceslevel"));
	assertNull(httpSession.getAttribute("sesusername"));

	//On ne verifie pas le contenu car ce serait restrictif si on change un message d'erreur
	//@TODO demander au prof
	assertNotNull(model.get("errorMsg"));
	assertNotNull(model.get("currentPage"));

    }

    @Test
    public void testDisconnect() {

	HttpSession httpSession = Mockito.mock( HttpSession.class );
    	
	String ret = controller.Disconnect(model, /* ehm... null*/httpSession);
	
	assertNull(httpSession.getAttribute("sesacceslevel"));
	assertNull(httpSession.getAttribute("sesusername"));

	assertEquals(ret, "Home");

    }
    
    @Test
    public void testBasket() {
    	String ret = controller.Basket(model);
    	
    	assertEquals(model.get("currentPage"), "Basket.jsp");

    	assertEquals(ret, "Basket");
    }

}
