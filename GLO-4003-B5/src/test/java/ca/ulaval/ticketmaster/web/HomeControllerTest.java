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
import org.mockito.runners.MockitoJUnitRunner;
//import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.validation.support.BindingAwareModelMap;

import ca.ulaval.ticketmaster.dao.util.DataManager;
import ca.ulaval.ticketmaster.model.User;
import ca.ulaval.ticketmaster.model.User.AccessLevel;

//import ca.ulaval.ticketmaster.web.DomaineAffaire.DAUser;

@RunWith(MockitoJUnitRunner.class)
public class HomeControllerTest {

    @Mock
    public DataManager datamanager;

    // @InjectMocks
    // public DAUser domaine;

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
    public void testBasketLogged() {

	HttpSession httpSession = new MockHttpSession();

	httpSession.setAttribute("sesacceslevel", "user");

	String ret = controller.Basket(model, httpSession);

	assertEquals(ret, "Basket");
    }

    @Test
    public void testBasketNotLogged() {

	HttpSession httpSession = new MockHttpSession();

	String ret = controller.Basket(model, httpSession);

	assertEquals(ret, "Home");
    }

}
