/**
 * 
 * @author CP
 *	La javadoc, la javadoc !
 */

package ca.ulaval.ticketmaster.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import ca.ulaval.ticketmaster.model.enums.SportType;

public class Event {

    private UUID id;
    private boolean open;
    private int ticketsTotal;
    private int ticketsAvailable;
    private SportType sport;
    private String gender;
    private String homeTeam;
    private String visitorsTeam;
    private String location;
    private String stadium;
    private Date date;
    private Date time;
    private List<Ticket> ticketList;
    private List<String> sectionList;

    public Event() {
	id = UUID.randomUUID();
	ticketList = new ArrayList<Ticket>();
	sectionList = new ArrayList<String>();
	ticketsTotal = 0;
	ticketsAvailable = 0;
    }

    public Event(UUID _id) {
	id = _id;
	ticketList = new ArrayList<Ticket>();
	sectionList = new ArrayList<String>();
	ticketsTotal = 0;
	ticketsAvailable = 0;
    }

    public Event(boolean _open, SportType _sport, String _gender, String _homeTeam, String _visitorsTeam,
	    String _location, String _stadium, Date _date, Date _time) {
	this(UUID.randomUUID(), _open, _sport, _gender, _homeTeam, _visitorsTeam, _location, _stadium, _date,
		_time, 0, 0);
    }

    public Event(UUID _id, boolean _open, SportType _sport, String _gender, String _homeTeam,
	    String _visitorsTeam, String _location, String _stadium, Date _date, Date _time,
	    int _ticketsTotal, int _ticketsAvailable) {
	id = _id;
	open = _open;
	ticketsTotal = 0;
	ticketsAvailable = 0;
	sport = _sport;
	gender = _gender;
	homeTeam = _homeTeam;
	visitorsTeam = _visitorsTeam;
	location = _location;
	stadium = _stadium;
	date = _date;
	time = _time;
	ticketList = new ArrayList<Ticket>();
	sectionList = new ArrayList<String>();
	ticketsTotal = _ticketsTotal;
	ticketsAvailable = _ticketsAvailable;
    }

    public String toString() {
	return "\n id: " + id + " Sport: " + sport + " Gender: " + gender + " HomeTeam: " + homeTeam
		+ " Visitors: " + visitorsTeam + " Location: " + location + " Stadium: " + stadium + " @ "
		+ date + "tick total" + ticketsTotal + "tick avail" + ticketsAvailable;
    }

    public List<Ticket> getTicketList() {
	return ticketList;
    }

    public void setTicketList(List<Ticket> ticketList) {
	this.ticketList = ticketList;
    }

    public UUID getId() {
	return id;
    }

    public void setId(UUID id) {
	this.id = id;
    }

    public boolean isOpen() {
	return open;
    }

    public void setOpen(boolean open) {
	this.open = open;
    }

    public int getTicketsTotal() {
	return ticketsTotal;
    }

    public void setTicketsTotal(int ticketsTotal) {
	this.ticketsTotal = ticketsTotal;
    }

    public int getTicketsAvailable() {
	return ticketsAvailable;
    }

    public void setTicketsAvailable(int ticketsAvailable) {
	this.ticketsAvailable = ticketsAvailable;
    }

    public SportType getSport() {
	return sport;
    }

    public void setSport(SportType sport) {
	this.sport = sport;
    }

    public String getGender() {
	return gender;
    }

    public void setGender(String gender) {
	this.gender = gender;
    }

    public String getHomeTeam() {
	return homeTeam;
    }

    public void setHomeTeam(String homeTeam) {
	this.homeTeam = homeTeam;
    }

    public String getVisitorsTeam() {
	return visitorsTeam;
    }

    public void setVisitorsTeam(String visitorsTeam) {
	this.visitorsTeam = visitorsTeam;
    }

    public String getLocation() {
	return location;
    }

    public void setLocation(String location) {
	this.location = location;
    }

    public String getStadium() {
	return stadium;
    }

    public void setStadium(String stadium) {
	this.stadium = stadium;
    }

    public Date getDate() {
	return date;
    }

    public void setDate(Date date) {
	this.date = date;
    }

    public Date getTime() {
	return time;
    }

    public void setTime(Date time) {
	this.time = time;
    }

    public List<String> getSectionList() {
	return sectionList;
    }

    public void setSectionList(List<String> sectionList) {
	this.sectionList = sectionList;
    }

    public void addTicketToList(Ticket _ticket) {
	ticketList.add(_ticket);
	this.ticketsTotal++;
	// si le owner est vide cela signifie un billet disponible
	if (_ticket.getOwner().equals("")) {
	    this.ticketsAvailable++;
	} else if (_ticket.getResellprice() != 0) {
	    this.ticketsAvailable++;
	}
    }

    public void removeTicketFromList(UUID _ticketId) {
	Ticket ticket = null;
	for (Iterator<Ticket> it = this.ticketList.iterator(); it.hasNext();) {
	    Ticket itTicket = it.next();
	    if (itTicket.getId() == _ticketId) {
		ticket = itTicket;
	    }
	}
	if (ticket != null) {
	    ticketList.remove(ticket);
	    this.ticketsTotal--;
	    this.ticketsAvailable--;
	}
    }

    public void changeValuesFromEventObject(Event _event) {
	open = _event.isOpen();
	sport = _event.getSport();
	gender = _event.getGender();
	homeTeam = _event.getHomeTeam();
	visitorsTeam = _event.getVisitorsTeam();
	location = _event.getLocation();
	stadium = _event.getStadium();
	date = _event.getDate();
	time = _event.getTime();
    }

    public boolean findAndEditTicket(Ticket _ticket) {
	for (Iterator<Ticket> it = this.ticketList.iterator(); it.hasNext();) {
	    Ticket old = it.next();
	    if (old.getId() == _ticket.getId()) {
		old.changeValuesFromTicketObject(_ticket);
		return true;
	    }
	}
	return false;
    }
}
