package ca.ulaval.ticketmaster.home;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.validation.support.BindingAwareModelMap;

import ca.ulaval.ticketmaster.dao.util.DataManager;
//import org.springframework.mock.web.MockHttpSession;


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
    public void testHome() {
	String ret = controller.home(model);

	assertEquals(ret, "Home");
    }
}
