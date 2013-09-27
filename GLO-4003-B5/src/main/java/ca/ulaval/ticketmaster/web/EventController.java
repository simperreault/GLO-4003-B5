package ca.ulaval.ticketmaster.web;

import java.text.DateFormat;
import java.util.Locale;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ca.ulaval.ticketmaster.dao.util.DataManager;
import ca.ulaval.ticketmaster.model.Event;
import ca.ulaval.ticketmaster.web.converter.EventConverter;
import ca.ulaval.ticketmaster.web.converter.EventConverterTest;
import ca.ulaval.ticketmaster.web.viewmodels.EventViewModel;

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
		
		model.addAttribute("eventID", id);
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
	
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String create(Model model) {
		model.addAttribute("event", new EventViewModel());
		model.addAttribute("currentPage", "EventAdd.jsp");
		return "MainFrame";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String create(@Valid EventViewModel viewmodel, BindingResult result, Model model) {
		if (result.hasErrors()) {  	        
			model.addAttribute("error", result.getAllErrors());
			model.addAttribute("event", viewmodel);
			model.addAttribute("currentPage", "EventAdd.jsp");
			return "MainFrame";
		}

		// Save
		Event event = EventConverter.convert(viewmodel, datamanager);
		datamanager.saveEvent(event);
		return "redirect:/event/list";
	}
	
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public String delete(@PathVariable int id, Model model) {
		datamanager.deleteEvent(id);
		return "redirect:/event/list";
	}
	
}
