package ca.ulaval.ticketmaster.web;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ca.ulaval.ticketmaster.web.converter.UserConverter;
import ca.ulaval.ticketmaster.web.viewmodels.UserViewModel;

/**
 * Handles requests for the application home page.
 */
@Controller
// @SessionAttributes
public class HomeController {

	// private static final Logger logger =
	// LoggerFactory.getLogger(HomeController.class);
	private GlobalController controller;

	public HomeController() {
		controller = new GlobalController();
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String MainFrame(Model model) {
		return "Home";
	}

	@RequestMapping(value = "/Home", method = RequestMethod.GET)
	public String home(Model model) {
		return "Home";
	}

	@RequestMapping(value = "/CreateUser", method = RequestMethod.GET)
	public String CreateUser(Model model) {
		model.addAttribute("user", new UserViewModel());
		return "CreateUser";
	}

	@RequestMapping(value = "/CreateUser", method = RequestMethod.POST)
	public String CreateUser(@Valid UserViewModel viewmodel, BindingResult result, Model model, HttpSession session) {
		return controller.createUser(UserConverter.convert(viewmodel), viewmodel, model, result, session);
	}

	@RequestMapping(value = "/Login", method = RequestMethod.GET)
	public String Login(Model model) {
		return "Home";
	}

	@RequestMapping(value = { "/disconnect" }, method = RequestMethod.GET)
	public String Disconnect(Model model, HttpSession session) {

		return controller.disconnect(session);
	}

	@RequestMapping(value = { "/connect", "/event/connect", "/event/{id}/connect" }, method = RequestMethod.POST)
	public String Login(@RequestParam("username") String username, @RequestParam("password") String password,
			Model model, HttpSession session) {
		return controller.connect(username, password, model, session);
	}

	@RequestMapping(value = "/Basket", method = RequestMethod.GET)
	public String Basket(Model model) {
		model.addAttribute("currentPage", "Basket.jsp");
		return "Basket";
	}

}
