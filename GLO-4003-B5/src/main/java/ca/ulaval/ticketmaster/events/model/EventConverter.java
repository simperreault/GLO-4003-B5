package ca.ulaval.ticketmaster.events.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import ca.ulaval.ticketmaster.dao.util.DataManager;

public class EventConverter {

    static public Event convert(EventViewModel viewmodel, DataManager datamanager) throws ParseException {
	Event entry = new Event();
	entry.setId(viewmodel.getId() == null ? UUID.randomUUID() : viewmodel.getId());
	entry.setGender(viewmodel.getGender());
	entry.setHomeTeam(viewmodel.getHomeTeam());
	entry.setVisitorsTeam(viewmodel.getVisitorsTeam());
	entry.setLocation(viewmodel.getLocation());
	entry.setStadium(viewmodel.getStadium());

	SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	entry.setDate(dateFormat.parse(viewmodel.getDate()));
	entry.setTime(dateFormat.parse(viewmodel.getDate()));
	entry.setSport(viewmodel.getSport());

	return entry;
    }

    static public EventViewModel convert(Event entry) {
	EventViewModel viewmodel = new EventViewModel();
	viewmodel.setId(entry.getId());
	viewmodel.setGender(entry.getGender());
	viewmodel.setHomeTeam(entry.getHomeTeam());
	viewmodel.setVisitorsTeam(entry.getVisitorsTeam());
	viewmodel.setLocation(entry.getLocation());
	viewmodel.setStadium(entry.getStadium());

	SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	viewmodel.setDate(dateFormat.format(entry.getDate()));
	viewmodel.setSport(entry.getSport());

	return viewmodel;
    }

    public static List<EventViewModel> convert(List<Event> events) {
	List<EventViewModel> list = new LinkedList<>();
	for (Event e : events)
	    list.add(convert(e));

	return list;
    }
}
