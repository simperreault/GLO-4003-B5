package ca.ulaval.ticketmaster.dao.util;

import java.util.Date;

import ca.ulaval.ticketmaster.model.Event;
import ca.ulaval.ticketmaster.model.Event.Sport;

public class EventFactory {

	public EventFactory(){
	}
	
	public Event CreateEvent(boolean _open, int _ticketsTotal, int _ticketsAvailable, Sport _sport, String _gender, String _homeTeam, String _visitorsTeam, String _location, String _stadium, Date _date, Date _time){
		return new Event(_open, _sport, _gender, _homeTeam, _visitorsTeam, _location, _stadium, _date, _time);
	}
}
