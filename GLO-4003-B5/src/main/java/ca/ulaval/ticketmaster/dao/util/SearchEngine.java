package ca.ulaval.ticketmaster.dao.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import ca.ulaval.ticketmaster.events.model.Event;
import ca.ulaval.ticketmaster.events.model.SportType;
import ca.ulaval.ticketmaster.events.tickets.model.Ticket;
import ca.ulaval.ticketmaster.events.tickets.model.TicketType;

public class SearchEngine {

    private DataManager dataManager = new DataManager();

    public List<Ticket> findAllTickets(UUID _eventId) {
	return filterSoldTickets(dataManager.findEvent(_eventId).getTicketList());
    }

    public List<Event> findAllEvents() {
	return filterSoldTicketsForAllEvents(dataManager.findAllEventsWithAllTickets());
    }

    /**
     * Searches all events and returns the ones which match criterias, Sport -
     * pass null if it doesn't matter days - pass 0 if it doesn't matter team -
     * pass null or "" if it doesn't matter
     */
    public List<Event> SearchWithCriterias(SportType _sport, int _days, String _team) {
	List<Event> _list = findAllEvents();
	if (_sport != null) {
	    _list = this.filterSports(_sport, _list);
	}
	if (_days != 0) {
	    _list = this.filterDates(_days, _list);
	}
	if (_team != null && !_team.isEmpty()) {
	    _list = this.filterTeam(_team, _list);
	}
	return _list;
    }

    private List<Event> filterSports(SportType _sport, List<Event> _list) {
	List<Event> returnList = new ArrayList<Event>();
	for (Event e : _list) {
	    if (e.getSport().equals(_sport)) {
		returnList.add(e);
	    }
	}
	return returnList;
    }

    private List<Event> filterDates(int _days, List<Event> _list) {
	Calendar cal = Calendar.getInstance();
	cal.setTime(new Date()); // set with current time
	cal.add(Calendar.DATE, _days); // plus _days
	Date date = cal.getTime();
	List<Event> returnList = new ArrayList<Event>();
	for (Event e : _list) {
	    if (e.getDate().before(date) || e.getDate().equals(date)) {
		returnList.add(e);
	    }
	}
	return returnList;
    }

    private List<Event> filterTeam(String _team, List<Event> _list) {
	List<Event> returnList = new ArrayList<Event>();
	for (Event e : _list) {
	    if (e.getHomeTeam().equals(_team) || e.getVisitorsTeam().equals(_team)) {
		returnList.add(e);
	    }
	}
	return returnList;
    }

    public List<String> GetAllTeams() {
	Set<String> returnSet = new HashSet<String>();
	for (Event e : findAllEvents()) {
	    returnSet.add(e.getHomeTeam());
	    returnSet.add(e.getVisitorsTeam());
	}
	return new ArrayList<String>(returnSet);
    }

    /**
     * Find similar unsold simple or general Tickets, returns null list if no
     * ticket of the same type is found If the kind of ticket you want to find
     * doesn't have a section, use "" as a parameter
     * 
     * @param _eventId
     *            id of the event you wanna find tickets
     * @param _type
     *            type of tickets wanted
     * @param _section
     *            if the kind of tickets you want has a section, otherwise ""
     * @return a list of tickets which match criterias
     */
    public List<Ticket> findSimilarTickets(List<Ticket> _ticketList, TicketType _type, String _section) {
	List<Ticket> returnList = new ArrayList<Ticket>();
	for (Ticket t : filterSoldTickets(_ticketList)) {
	    if (_type.equals(t.getType()) && _section.equals(t.getSection())) {
		returnList.add(t);
	    }
	}
	if (returnList.size() > 0)
	    return returnList;
	else
	    return null;
    }

    /**
     * 
     * @param _eventId
     *            id of the event you wanna find tickets
     * @param _type
     *            type of tickets wanted
     * @param _section
     *            if the kind of tickets you want has a section, otherwise ""
     * @return number of similar tickets found
     */
    public int countSimilarTickets(List<Ticket> _ticketList, TicketType _type, String _section) {
	return findSimilarTickets(_ticketList, _type, _section).size();
    }

    /**
     * Regroups all the tickets by their respective type and section, returns
     * null if event doesnt exist
     * 
     * @param id
     *            of the event
     * @return list with general, simple, season and reserved tickets by section
     */
    public List<ArrayList<Ticket>> regroupSimilarTickets(UUID _eventId) {
	Event e = dataManager.findEvent(_eventId);
	if (e == null)
	    return null;
	List<Ticket> ticketList = findAllTickets(_eventId);
	List<ArrayList<Ticket>> returnList = new ArrayList<ArrayList<Ticket>>();
	// add all general tickets in first list
	returnList.add((ArrayList<Ticket>) findSimilarTickets(ticketList, TicketType.GENERAL, ""));
	// then add simple tickets
	for (String s : e.listExistingSections()) {
	    returnList.add((ArrayList<Ticket>) findSimilarTickets(ticketList, TicketType.SIMPLE, s));
	}
	// then add season tickets
	for (String s : e.listExistingSections()) {
	    returnList.add((ArrayList<Ticket>) findSimilarTickets(ticketList, TicketType.SEASON, s));
	}

	// then add reserved tickets
	for (String s : e.listExistingSections()) {
	    returnList.add((ArrayList<Ticket>) findSimilarTickets(ticketList, TicketType.RESERVED, s));
	}
	// need to do it three times so season and reserved tickets are not
	// interleaved with the others
	returnList.removeAll(Collections.singleton(null));
	return returnList;
    }

