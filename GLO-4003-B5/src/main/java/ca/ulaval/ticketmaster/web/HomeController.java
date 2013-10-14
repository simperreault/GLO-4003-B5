package ca.ulaval.ticketmaster.web;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ca.ulaval.ticketmaster.dao.util.DataManager;
import ca.ulaval.ticketmaster.model.User;
import ca.ulaval.ticketmaster.model.enums.SportType;
import ca.ulaval.ticketmaster.model.enums.TicketType;
import ca.ulaval.ticketmaster.web.converter.UserConverter;
import ca.ulaval.ticketmaster.web.viewmodels.UserViewModel;

/**
 * Handles requests for the application home page.
 */
@Controller
// @SessionAttributes
public class HomeController {

    //private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
    private DataManager datamanager;

    public HomeController() {
	datamanager = new DataManager();
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String MainFrame(Model model) {
	// model.addAttribute("currentPage", "Home.jsp");
	return "Home";
    }

    @RequestMapping(value = "/Home", method = RequestMethod.GET)
    public String home(Model model) {
	// model.addAttribute("currentPage", "Home.jsp");
	return "Home";
    }

    @RequestMapping(value = "/CreateUser", method = RequestMethod.GET)
    public String CreateUser(Model model) {
	model.addAttribute("user", new UserViewModel());
	model.addAttribute("typeList", TicketType.values());
	// model.addAttribute("currentPage", "CreateUser.jsp");
	return "CreateUser";
    }

    @RequestMapping(value = "/CreateUser", method = RequestMethod.POST)
    public String CreateUser(@Valid UserViewModel viewmodel, BindingResult result, Model model) {

    	if (result.hasErrors()) {
    		System.out.println("CreateUser:POST:ERRORS");
    		model.addAttribute("user", viewmodel);
    		model.addAttribute("typeList", TicketType.values());
    	    model.addAttribute("error", result.getAllErrors());
    	    return "CreateUser";
    	}

		//model.addAttribute("username", viewmodel.username);
	
		User user = UserConverter.convert(viewmodel);
	
		if (datamanager.saveUser(user)) {
			model.addAttribute("message", "Utilisateur ajoute");
		} else {
			//Complexe : soit on passe d'une certaine facon le XMLReader au Validator,
			// soit on fait la validation ici
			
			//model.addAttribute("message", "Utilisateur deja present");
    		//model.addAttribute("error", "Utilisateur deja present");
			result.addError(new ObjectError("user", "Utilisateur deja existant"));
			
    	    model.addAttribute("error", result.getAllErrors());
    	    
    		model.addAttribute("user", viewmodel);
    		model.addAttribute("typeList", TicketType.values());

    	    return "CreateUser";
		}

		return "Home";
    }

    @RequestMapping(value = "/Login", method = RequestMethod.GET)
    public String Login(Model model) {
	// model.addAttribute("currentPage", "Login.jsp");
	return "Home";
    }

    @RequestMapping(value = { "/disconnect" }, method = RequestMethod.GET)
    public String Disconnect(Model model, HttpSession session) {
	session.setAttribute("sesacceslevel", null);
	session.setAttribute("sesusername", null);
	return "Home";

    }

    // Msemble  ca va  etre  a mettre ailleurs
    // TODO:trouve comment pas hardcoder tous les paths possibles
    @RequestMapping(value = { "/connect"}, method = RequestMethod.POST)
    public String Login(@RequestParam("username") String username, @RequestParam("password") String password,
	    Model model, HttpSession session) {

	boolean userIsOk = false;

	User user = datamanager.findUser(username);
	if (user != null) {
	    if (user.getPassword().equals(password)) {
		userIsOk = true;
		session.setAttribute("sesacceslevel", user.getAccessLevel().toString());
		session.setAttribute("sesusername", username);
	    }
	}

	if (!userIsOk) {
	    model.addAttribute("errorMsg", "La combinaison pseudo/mot de passe est invalide");
	    model.addAttribute("currentPage", "Home.jsp");
	} 
	
	return "Home";
	
    }

    @RequestMapping(value = "/Basket", method = RequestMethod.GET)
    public String Basket(Model model) {
	model.addAttribute("currentPage", "Basket.jsp");
	return "Basket";
    }

}
