package ca.ulaval.billet;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ca.ulaval.billet.dataUtil.DataManager;
import ca.ulaval.billet.model.Event;
import ca.ulaval.billet.model.Ticket;
import ca.ulaval.billet.model.Event.Sport;

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
	
	@RequestMapping(value = "/EventList", method = RequestMethod.GET)
	public String detail(Model model) {
		
		model.addAttribute("EventList", datamanager.getEventList());
		model.addAttribute("currentPage", "EventList.jsp");
		return "MainFrame";
	}
	
	@RequestMapping(value = "/Event{id}", method = RequestMethod.GET)
	public String Event(@PathVariable int id, Model model) {
		
		model.addAttribute("ticketList", datamanager.loadAllTickets(id));
		model.addAttribute("currentPage", "TicketList.jsp");
		return "MainFrame";
	}
	
	@RequestMapping(value = "/Event{id1}/Ticket{id2}", method = RequestMethod.GET)
	public String detail(@PathVariable int id1,@PathVariable int id2, Model model) {
		
		model.addAttribute("ticket", datamanager.getTicket(id1, id2));
		model.addAttribute("currentPage", "detail.jsp");
		return "MainFrame";
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