    public List<ArrayList<Ticket>> regroupSimilarTicketsByEvents(List<Ticket> _ticketList) {
	Set<UUID> eventSet = new HashSet<UUID>();
	for (Ticket t : _ticketList) {
	    eventSet.add(t.getEvent().getId());
	}
	ArrayList<ArrayList<Ticket>> eventSortedList = new ArrayList<ArrayList<Ticket>>();
	List<ArrayList<Ticket>> returnList = new ArrayList<ArrayList<Ticket>>();
	for (UUID e : eventSet) {
	    ArrayList<Ticket> tempList = new ArrayList<Ticket>();
	    for (Ticket t : _ticketList) {
		if (t.getEvent().getId().equals(e)) {
		    tempList.add(t);
		}
	    }
	    eventSortedList.add(tempList);
	}
	for (ArrayList<Ticket> ar : eventSortedList) {
	    // add all general tickets in first list
	    Event e = ar.get(0).getEvent();
	    returnList.add((ArrayList<Ticket>) findSimilarTickets(ar, TicketType.GENERAL, ""));
	    // then add simple tickets
	    for (String s : e.listExistingSections()) {
		returnList.add((ArrayList<Ticket>) findSimilarTickets(ar, TicketType.SIMPLE, s));
	    }
	    // then add season tickets
	    for (String s : e.listExistingSections()) {
		returnList.add((ArrayList<Ticket>) findSimilarTickets(ar, TicketType.SEASON, s));
	    }
	    // then add reserved tickets
	    for (String s : e.listExistingSections()) {
		returnList.add((ArrayList<Ticket>) findSimilarTickets(ar, TicketType.RESERVED, s));
	    }
	    // need to do it three times so season and reserved tickets are not
	    // interleaved with the others
	}
	returnList.removeAll(Collections.singleton(null));
	return returnList;
    }

    /**
     * Regroups all the tickets by their respective price, returns null if event
     * doesnt exist
     * 
     * @param id
     *            of the event
     * @return list with general, simple, season and reserved tickets by section
     */
    public List<ArrayList<Ticket>> regroupSamePriceTickets(UUID _eventId) {
	Event e = dataManager.findEvent(_eventId);
	if (e == null)
	    return null;
	List<Ticket> ticketList = filterSoldTickets(findAllTickets(_eventId));
	// find all prices of tickets
	Set<Double> PriceSet = new HashSet<Double>();
	for (Ticket t : ticketList) {
	    PriceSet.add(t.getPrice());
	}

	// create a list for each price and add it to the return list
	List<ArrayList<Ticket>> returnList = new ArrayList<ArrayList<Ticket>>();
	for (Double d : PriceSet) {
	    ArrayList<Ticket> tempList = new ArrayList<Ticket>();
	    for (Ticket t : ticketList) {
		if (t.getPrice() == d) {
		    tempList.add(t);
		}
	    }
	    returnList.add(tempList);
	}
	return returnList;

    }

    /**
     * Remove from the first list the elements in the second list
     * 
     * @param _toFilter
     * @return
     */
    public List<Ticket> filterListWithList(List<Ticket> _toFilter, List<Ticket> _filter) {

	List<Ticket> returnList = new ArrayList<Ticket>();

	boolean doAdd;

	for (Ticket t : _toFilter) {
	    doAdd = true;
	    for (Ticket tFilter : _filter) {
		if (t.getId().equals(tFilter.getId())) {
		    doAdd = false;
		}
	    }
	    if (doAdd) {
		returnList.add(t);
	    }
	}

	return returnList;
    }

    public List<Ticket> filterSoldTickets(List<Ticket> _toFilter) {
	List<Ticket> returnList = new ArrayList<Ticket>();
	for (Ticket t : _toFilter) {
	    if (t.getOwner().isEmpty() || (!t.getOwner().isEmpty() && t.getResellprice() != 0)) {
		returnList.add(t);
	    }
	}
	return returnList;
    }

    private List<Event> filterSoldTicketsForAllEvents(List<Event> _toFilter) {
	List<Event> returnList = new ArrayList<Event>();
	for (Event e : _toFilter) {
	    e.setTicketList(filterSoldTickets(e.getTicketList()));
	    returnList.add(e);
	}
	return returnList;
    }
}
