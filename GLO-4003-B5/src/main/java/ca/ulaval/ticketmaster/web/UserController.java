package ca.ulaval.ticketmaster.web;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ca.ulaval.ticketmaster.model.enums.PaymentType;
import ca.ulaval.ticketmaster.model.enums.TicketType;
import ca.ulaval.ticketmaster.web.DomaineAffaire.DAAuthentication;
import ca.ulaval.ticketmaster.web.DomaineAffaire.DAUser;
import ca.ulaval.ticketmaster.web.DomaineAffaire.Page;
import ca.ulaval.ticketmaster.web.DomaineAffaire.proxy.ProxyHttpSession;
import ca.ulaval.ticketmaster.web.DomaineAffaire.proxy.ProxyModel;
import ca.ulaval.ticketmaster.web.converter.UserConverter;
import ca.ulaval.ticketmaster.web.viewmodels.PurchaseViewModel;
import ca.ulaval.ticketmaster.web.viewmodels.UserViewModel;

/**
 * Handles requests for the application home page.
 */
@Controller
@RequestMapping(value = "/user")
// @SessionAttributes
public class UserController {

    // private static final Logger logger =
    // LoggerFactory.getLogger(HomeController.class);
    private DAUser domaine;

    public UserController() {
	domaine = new DAUser();
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String CreateUser(Model model) {

	model.addAttribute("user", new UserViewModel());
	model.addAttribute("typeList", TicketType.values());

	//return "CreateUser";
	return Page.CreateUser.toString();
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String CreateUser(@Valid UserViewModel viewmodel, BindingResult result, Model model,
	    HttpSession session) {

	return domaine.createUser(UserConverter.convert(viewmodel), viewmodel, ProxyModel.create(model),
		result, ProxyHttpSession.create(session));
    }

    @RequestMapping(value = "/Login", method = RequestMethod.GET)
    public String Login(Model model) {
	return "Home";
    }

    @RequestMapping(value = { "/disconnect" }, method = RequestMethod.GET)
    public String Disconnect(Model model, HttpSession session) {

	return domaine.disconnect(ProxyHttpSession.create(session));
    }

    @RequestMapping(value = { "/connect", "/event/connect", "/event/{id}/connect" }, method = RequestMethod.POST)
    public String Login(@RequestParam("username") String username, @RequestParam("password") String password,
	    Model model, HttpSession session) {
	return domaine
		.connect(username, password, ProxyModel.create(model), ProxyHttpSession.create(session));
    }

}
