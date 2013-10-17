package ca.ulaval.ticketmaster.web.DomaineAffaire;

import java.text.ParseException;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import ca.ulaval.ticketmaster.dao.util.DataManager;
import ca.ulaval.ticketmaster.model.Event;
import ca.ulaval.ticketmaster.model.enums.SportType;
import ca.ulaval.ticketmaster.web.converter.EventConverter;
import ca.ulaval.ticketmaster.web.viewmodels.EventViewModel;

public class DAEvent {
	private DataManager datamanager;

	public DAEvent() {
		datamanager = new DataManager();
	}

	public String getEventList(Model model) {
		model.addAttribute("search", new SearchViewModel());
		model.addAttribute("sportList", SportType.values());
		model.addAttribute("EventList", datamanager.findAllEvents());
		return Page.EventList.toString();
	}

	public String getTickedEvent(String idEvent, Model model) {
		model.addAttribute("eventID", UUID.fromString(idEvent));
		model.addAttribute("ticketList", datamanager.findAllTickets(UUID.fromString(idEvent)));
		return Page.TicketList.toString();
	}

	public String getTickedEvent(String idEvent1, String idEvent2, Model model) {
		model.addAttribute("ticket", datamanager.findTicket(UUID.fromString(idEvent1), UUID.fromString(idEvent2)));
		return Page.Detail.toString();
	}
	
	public String getAddEvent(Model model, HttpSession session) {
		if (DAAuthentication.isAdmin(session)) {
			model.addAttribute("event", new EventViewModel());
			model.addAttribute("sportList", SportType.values());
			return Page.EventAdd.toString();
		} else {
			return Page.Error403.toString();
		}
	}

	public String addEvent(EventViewModel viewmodel, BindingResult result, Model model, HttpSession session) {
		if (DAAuthentication.isAdmin(session)) {
			if (result.hasErrors()) {
				model.addAttribute("sportList", SportType.values());
				model.addAttribute("error", result.getAllErrors());
				model.addAttribute("event", viewmodel);
				return Page.EventAdd.toString();
			}

			try {
				Event event = EventConverter.convert(viewmodel, datamanager);
				datamanager.saveEvent(event);
			} catch (ParseException e) {
				// Error happen, This event will not be saved
				model.addAttribute("sportList", SportType.values());
				model.addAttribute("error", "Erreur dans le format de la date (dd/mm/yyyy HH:MM)");
				model.addAttribute("event", viewmodel);
				return Page.EventAdd.toString();
			}
			return "redirect:/event/list";

		} else {
			return Page.Error403.toString();
		}
	}

	public String search(SearchViewModel viewModel, BindingResult result, Model model) {
		model.addAttribute("search", viewModel);
		model.addAttribute("sportList", SportType.values());
		model.addAttribute("EventList", datamanager.SearchWithCriterias(viewModel.sport, viewModel.days, viewModel.team));
		return Page.EventList.toString();
	}

	public String deleteEvent(String eventId) {
		datamanager.deleteEvent(UUID.fromString(eventId));
		return "redirect:/event/list";
	}

}
