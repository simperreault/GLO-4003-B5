package ca.ulaval.ticketmaster.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ca.ulaval.ticketmaster.dao.util.DataManager;

/**
 * Handles requests for the application related to Event
 */
@Controller
@RequestMapping(value = "/event")
public class EventController {
	
	private static final Logger logger = LoggerFactory.getLogger(EventController.class);
	private DataManager datamanager;
	
	public EventController(){
		 datamanager = new DataManager();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String detail(Model model) {
		
		model.addAttribute("EventList", datamanager.getEventList());
		model.addAttribute("currentPage", "EventList.jsp");
		return "MainFrame";
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String Event(@PathVariable int id, Model model) {
		
		model.addAttribute("ticketList", datamanager.loadAllTickets(id));
		model.addAttribute("currentPage", "TicketList.jsp");
		return "MainFrame";
	}
	
	@RequestMapping(value = "/{id1}/{id2}", method = RequestMethod.GET)
	public String detail(@PathVariable int id1 ,@PathVariable int id2, Model model) {
		
		model.addAttribute("ticket", datamanager.getTicket(id1, id2));
		model.addAttribute("currentPage", "detail.jsp");
		return "MainFrame";
	}
	
}
