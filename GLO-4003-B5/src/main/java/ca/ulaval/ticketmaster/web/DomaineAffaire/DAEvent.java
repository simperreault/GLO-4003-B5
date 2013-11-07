package ca.ulaval.ticketmaster.web.DomaineAffaire;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.validation.BindingResult;

import ca.ulaval.ticketmaster.dao.util.DataManager;
import ca.ulaval.ticketmaster.model.Event;
import ca.ulaval.ticketmaster.model.enums.SportType;
import ca.ulaval.ticketmaster.web.DomaineAffaire.proxy.ProxyHttpSession;
import ca.ulaval.ticketmaster.web.DomaineAffaire.proxy.ProxyModel;
import ca.ulaval.ticketmaster.web.converter.EventConverter;
import ca.ulaval.ticketmaster.web.viewmodels.EventViewModel;
import ca.ulaval.ticketmaster.web.viewmodels.SearchViewModel;

public class DAEvent {
	private DataManager datamanager;

	public DAEvent() {
		datamanager = new DataManager();
	}

	public String getEventList(ProxyModel model) {
		model.addAttribute("search", new SearchViewModel());
		model.addAttribute("sportList", SportType.values());
		model.addAttribute("EventList", datamanager.findAllEvents());

		List<String> teamList = datamanager.GetAllTeams();
		model.addAttribute("teamList", teamList);

		LinkedHashMap<Integer, String> days = new LinkedHashMap<Integer, String>();
		DateTime date = new DateTime();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		for (int i = 1; i < 10; ++i) {
			date = date.plusDays(1);
			days.put(i, "Avant le " + sdf.format(date.toDate()));
		}
		model.addAttribute("dayList", days);

		return Page.EventList.toString();
	}

	public String getTickedEvent(String idEvent, ProxyModel model) {
		model.addAttribute("eventID", UUID.fromString(idEvent));
		model.addAttribute("ticketList", datamanager.findAllTickets(UUID.fromString(idEvent)));
		return Page.TicketList.toString();
	}

	public String getTickedEvent(String idEvent1, String idEvent2, ProxyModel model) {
		model.addAttribute("ticket",
				datamanager.findTicket(UUID.fromString(idEvent1), UUID.fromString(idEvent2)));
		return Page.Detail.toString();
	}

	public String getAddEvent(ProxyModel model, ProxyHttpSession session) {
		if (DAAuthentication.isAdmin(session)) {
			model.addAttribute("event", new EventViewModel());
			model.addAttribute("sportList", SportType.values());
			return Page.EventAdd.toString();
		} else {
			return Page.Error403.toString();
		}
	}

	public String addEvent(EventViewModel viewmodel, BindingResult result, ProxyModel model,
			ProxyHttpSession session) {
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

	public String search(SearchViewModel viewModel, ProxyModel model) {
		model.addAttribute("search", viewModel);
		model.addAttribute("sportList", SportType.values());
		List<String> teamList = datamanager.GetAllTeams();
		model.addAttribute("teamList", teamList);

		LinkedHashMap<Integer, String> days = new LinkedHashMap<Integer, String>();
		DateTime date = new DateTime();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		for (int i = 1; i < 10; ++i) {
			date = date.plusDays(1);
			days.put(i, "Avant le " + sdf.format(date.toDate()));
		}
		model.addAttribute("dayList", days);

		List<Event> events = datamanager.SearchWithCriterias(viewModel.sport, viewModel.days, viewModel.team);
		model.addAttribute("EventList", events);

		if (events.size() == 0)
			model.addAttribute("message", "Aucun �v�nement n'a �t� trouv�");

		return Page.EventList.toString();
	}

	public String deleteEvent(String eventId) {
		datamanager.deleteEvent(UUID.fromString(eventId));
		return "redirect:/event/list";
	}

}