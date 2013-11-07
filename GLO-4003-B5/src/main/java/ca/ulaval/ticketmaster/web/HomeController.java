package ca.ulaval.ticketmaster.web;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ca.ulaval.ticketmaster.model.enums.PaymentType;
import ca.ulaval.ticketmaster.web.DomaineAffaire.DAAuthentication;
import ca.ulaval.ticketmaster.web.DomaineAffaire.Page;
import ca.ulaval.ticketmaster.web.DomaineAffaire.proxy.ProxyHttpSession;
import ca.ulaval.ticketmaster.web.viewmodels.PurchaseViewModel;

/**
 * Handles requests for the application home page.
 */
@Controller
// @SessionAttributes
public class HomeController {

    // private static final Logger logger =
    // LoggerFactory.getLogger(HomeController.class);
    //private DAUser domaine;

    public HomeController() {
	//domaine = new DAUser();
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String MainFrame(Model model) {
	return "Home";
    }

    @RequestMapping(value = "/Home", method = RequestMethod.GET)
    public String home(Model model) {
	return "Home";
    }
    
    @RequestMapping(value = "/purchase", method = RequestMethod.GET)
    public String Purchase(Model model) {
    	model.addAttribute("purchaseInfos", new PurchaseViewModel());
    	model.addAttribute("paymentType", PaymentType.values());
	return "Purchase";
    }

    @RequestMapping(value = "/Basket", method = RequestMethod.GET)
    public String Basket(Model model, HttpSession session) {
	// model.addAttribute("currentPage", "Basket.jsp");
	if (DAAuthentication.isLogged(ProxyHttpSession.create(session)))
	    //return "Basket";
		return Page.Basket.toString();
	else
	    //return "Home";
		return Page.Home.toString();
    }

}
