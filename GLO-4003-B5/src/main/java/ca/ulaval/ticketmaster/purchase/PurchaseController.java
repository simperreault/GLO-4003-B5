package ca.ulaval.ticketmaster.purchase;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ca.ulaval.ticketmaster.events.tickets.model.Ticket;
import ca.ulaval.ticketmaster.exceptions.InvalidFormExceptions;
import ca.ulaval.ticketmaster.exceptions.InvalidPurchaseException;
import ca.ulaval.ticketmaster.exceptions.UnauthenticatedException;
import ca.ulaval.ticketmaster.home.DAAuthentication;
import ca.ulaval.ticketmaster.home.Page;
import ca.ulaval.ticketmaster.purchase.model.PaymentType;
import ca.ulaval.ticketmaster.purchase.model.PurchaseViewModel;
import ca.ulaval.ticketmaster.springproxy.ProxyHttpSession;
import ca.ulaval.ticketmaster.springproxy.ProxyModel;

@Controller
@RequestMapping(value = "/purchase")
public class PurchaseController {

    // private static final Logger logger =
    // LoggerFactory.getLogger(HomeController.class);

    private BLBasket domaine;

    public PurchaseController() {
	domaine = new BLBasket();
    }

    @RequestMapping(value = "/Confirmation", method = RequestMethod.GET)
    public String confirmation(Model model) {
	return Page.Confirmation.toString();
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/Purchase", method = RequestMethod.GET)
    public String purchase(Model model, HttpSession session) {
	model.addAttribute("purchaseInfos", new PurchaseViewModel());
	model.addAttribute("paymentType", PaymentType.values());
	model.addAttribute("basketDisplay", (ArrayList<Ticket>) session.getAttribute("basketDisplay"));
	return Page.Purchase.toString();
    }

    @RequestMapping(value = "/Purchase", method = RequestMethod.POST)
    public String purchase(@Valid PurchaseViewModel purchaseModel, Model model, HttpSession session,
	    BindingResult result, HttpServletRequest request) {
	model.addAttribute("purchaseInfos", new PurchaseViewModel());
	model.addAttribute("paymentType", PaymentType.values());
	System.out.println(request.getLocalAddr());
	try {
	    domaine.purchase(ProxyHttpSession.create(session), ProxyModel.create(model), result);
	} catch (UnauthenticatedException e) {
	    return Page.Home.toString();
	} catch (InvalidFormExceptions e) {
	    return Page.Purchase.toString();
	} catch (InvalidPurchaseException e) {
	    return Page.Purchase.toString(); // return "redirect:/Purchase"; !?
	}

	return "redirect:/purchase/Confirmation";
    }

    @RequestMapping(value = "/Basket", method = RequestMethod.GET)
    public String basket(Model model, HttpSession session) {
	if (DAAuthentication.isLogged(ProxyHttpSession.create(session)))
	    return Page.Basket.toString();
	else {
	    model.addAttribute("errorMsg", new UnauthenticatedException().getErrorMsg());
	    return Page.Home.toString();
	}
    }

    @RequestMapping(value = "/addBasket", method = RequestMethod.POST)
    public String copyToBasket(@RequestParam("amount") int amount, @RequestParam("ticketId") String ticketId,
	    @RequestParam("eventId") String eventId, Model model, HttpSession session) {
	try {
	    domaine.copyToBasket(eventId, ticketId, amount, ProxyModel.create(model),
		    ProxyHttpSession.create(session));
	} catch (UnauthenticatedException e) {
	    // model.addAttribute("errorMsg", e.getErrorMsg());
	    return Page.Home.toString();
	}

	return Page.Basket.toString();
    }

    @RequestMapping(value = "/emptyBasket", method = RequestMethod.GET)
    public String emptyBasket(Model model, HttpSession session) {
	try {
	    domaine.removeAllFromBasket(ProxyHttpSession.create(session));
	} catch (UnauthenticatedException e) {
	    return Page.Home.toString();
	}

	return Page.Basket.toString();
    }
}
