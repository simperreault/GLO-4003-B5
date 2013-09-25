package ca.ulaval.ticketmaster.web;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ca.ulaval.ticketmaster.dao.util.DataManager;
import ca.ulaval.ticketmaster.model.Event;
import ca.ulaval.ticketmaster.model.Ticket;
import ca.ulaval.ticketmaster.model.User;
import ca.ulaval.ticketmaster.model.Event.Sport;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	private DataManager datamanager;
	
	public HomeController(){
		 datamanager = new DataManager();
	}
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/List", method = RequestMethod.GET)
	public String list(Locale locale, Model model) {
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		ArrayList<Ticket> list = new ArrayList<Ticket>();
		/*list.add(t1);
		list.add(t2);*/
		System.out.println(datamanager.getEventList().size());
		for(int i = 0; i < datamanager.getEventList().size(); ++i){
			list.addAll(datamanager.getEventList().get(i).getTicketList());
		}
		
		model.addAttribute("Ticketlist", list );
		model.addAttribute("currentPage", "list.jsp");
		return "MainFrame";
	}
	
	@RequestMapping(value = "/addTicket", method = RequestMethod.GET)
	public String showAddTicket(Locale locale, Model model, Ticket ticket) {
		model.addAttribute("currentPage", "addTicket.jsp");
		model.addAttribute("ticket", new Ticket());
		return "MainFrame";
	}
	
	@RequestMapping(value = "/addTicket", method = RequestMethod.POST)
	public String validateAddTicket(Model model) {
		return "redirect:/event/list";
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String MainFrame(Locale locale, Model model) {
		model.addAttribute("currentPage", "Home.jsp");
		return "MainFrame";
	}
	
	@RequestMapping(value = "/Home", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		model.addAttribute("currentPage", "Home.jsp");
		return "MainFrame";
	}
	
}
