package ca.ulaval.ticketmaster.events;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ca.ulaval.ticketmaster.events.model.EventViewModel;
import ca.ulaval.ticketmaster.events.model.SearchViewModel;
import ca.ulaval.ticketmaster.execptions.UnauthorizedException;
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

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) {
	domaine.getEventList(ProxyModel.create(model));
	return Page.EventList.toString();
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public String list(SearchViewModel viewmodel, Model model) {
	domaine.search(viewmodel, ProxyModel.create(model));
	return Page.EventList.toString();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String Event(@PathVariable String id, Model model, HttpSession session) {
	try {
	    domaine.getTickedEvent(id, ProxyModel.create(model), ProxyHttpSession.create(session));
	} catch (UnauthorizedException e) {
	    return Page.Home.toString();
	}
	return Page.TicketList.toString();
    }

    @RequestMapping(value = "/{id1}/{id2}", method = RequestMethod.GET)
    public String detail(@PathVariable String id1, @PathVariable String id2, Model model) {
	return domaine.getTickedEvent(id1, id2, ProxyModel.create(model));
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String create(Model model, HttpSession session) {
	return domaine.getAddEvent(ProxyModel.create(model), ProxyHttpSession.create(session));
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String create(@Valid EventViewModel viewmodel, BindingResult result, Model model,
	    HttpSession session) {
	return domaine
		.addEvent(viewmodel, result, ProxyModel.create(model), ProxyHttpSession.create(session));
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable String id, Model model, HttpSession session) {
	return domaine.deleteEvent(id);
    }

}