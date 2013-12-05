package ca.ulaval.ticketmaster.events.tickets;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ca.ulaval.ticketmaster.events.tickets.model.TicketViewModel;
import ca.ulaval.ticketmaster.execptions.UnauthenticatedException;
import ca.ulaval.ticketmaster.execptions.UnauthorizedException;
import ca.ulaval.ticketmaster.home.Page;
import ca.ulaval.ticketmaster.purchase.BLBasket;
import ca.ulaval.ticketmaster.springproxy.ProxyHttpSession;
import ca.ulaval.ticketmaster.springproxy.ProxyModel;

/**
 * Handles requests for the application related to Ticket
 */
@Controller
@RequestMapping(value = "/ticket")
// TODO Move what is related to ticket in EventController here
public class TicketController {

	// private static final Logger logger =
	// LoggerFactory.getLogger(TicketController.class);
	private BLTicket domain;
	private BLBasket basket;

	public TicketController() {
		domain = new BLTicket();
		basket = new BLBasket();
	}

	@RequestMapping(value = "/add/{eventId}", method = RequestMethod.GET)
	public String create(@PathVariable String eventId, Model model, HttpSession session) {
		try {
			domain.getAddTicket(eventId, ProxyModel.create(model), ProxyHttpSession.create(session));
		} catch (UnauthenticatedException e) {
			return Page.Home.toString();
		}
		return Page.TicketAdd.toString();
	}

	@RequestMapping(value = "/add/{eventId}", method = RequestMethod.POST)
	public String create(@PathVariable String eventId, @Valid TicketViewModel viewmodel,
			BindingResult result, Model model, HttpSession session) {
		try {
			domain.addTicket(eventId, viewmodel, result, ProxyModel.create(model),
					ProxyHttpSession.create(session));
		} catch (UnauthorizedException e) {
			Page.Home.toString();
		}
		return "redirect:/event/" + eventId;
	}

	@RequestMapping(value = "/delete/{eventId}/{ticketId}", method = RequestMethod.GET)
	public String delete(@PathVariable String eventId, @PathVariable String ticketId, Model model,
			HttpSession session) {
		domain.deleteTicket(eventId, ticketId, ProxyHttpSession.create(session));
		return "redirect:/event/" + eventId;
	}

	@RequestMapping(value = "/addBasket/{eventId}", method = RequestMethod.POST)
	public String addToBasketMultiple(@PathVariable String eventId,
			@RequestParam("ticketId") String ticketId,
			@RequestParam("nbSimilarTickets") String nbSimilarTickets, Model model, HttpSession session) {
		try {
			basket.addMultipleTicketsToBasket(eventId, ticketId, nbSimilarTickets,
					ProxyModel.create(model), ProxyHttpSession.create(session));
		} catch (UnauthenticatedException e) {
			return Page.TicketList.toString();
		}
		return "redirect:/event/" + eventId;
	}

	@RequestMapping(value = "/deleteBasket/{eventId}/{ticketId}", method = RequestMethod.GET)
	public String removeFromBasket(@PathVariable String eventId, @PathVariable String ticketId, Model model,
			HttpSession session) {
		basket.removeFromBasket(eventId, ticketId, ProxyHttpSession.create(session));
		return "redirect:/Basket";
	}

	@RequestMapping(value = "/copyBasket/{eventId}/{ticketId}", method = RequestMethod.GET)
	public String copyToBasket(@PathVariable String eventId, @PathVariable String ticketId, Model model,
			@RequestParam("amount") int amount, HttpSession session) {
		try {
			basket.copyToBasket(eventId, ticketId, amount, ProxyModel.create(model),
					ProxyHttpSession.create(session));
		} catch (UnauthenticatedException e) {
			return Page.Home.toString();
		}

		return Page.Basket.toString();
	}

	@RequestMapping(value = "/buySingleTicket/{eventId}/{ticketId}", method = RequestMethod.GET)
	public String buySingleTicket(@PathVariable String eventId, @PathVariable String ticketId, Model model,
			HttpSession session) {
		try {
			basket.buySingleTicket(eventId, ticketId, ProxyHttpSession.create(session));
		} catch (UnauthenticatedException e) {
			return Page.Home.toString();
		}
		return "redirect:/purchase/Purchase";
	}

}
