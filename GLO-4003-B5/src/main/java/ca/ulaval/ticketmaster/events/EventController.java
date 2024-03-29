package ca.ulaval.ticketmaster.events;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ca.ulaval.ticketmaster.events.model.EventViewModel;
import ca.ulaval.ticketmaster.events.model.SearchViewModel;
import ca.ulaval.ticketmaster.exceptions.InvalidFormExceptions;
import ca.ulaval.ticketmaster.exceptions.UnauthorizedException;
import ca.ulaval.ticketmaster.home.Page;
import ca.ulaval.ticketmaster.springproxy.ProxyHttpSession;
import ca.ulaval.ticketmaster.springproxy.ProxyModel;

/**
 * Handles requests for the application related to Event
 */
@Controller
@RequestMapping(value = "/event")
public class EventController {

    private BLEvent domaine;

    public EventController() {
	domaine = new BLEvent();
    }

    public EventController(BLEvent e) {
	domaine = e;
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model, SearchViewModel viewmodel, HttpSession session) {
	domaine.preLoadEvents(viewmodel, ProxyModel.create(model), ProxyHttpSession.create(session));
	return Page.EventList.toString();
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public String list(SearchViewModel viewmodel, Model model) {
	domaine.search(viewmodel, ProxyModel.create(model));
	return Page.EventList.toString();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String event(@PathVariable String id, Model model, HttpSession session,
	    @RequestParam(value = "confirmation", defaultValue = "") String confirmation,
	    @RequestParam(value = "error", defaultValue = "") String error) {
	domaine.getTicketsEvent(id, ProxyModel.create(model), ProxyHttpSession.create(session));
	if (confirmation.equals("1"))
	    model.addAttribute("confirmationMsg", "Le(s) billet(s) a/ont bien été ajouté(s) à votre panier");
	if (error.equals("1"))
	    model.addAttribute("errorMsg",
		    "Vous devez être authentifié pour ajouter des billets à votre panier.");

	return Page.TicketList.toString();
    }

    @RequestMapping(value = "/{id1}/{id2}", method = RequestMethod.GET)
    public String detail(@PathVariable String id1, @PathVariable String id2, Model model) {
	domaine.getTicketsEvent(id1, id2, ProxyModel.create(model));
	return Page.Detail.toString();
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String create(Model model, HttpSession session) {
	try {
	    domaine.getAddEvent(ProxyModel.create(model), ProxyHttpSession.create(session));
	} catch (UnauthorizedException e) {
	    return Page.Error403.toString();
	}
	return Page.EventAdd.toString();
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String create(@Valid EventViewModel viewmodel, BindingResult result, Model model,
	    HttpSession session) {
	try {
	    domaine.addEvent(viewmodel, result, ProxyModel.create(model), ProxyHttpSession.create(session));
	} catch (UnauthorizedException | InvalidFormExceptions e) {
	    return Page.EventAdd.toString();
	}
	return "redirect:/event/list";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable String id, Model model, HttpSession session) {
	domaine.deleteEvent(id);
	return "redirect:/event/list";
    }

}
