package ca.ulaval.ticketmaster.events;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.validation.BindingResult;

import ca.ulaval.ticketmaster.dao.util.DataManager;
import ca.ulaval.ticketmaster.dao.util.SearchEngine;
import ca.ulaval.ticketmaster.events.model.Event;
import ca.ulaval.ticketmaster.events.model.EventConverter;
import ca.ulaval.ticketmaster.events.model.EventViewModel;
import ca.ulaval.ticketmaster.events.model.SearchViewModel;
import ca.ulaval.ticketmaster.events.model.SportType;
import ca.ulaval.ticketmaster.execptions.UnauthorizedException;
import ca.ulaval.ticketmaster.home.DAAuthentication;
import ca.ulaval.ticketmaster.home.Page;
import ca.ulaval.ticketmaster.springproxy.ProxyHttpSession;
import ca.ulaval.ticketmaster.springproxy.ProxyModel;

public class BLEvent {
	private DataManager datamanager;
	private SearchEngine searchEngine;

	public BLEvent() {
		datamanager = new DataManager();
		searchEngine = new SearchEngine();
	}

	public void getEventList(ProxyModel model) {
		model.addAttribute("search", new SearchViewModel());
		model.addAttribute("sportList", SportType.values());
		model.addAttribute("EventList", searchEngine.findAllEvents());

		List<String> teamList = searchEngine.GetAllTeams();
		model.addAttribute("teamList", teamList);

		LinkedHashMap<Integer, String> days = new LinkedHashMap<Integer, String>();
		DateTime date = new DateTime();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		for (int i = 1; i < 10; ++i) {
			date = date.plusDays(1);
			days.put(i, "Avant le " + sdf.format(date.toDate()));
		}
		model.addAttribute("dayList", days);
	}

	public void getTickedEvent(String idEvent, ProxyModel model, ProxyHttpSession session) throws UnauthorizedException {
		
		model.addAttribute("eventID", UUID.fromString(idEvent));
		//model.addAttribute("ticketList", datamanager.findAllTickets(UUID.fromString(idEvent)));
		model.addAttribute("ticketList", searchEngine.regroupSimilarTickets(UUID.fromString(idEvent)));
		
		if (!DAAuthentication.isLogged(session))
			throw new UnauthorizedException();
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

	public void search(SearchViewModel viewModel, ProxyModel model) {
		model.addAttribute("search", viewModel);
		model.addAttribute("sportList", SportType.values());
		List<String> teamList = searchEngine.GetAllTeams();
		model.addAttribute("teamList", teamList);

		LinkedHashMap<Integer, String> days = new LinkedHashMap<Integer, String>();
		DateTime date = new DateTime();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		for (int i = 1; i < 10; ++i) {
			date = date.plusDays(1);
			days.put(i, "Avant le " + sdf.format(date.toDate()));
		}
		model.addAttribute("dayList", days);

		List<Event> events = searchEngine.SearchWithCriterias(viewModel.sport, viewModel.days, viewModel.team);
		model.addAttribute("EventList", events);
	}

	public String deleteEvent(String eventId) {
		datamanager.deleteEvent(UUID.fromString(eventId));
		return "redirect:/event/list";
	}

}
