package ca.ulaval.ticketmaster.web;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ca.ulaval.ticketmaster.model.enums.PaymentType;
import ca.ulaval.ticketmaster.web.DomaineAffaire.DAAuthentication;
import ca.ulaval.ticketmaster.web.DomaineAffaire.DABasket;
import ca.ulaval.ticketmaster.web.DomaineAffaire.Page;
import ca.ulaval.ticketmaster.web.DomaineAffaire.proxy.ProxyHttpSession;
import ca.ulaval.ticketmaster.web.DomaineAffaire.proxy.ProxyModel;
import ca.ulaval.ticketmaster.web.viewmodels.PurchaseViewModel;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

	// private static final Logger logger =
	// LoggerFactory.getLogger(HomeController.class);

	private DABasket domaine;

	public HomeController() {
		domaine = new DABasket();
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String MainFrame(Model model) {
		return "Home";
	}

	@RequestMapping(value = "/Home", method = RequestMethod.GET)
	public String home(Model model) {
		return "Home";
	}
	
	@RequestMapping(value = "/Confirmation", method = RequestMethod.GET)
	public String confirmation(Model model) {
		return "Confirmation";
	}

	@RequestMapping(value = "/Purchase", method = RequestMethod.GET)
	public String Purchase(Model model) {
		model.addAttribute("purchaseInfos", new PurchaseViewModel());
		model.addAttribute("paymentType", PaymentType.values());
		return "Purchase";
	}

	@RequestMapping(value = "/Purchase", method = RequestMethod.POST)
	public String Purchase(@Valid PurchaseViewModel purchaseModel, Model model, HttpSession session,  BindingResult result) {
		model.addAttribute("purchaseInfos", new PurchaseViewModel());
		model.addAttribute("paymentType", PaymentType.values());
		return domaine.purchase(ProxyHttpSession.create(session),ProxyModel.create(model), result);
	}

	@RequestMapping(value = "/Basket", method = RequestMethod.GET)
	public String Basket(Model model, HttpSession session) {
		if (DAAuthentication.isLogged(ProxyHttpSession.create(session)))
			return Page.Basket.toString();
		else
			return Page.Home.toString();
	}

	@RequestMapping(value = "/addBasket", method = RequestMethod.POST)
	public String copyToBasket(@RequestParam("amount") int amount,@RequestParam("ticketId") String ticketId,@RequestParam("eventId") String eventId, HttpSession session) {
		return domaine.copyToBasket(eventId, ticketId, amount, ProxyHttpSession.create(session));
	}
	
}
