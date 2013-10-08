package ca.ulaval.ticketmaster.web.converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import ca.ulaval.ticketmaster.dao.util.DataManager;
import ca.ulaval.ticketmaster.model.Event;
import ca.ulaval.ticketmaster.web.viewmodels.EventViewModel;

public class EventConverter {

    static public Event convert(EventViewModel viewmodel, DataManager datamanager) throws ParseException {
	Event entry = new Event();
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
}
