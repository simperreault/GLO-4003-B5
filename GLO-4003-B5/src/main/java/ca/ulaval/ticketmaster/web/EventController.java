package ca.ulaval.ticketmaster.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ca.ulaval.ticketmaster.web.DomaineAffaire.DAEvent;
import ca.ulaval.ticketmaster.web.DomaineAffaire.DATicket;
import ca.ulaval.ticketmaster.web.viewmodels.EventViewModel;
import ca.ulaval.ticketmaster.web.viewmodels.SearchViewModel;

/**
 * Handles requests for the application related to Event
 */
@Controller
@RequestMapping(value = "/event")
public class EventController {

	// private static final Logger logger =
	// LoggerFactory.getLogger(EventController.class);
	// private DataManager datamanager;
	private DAEvent domaine;
	
	public EventController() {
		domaine = new DAEvent();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Model model) { // @RequestParam("sportType") String sportType,
		System.out.println("getParam");
		/**String sportType = null;
		if (sportType != null) {
			System.out.println("avec sport");
			return controller.search(model, sportType);
		}
		else
		{
			System.out.println("pas de sport");
			System.out.println( controller.list(model));
		}**/
			
			return controller.list(model);
		//return domaine.getEventList(model);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String Event(@PathVariable String id, Model model) {
		return domaine.getTickedEvent(id, model);
	}

	@RequestMapping(value = "/{id1}/{id2}", method = RequestMethod.GET)
	public String detail(@PathVariable String id1, @PathVariable String id2, Model model ) {
		return domaine.getTickedEvent(id1, id2, model);
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String create(Model model, HttpSession session) {
		return domaine.getAddEvent(model, session);
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String create(@Valid EventViewModel viewmodel, BindingResult result, Model model, HttpSession session) {
		return domaine.addEvent(viewmodel, result, model, session);
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public String delete(@PathVariable String id, Model model,HttpSession session) {
		return domaine.deleteEvent(id);
	}

}
