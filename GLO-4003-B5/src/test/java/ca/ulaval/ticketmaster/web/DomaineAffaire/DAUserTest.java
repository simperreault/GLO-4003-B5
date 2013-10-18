package ca.ulaval.ticketmaster.web.DomaineAffaire;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;

import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.validation.BindingResult;
import org.springframework.validation.support.BindingAwareModelMap;
//import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockHttpSession;







import ca.ulaval.ticketmaster.dao.util.DataManager;
import ca.ulaval.ticketmaster.model.User;
import ca.ulaval.ticketmaster.model.User.AccessLevel;
import ca.ulaval.ticketmaster.web.HomeController;
import ca.ulaval.ticketmaster.web.DomaineAffaire.DAUser;
import ca.ulaval.ticketmaster.web.converter.UserConverter;
import ca.ulaval.ticketmaster.web.viewmodels.UserViewModel;

@RunWith(MockitoJUnitRunner.class)
public class DAUserTest {

    @Mock
    public DataManager datamanager;
    
    @InjectMocks
	public DAUser domaine;

    private BindingAwareModelMap model;

    @Before
    public void setUp() {
	model = new BindingAwareModelMap();
    }
    
    //Certaines fonctions sont deja testees par HomeControllerTest

    @Test
    public void testCreateUserPOST() {
    	//Je test directement dans le domaine, puisque le controlleur ne fait qu'un appel .. ?
    	
    	HttpSession httpSession = new MockHttpSession();
    	
		UserViewModel viewModel = new UserViewModel();
		viewModel.setUsername("bob");
		
	    BindingResult result = mock(BindingResult.class);
	    
	    User user = UserConverter.convert(viewModel);
		
		when(datamanager.saveUser(user)).thenReturn(true);
	
		String ret = domaine.createUser(user, viewModel, model, result, httpSession);
	
	
		//assertEquals(model.get("sesusername"), viewModel.getUsername());
		assertEquals(httpSession.getAttribute("sesusername"), user.getUsername());
		assertEquals(ret, "Home");

    }

    @Test
    public void testCreateUserAlreadyExistPOST() {
    	//Je test directement dans le domaine, puisque le controlleur ne fait qu'un appel .. ?
    	
    	HttpSession httpSession = new MockHttpSession();
    	
		UserViewModel viewModel = new UserViewModel();
		viewModel.setUsername("bob");
		
	    BindingResult result = mock(BindingResult.class);
	    
	    User user = UserConverter.convert(viewModel);
		
		when(datamanager.saveUser(user)).thenReturn(false);
	
		String ret = domaine.createUser(user, viewModel, model, result, httpSession);
	
	
		//assertEquals(model.get("sesusername"), viewModel.getUsername());
		assertNull(httpSession.getAttribute("sesusername"));
		assertEquals(ret, "CreateUser");

    }

    @Test
    public void testCreateUserWithErrorsPOST() {
    	//Je test directement dans le domaine, puisque le controlleur ne fait qu'un appel .. ?
    	
    	HttpSession httpSession = new MockHttpSession();
    	
		UserViewModel viewModel = new UserViewModel();
		viewModel.setUsername("bob");
		
	    BindingResult result = mock(BindingResult.class);
	    
	    User user = UserConverter.convert(viewModel);
		
		when(result.hasErrors()).thenReturn(true);
	
		String ret = domaine.createUser(user, viewModel, model, result, httpSession);
	
		assertEquals(ret, "CreateUser");

    }

}
