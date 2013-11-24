package ca.ulaval.ticketmaster.home;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

    // private static final Logger logger =
    // LoggerFactory.getLogger(HomeController.class);

    public HomeController() {
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String mainFrame(Model model) {
	return Page.Home.toString();
    }

    @RequestMapping(value = "/Home", method = RequestMethod.GET)
    public String home(Model model) {
	return Page.Home.toString();
    }

}
