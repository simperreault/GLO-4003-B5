package ca.ulaval.ticketmaster.web;

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
import ca.ulaval.ticketmaster.model.Ticket;
import ca.ulaval.ticketmaster.web.converter.TicketConverter;
import ca.ulaval.ticketmaster.web.viewmodels.TicketViewModel;

/**
 * Handles requests for the application related to Ticket
 */
@Controller
@RequestMapping(value = "/ticket")
// TODO Move what is related to ticket in EventController here
public class TicketController {

	private static final Logger logger = LoggerFactory.getLogger(TicketController.class);
	private DataManager datamanager;

	public TicketController(){
		datamanager = new DataManager();
	}

	@RequestMapping(value = "/add/{eventId}", method = RequestMethod.GET)
	public String create(@PathVariable int eventId, Model model) {
		model.addAttribute("ticket", new TicketViewModel(new Event(eventId)));
		//model.addAttribute("currentPage", "TicketAdd.jsp");
		return "TicketAdd";
	}

	@RequestMapping(value = "/add/{eventId}", method = RequestMethod.POST)
	public String create(@PathVariable int eventId,
			@Valid TicketViewModel viewmodel, BindingResult result, Model model) {
		if (result.hasErrors()) {  	        
			model.addAttribute("error", result.getAllErrors());
			model.addAttribute("ticket", viewmodel);
			//model.addAttribute("currentPage", "TicketAdd.jsp");
			return "TicketAdd";
		}

		viewmodel.setEvent(datamanager.getEvent(eventId));
		for (int i= 0; i < viewmodel.howMany; ++i) {
			Ticket ticket = TicketConverter.convert(viewmodel, datamanager);
			datamanager.saveTicket(ticket); // TODO What if save failed ?
		}
		return "redirect:/event/" + eventId;
	}
	
	@RequestMapping(value = "/delete/{eventId}/{ticketId}", method = RequestMethod.GET)
	public String delete(@PathVariable int eventId, @PathVariable int ticketId, Model model) {
		datamanager.deleteTicket(eventId,ticketId);
		return "redirect:/event/{eventId}";
	}
}
