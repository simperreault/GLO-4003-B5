package ca.ulaval.ticketmaster.users;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpServletRequest;
//import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.validation.support.BindingAwareModelMap;

import ca.ulaval.ticketmaster.dao.util.DataManager;
import ca.ulaval.ticketmaster.users.model.User;
import ca.ulaval.ticketmaster.users.model.User.AccessLevel;

//import ca.ulaval.ticketmaster.web.DomaineAffaire.DAUser;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

    @Mock
    public DataManager datamanager;

    // @InjectMocks
    // public DAUser domaine;

    @InjectMocks
    public UserController controller;

    private BindingAwareModelMap model;

    @Before
    public void setUp() {
	model = new BindingAwareModelMap();
    }
    
    @Test
    public void testCreateUserGET() {
	String ret = controller.CreateUser(model);

	assertNotNull(model.get("user"));
	assertEquals(ret, "CreateUser");

    }

    
    @Test
    public void testLoginGET() {

	String ret = controller.Login(model, null);

	assertEquals(ret, "Home");

    }

    @Test
    public void testSuccessfulLogin() {

	HttpSession httpSession = new MockHttpSession();

	String adminUsername = "CarloBoutet";

	User admin = new User(adminUsername);
	admin.setPassword("123");
	admin.setAccessLevel(AccessLevel.Admin);

	when(datamanager.findUser(adminUsername)).thenReturn(admin);



	String ret = controller.Login(adminUsername, admin.getPassword(), model, httpSession, null);

	assertEquals(ret, "Home");

	assertNotNull(httpSession.getAttribute("sesusername"));
	assertNotNull(httpSession.getAttribute("sesacceslevel"));


    }

    @Test
    public void testFailLoginWithExistingUser() {

	HttpSession httpSession = new MockHttpSession();

	User admin = new User("CarloBoutet");
	admin.setPassword("123");

	when(datamanager.findUser(admin.getUsername())).thenReturn(admin);

	String ret = controller.Login(admin.getUsername(), "NOTTHEPASSWORD", model, httpSession, null);

	assertEquals(ret, "Home");

	assertNull(httpSession.getAttribute("sesacceslevel"));
	assertNull(httpSession.getAttribute("sesusername"));

	// On ne verifie pas le contenu car ce serait restrictif si on change un
	// message d'erreur
	// @TODO demander au prof
	assertNotNull(model.get("errorMsg"));
	// assertNotNull(model.get("currentPage"));

    }

    @Test
    public void testFailLoginWithUnexistingUser() {

	HttpSession httpSession = new MockHttpSession();
	HttpServletRequest request = new MockHttpServletRequest();

	String ret = controller.Login("HEY!!", "hey", model, httpSession, request);

	assertEquals(ret, "Home");

	assertNull(httpSession.getAttribute("sesacceslevel"));
	assertNull(httpSession.getAttribute("sesusername"));

	// On ne verifie pas le contenu car ce serait restrictif si on change un
	// message d'erreur
	// @TODO demander au prof
	assertNotNull(model.get("errorMsg"));
	// assertNotNull(model.get("currentPage"));

    }

    @Test
    public void testDisconnect() {

	HttpSession httpSession = new MockHttpSession();

	String ret = controller.Disconnect(model, httpSession, null);

	assertNull(httpSession.getAttribute("sesacceslevel"));
	assertNull(httpSession.getAttribute("sesusername"));

	assertEquals(ret, "Home");

    }

}
