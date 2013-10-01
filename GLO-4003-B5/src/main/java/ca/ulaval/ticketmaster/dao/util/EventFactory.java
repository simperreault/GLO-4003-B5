package ca.ulaval.ticketmaster.dao.util;

import java.util.Date;
import java.util.UUID;

import ca.ulaval.ticketmaster.model.Event;
import ca.ulaval.ticketmaster.model.Event.Sport;

public class EventFactory {

	public EventFactory(){
	}
	
	public static Event CreateEvent(boolean _open, Sport _sport, String _gender, String _homeTeam, String _visitorsTeam, String _location, String _stadium, Date _date, Date _time, int _ticketsTotal, int _ticketsAvailable){
		return new Event(_open, _sport, _gender, _homeTeam, _visitorsTeam, _location, _stadium, _date, _time);
	}
	
	public static Event CreateExistingEvent(UUID _id,boolean _open, Sport _sport, String _gender, String _homeTeam, String _visitorsTeam, String _location, String _stadium, Date _date, Date _time, int _ticketsTotal, int _ticketsAvailable){
		return new Event( _id , _open,  _sport,  _gender,  _homeTeam,  _visitorsTeam,  _location,  _stadium,  _date,  _time,  _ticketsTotal, _ticketsAvailable);
	}
	
}
