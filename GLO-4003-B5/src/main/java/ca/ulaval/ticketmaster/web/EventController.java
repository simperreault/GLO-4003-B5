package ca.ulaval.ticketmaster.web;

import java.text.ParseException;
import java.util.UUID;

import javax.servlet.http.HttpSession;
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
	// private DataManager datamanager;
	private GlobalController controller;

	public EventController() {
		controller = new GlobalController();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Model model, HttpSession session) {
		return controller.list(model, session);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String Event(@PathVariable String id, Model model, HttpSession session) {
		return controller.getTickedEvent(id, model, session);
	}

	@RequestMapping(value = "/{id1}/{id2}", method = RequestMethod.GET)
	public String detail(@PathVariable String id1, @PathVariable String id2, Model model, HttpSession session) {
		return controller.getTickedEvent(id1, id2, model, session);
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String create(Model model, HttpSession session) {

		return controller.getAddEvent(model, session);
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String create(@Valid EventViewModel viewmodel, BindingResult result, Model model, HttpSession session) {
		return controller.addEvent(viewmodel, result, model, session);
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public String delete(@PathVariable String id, Model model,HttpSession session) {
		return controller.deleteEvent(id, session);
	}

}
