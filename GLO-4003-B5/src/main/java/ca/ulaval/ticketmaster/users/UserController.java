package ca.ulaval.ticketmaster.users;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ca.ulaval.ticketmaster.events.tickets.model.TicketType;
import ca.ulaval.ticketmaster.exceptions.InvalidFormExceptions;
import ca.ulaval.ticketmaster.home.Page;
import ca.ulaval.ticketmaster.springproxy.ProxyHttpSession;
import ca.ulaval.ticketmaster.springproxy.ProxyModel;
import ca.ulaval.ticketmaster.users.model.UserConverter;
import ca.ulaval.ticketmaster.users.model.UserViewModel;

/**
 * Handles requests for the application home page.
 */
@Controller
@RequestMapping(value = "/user")
// @SessionAttributes
public class UserController {

	// private static final Logger logger =
	// LoggerFactory.getLogger(HomeController.class);
	private BLUser domaine;

	public UserController() {
		domaine = new BLUser();
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String CreateUser(Model model) {
		model.addAttribute("user", new UserViewModel());
		model.addAttribute("typeList", TicketType.values());
		return Page.CreateUser.toString();
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String CreateUser(@Valid UserViewModel viewmodel, BindingResult result, Model model,
			HttpSession session) {
		try {
			domaine.createUser(UserConverter.convert(viewmodel), viewmodel, ProxyModel.create(model),
					result, ProxyHttpSession.create(session));
		} catch (InvalidFormExceptions e) {
			return Page.CreateUser.toString();
		}
		return Page.Home.toString();
	}

	@RequestMapping(value = "/Login", method = RequestMethod.GET)
	public String Login(Model model, HttpServletRequest request) {
		return "redirect:" + request.getHeader("Referer");
	}

	@RequestMapping(value = { "/disconnect" }, method = RequestMethod.GET)
	public String Disconnect(Model model, HttpSession session, HttpServletRequest request) {
		domaine.disconnect(ProxyHttpSession.create(session));
		return "redirect:" + request.getHeader("Referer");
	}

	@RequestMapping(value = { "/connect"}, method = RequestMethod.POST)
	public String Login(@RequestParam("username") String username, @RequestParam("password") String password,
			Model model, HttpSession session, HttpServletRequest request) {
		domaine.connect(username, password, ProxyModel.create(model), ProxyHttpSession.create(session));
		return "redirect:" + request.getHeader("Referer");

	}

}
