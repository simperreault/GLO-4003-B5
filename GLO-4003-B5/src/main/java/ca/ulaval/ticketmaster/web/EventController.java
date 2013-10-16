package ca.ulaval.ticketmaster.web;

import java.text.ParseException;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ca.ulaval.ticketmaster.dao.util.DataManager;
import ca.ulaval.ticketmaster.model.Event;
import ca.ulaval.ticketmaster.model.enums.SportType;
import ca.ulaval.ticketmaster.web.converter.EventConverter;
import ca.ulaval.ticketmaster.web.viewmodels.EventViewModel;

/**
 * Handles requests for the application related to Event
 */
@Controller
@RequestMapping(value = "/event")
public class EventController {

    // private static final Logger logger =
    // LoggerFactory.getLogger(EventController.class);
    private DataManager datamanager;

    public EventController() {
	datamanager = new DataManager();
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) {
	System.out.println("/list elem count : " + datamanager.findAllEvents().size());
	model.addAttribute("EventList", datamanager.findAllEvents());
	return "EventList";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String Event(@PathVariable String id, Model model) {
	System.out.println(id);
	model.addAttribute("eventID", UUID.fromString(id));
	model.addAttribute("ticketList", datamanager.findAllTickets(UUID.fromString(id)));
	return "TicketList";
    }

    @RequestMapping(value = "/{id1}/{id2}", method = RequestMethod.GET)
    public String detail(@PathVariable String id1, @PathVariable String id2, Model model) {
	model.addAttribute("ticket", datamanager.findTicket(UUID.fromString(id1), UUID.fromString(id2)));
	return "detail";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String create(Model model) {
	model.addAttribute("event", new EventViewModel());
	model.addAttribute("sportList", SportType.values());
	return "EventAdd";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String create(@Valid EventViewModel viewmodel, BindingResult result, Model model) {
	if (result.hasErrors()) {
	    model.addAttribute("sportList", SportType.values());
	    model.addAttribute("error", result.getAllErrors());
	    model.addAttribute("event", viewmodel);
	    return "EventAdd";
	}

	// Save
	try {
	    Event event = EventConverter.convert(viewmodel, datamanager);
	    datamanager.saveEvent(event);
	} catch (ParseException e) {
	    // Error happen, This event will not be saved
	    model.addAttribute("sportList", SportType.values());
	    model.addAttribute("error", "Erreur dans le format de la date (dd/mm/yyyy HH:MM)");
	    model.addAttribute("event", viewmodel);
	    return "EventAdd";
	}
	return "redirect:/event/list";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable String id, Model model) {
	datamanager.deleteEvent(UUID.fromString(id));
	return "redirect:/event/list";
    }

}
