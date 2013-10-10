package ca.ulaval.ticketmaster.web;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ca.ulaval.ticketmaster.web.viewmodels.TicketViewModel;

/**
 * Handles requests for the application related to Ticket
 */
@Controller
@RequestMapping(value = "/ticket")
// TODO Move what is related to ticket in EventController here
public class TicketController {

	// private static final Logger logger =
	// LoggerFactory.getLogger(TicketController.class);
	private GlobalController controller;

	public TicketController() {
		controller = new GlobalController();
	}

	@RequestMapping(value = "/add/{eventId}", method = RequestMethod.GET)
	public String create(@PathVariable String eventId, Model model, HttpSession session) {
		return controller.getAddTicket(eventId, model, session);
	}

	@RequestMapping(value = "/add/{eventId}", method = RequestMethod.POST)
	public String create(@PathVariable String eventId, @Valid TicketViewModel viewmodel, BindingResult result,
			Model model, HttpSession session) {
		return controller.addTicket(eventId, viewmodel, result, model, session);
	}

	@RequestMapping(value = "/delete/{eventId}/{ticketId}", method = RequestMethod.GET)
	public String delete(@PathVariable String eventId, @PathVariable String ticketId, Model model, HttpSession session) {
		return controller.deleteTicket(eventId, ticketId, session);
	}
}
