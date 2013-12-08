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
import ca.ulaval.ticketmaster.exceptions.InvalidFormExceptions;
import ca.ulaval.ticketmaster.exceptions.UnauthorizedException;
import ca.ulaval.ticketmaster.home.DAAuthentication;
import ca.ulaval.ticketmaster.springproxy.ProxyHttpSession;
import ca.ulaval.ticketmaster.springproxy.ProxyModel;
import ca.ulaval.ticketmaster.users.model.User;

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

	public void getTicketsEvent(String idEvent, ProxyModel model, ProxyHttpSession session) {
		System.out.println("idEvent : " + idEvent);
		model.addAttribute("eventID", UUID.fromString(idEvent));
		model.addAttribute("ticketList", searchEngine.regroupSimilarTickets(UUID.fromString(idEvent)));
	}

	public void getTicketsEvent(String idEvent1, String idEvent2, ProxyModel model) {
		model.addAttribute("ticket",
				datamanager.findTicket(UUID.fromString(idEvent1), UUID.fromString(idEvent2)));
	}

	public void getAddEvent(ProxyModel model, ProxyHttpSession session) throws UnauthorizedException {
		if (!DAAuthentication.isAdmin(session))
			throw new UnauthorizedException();
			
			model.addAttribute("event", new EventViewModel());
			model.addAttribute("sportList", SportType.values());
	}

	public void addEvent(EventViewModel viewmodel, BindingResult result, ProxyModel model,
			ProxyHttpSession session) throws UnauthorizedException, InvalidFormExceptions {
		if (!DAAuthentication.isAdmin(session))
			throw new UnauthorizedException();
			
			if (result.hasErrors()) {
				model.addAttribute("sportList", SportType.values());
				model.addAttribute("error", result.getAllErrors());
				model.addAttribute("event", viewmodel);
				throw new InvalidFormExceptions();
			}

			try {
				Event event = EventConverter.convert(viewmodel, datamanager);
				datamanager.saveEvent(event);
			} catch (ParseException e) {
				// Error happen, This event will not be saved
				model.addAttribute("sportList", SportType.values());
				model.addAttribute("error", "Erreur dans le format de la date (dd/mm/yyyy HH:MM)");
				model.addAttribute("event", viewmodel);
			}
	}

	public void preLoadEvents(SearchViewModel viewModel, ProxyModel model, ProxyHttpSession session)
	{
		if (session.getAttribute("sesusername") != null){
			User user = datamanager.findUser((String) session.getAttribute("sesusername"));
			if (SportType.matchString(user.getFavSport()) != null)
				viewModel.sport = SportType.matchString(user.getFavSport());
		}
		search(viewModel, model);
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

	public void deleteEvent(String eventId) {
		datamanager.deleteEvent(UUID.fromString(eventId));
	}

}
