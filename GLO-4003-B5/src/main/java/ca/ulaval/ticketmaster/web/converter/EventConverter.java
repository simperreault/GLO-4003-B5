package ca.ulaval.ticketmaster.web.converter;

import ca.ulaval.ticketmaster.dao.util.DataManager;
import ca.ulaval.ticketmaster.model.Event;
import ca.ulaval.ticketmaster.web.viewmodels.EventViewModel;

public class EventConverter {

    static public Event convert(EventViewModel viewmodel, DataManager datamanager) {
	Event entry = new Event();
	entry.setGender(viewmodel.getGender());
	entry.setHomeTeam(viewmodel.getHomeTeam());
	entry.setVisitorsTeam(viewmodel.getVisitorsTeam());
	entry.setLocation(viewmodel.getLocation());
	entry.setStadium(viewmodel.getStadium());
	entry.setDate(viewmodel.getDate());
	entry.setTime(viewmodel.getTime());
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
	viewmodel.setDate(entry.getDate());
	viewmodel.setTime(entry.getTime());
	viewmodel.setSport(entry.getSport());

	return viewmodel;
    }
}
