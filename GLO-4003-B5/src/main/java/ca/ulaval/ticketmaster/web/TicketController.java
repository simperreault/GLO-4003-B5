package ca.ulaval.ticketmaster.web;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ca.ulaval.ticketmaster.web.DomaineAffaire.DABasket;
import ca.ulaval.ticketmaster.web.DomaineAffaire.DATicket;
import ca.ulaval.ticketmaster.web.DomaineAffaire.proxy.ProxyHttpSession;
import ca.ulaval.ticketmaster.web.DomaineAffaire.proxy.ProxyModel;
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
	private DATicket domain;
	private DABasket basket;

	public TicketController() {
		domain = new DATicket();
		basket = new DABasket();
	}

	@RequestMapping(value = "/add/{eventId}", method = RequestMethod.GET)
	public String create(@PathVariable String eventId, Model model, HttpSession session) {
		return domain.getAddTicket(eventId, ProxyModel.create(model), ProxyHttpSession.create(session));
	}

	@RequestMapping(value = "/add/{eventId}", method = RequestMethod.POST)
	public String create(@PathVariable String eventId, @Valid TicketViewModel viewmodel, BindingResult result,
			Model model, HttpSession session) {
		return domain.addTicket(eventId, viewmodel, result, ProxyModel.create(model), ProxyHttpSession.create(session));
	}

	@RequestMapping(value = "/delete/{eventId}/{ticketId}", method = RequestMethod.GET)
	public String delete(@PathVariable String eventId, @PathVariable String ticketId, Model model, HttpSession session) {
		return domain.deleteTicket(eventId, ticketId, ProxyHttpSession.create(session));
	}

	@RequestMapping(value = "/addBasket/{eventId}/{ticketId}", method = RequestMethod.GET)
	public String addToBasket(@PathVariable String eventId, @PathVariable String ticketId, Model model,
			HttpSession session) {
		return basket.addToBasket(eventId, ticketId, ProxyModel.create(model), ProxyHttpSession.create(session));
	}

	//Ajoute plusieurs billets au panier
	@RequestMapping(value = "/addBasket/{eventId}/{ticketId}/{nbSimilarTickets}", method = RequestMethod.GET)
	public String addToBasketMultiple(@PathVariable String eventId, @PathVariable String ticketId,
			@PathVariable String nbSimilarTickets,
			Model model,
			HttpSession session) {
		
		return basket.addMultipleTicketsToBasket(eventId, ticketId, nbSimilarTickets, ProxyModel.create(model), ProxyHttpSession.create(session));
	}

	@RequestMapping(value = "/deleteBasket/{eventId}/{ticketId}", method = RequestMethod.GET)
	public String removeFromBasket(@PathVariable String eventId, @PathVariable String ticketId, Model model,
			HttpSession session) {
		return basket.removeFromBasket(eventId, ticketId, ProxyModel.create(model), ProxyHttpSession.create(session));
	}

	@RequestMapping(value = "/copyBasket/{eventId}/{ticketId}", method = RequestMethod.GET)
	public String copyToBasket(@PathVariable String eventId, @PathVariable String ticketId, Model model,
			HttpSession session) {
		return basket.copyToBasket(eventId, ticketId, ProxyHttpSession.create(session));
	}
	
	@RequestMapping(value = "/buySingleTicket/{eventId}/{ticketId}", method = RequestMethod.GET)
	public String buySingleTicket(@PathVariable String eventId, @PathVariable String ticketId, Model model,
			HttpSession session) {
		return basket.buySingleTicket(eventId, ticketId, ProxyHttpSession.create(session));
	}

}
