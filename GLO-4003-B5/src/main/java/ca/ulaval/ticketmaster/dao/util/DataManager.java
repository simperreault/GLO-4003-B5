/**
 * 
 * @author CP & Mathieu Bolduc
 *	Class repo soonï¿½
 */
package ca.ulaval.ticketmaster.dao.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;



import org.springframework.stereotype.Repository;

import ca.ulaval.ticketmaster.model.Event;
import ca.ulaval.ticketmaster.model.Ticket;
import ca.ulaval.ticketmaster.model.User;
import ca.ulaval.ticketmaster.model.enums.SportType;

@Repository
public class DataManager {
	//private static final Logger logger = LoggerFactory.getLogger(DataManager.class);

	private XmlWriter xmlWriter;
	private XmlReader xmlReader;
	
	private int totalUsers;
	private int totalEvents;
	//private List<User> userList;
	//private List<Event> eventList;
	//private HashMap<String,User> userMap;
	//private HashMap<Integer,Event> eventMap;
	
	public boolean reconnect(String _file){
		return xmlWriter.connect(_file) && xmlReader.connect(_file);
	}
	
	public int getTotalUsers() {
		return totalUsers;
	}

	public void setTotalUsers(int totalUsers) {
		this.totalUsers = totalUsers;
	}

	public int getTotalEvents() {
		return totalEvents;
	}

	public void setTotalEvents(int totalEvents) {
		this.totalEvents = totalEvents;
	}

	public DataManager(){
		LoadStartupInformation();
	}
	
	private void LoadStartupInformation(){
		xmlWriter = new XmlWriter();
		xmlReader = new XmlReader();
		// Loader les id d'event pour ne pas écraser un event existant dans le cas de la cré¥Œtion d'un nouvel event
		int output[] = xmlReader.readStartupInformation();
		totalEvents = output[0];
		totalUsers = output[1];
	}
	
	/*
	 * Returns an event if it exists, null otherwise
	 */
	public Event findEvent(UUID _eventId){
		// implémentation sujet changement
		return xmlReader.loadEvent(_eventId);
	}
	
	/*
	 * Returns a ticket from a specific event if it exists, null otherwise
	 */
	public Ticket findTicket(UUID _eventId, UUID _ticketId){
		// implémentation sujet changement
		return xmlReader.loadTicket(_eventId, _ticketId);
	}

	/*
	 * Returns a user if it exists, null otherwise
	 */
	public User findUser(String _username) {
		// implémentation sujet changement
		return xmlReader.userAuthenticate(_username);
	}
	
	public boolean saveEvent(Event _event){
		if(xmlWriter.writeEvent(_event)){
			totalEvents ++;
			return true;
		}
		return false;
	}
	/*
	 * Adds a ticket to the local event linked in the ticket received and adds a ticket to the data of the website
	 * Be sure to have a linked event in the ticket // the id is generated for the ticket
	 * Return a boolean representing if the operation succeeded
	 */
	public boolean saveTicket(Ticket _ticket){
		//gestion de l'ajout local et au fichier
		if(_ticket.getEvent() != null){
			Event event = _ticket.getEvent();
			//add
			event.addTicketToList(_ticket);
			return xmlWriter.writeTicketToEvent(event.getId(), _ticket);
		}
		return false;
	}
	
	public boolean saveTicketsToEvent(UUID _eventId ,List<Ticket> _ticketsToAdd){
		for(Iterator<Ticket> it = _ticketsToAdd.iterator(); it.hasNext();)
		{
			Ticket ticket = it.next();
			if(ticket.getEvent() == null){
				return false;
			}
		}
		Event event = this.findEvent(_eventId);
		for(Iterator<Ticket> it = _ticketsToAdd.iterator(); it.hasNext();)
		{
			Ticket toAdd = it.next();
			event.addTicketToList(toAdd);
			xmlWriter.writeTicketToEvent(it.next().getEvent().getId(), toAdd);
		}
		return true;
	}
	
	public boolean saveUser(User _user){
		//vé§»ifier que le user n'existe pas dé§›ï¿½
		if(xmlReader.userAuthenticate(_user.getUsername()) == null){
			if(xmlWriter.writeUser(_user)){
				totalUsers++;
				return true;
			}
		}
		return false;
	}
	
	public boolean editUser(User _user){
		return xmlWriter.modifyUser(_user);
	}
	
	public boolean editEvent(Event _event){
		return xmlWriter.modifyEvent(_event);
	}
	
	public boolean editTicket(Ticket _ticket){
		return xmlWriter.modifyTicket(_ticket);
	}
	
	public boolean deleteTicket(UUID _eventId , UUID _ticketId){
		return xmlWriter.deleteTicket(_eventId, _ticketId);
	}
	
	public boolean deleteTicket(Ticket _ticket){
		return xmlWriter.deleteTicket(_ticket.getEvent().getId(), _ticket.getId());
	}
	
	public boolean deleteUser(String _username){
		return xmlWriter.deleteUser(_username);
	}
	
	public boolean deleteEvent(Event _event){
		return xmlWriter.deleteEvent(_event.getId());
	}
	
	public boolean deleteEvent(UUID _eventId){
		return xmlWriter.deleteEvent(_eventId);
	}
	
	public List<Ticket> findAllTickets(UUID _eventId){
		return filterSoldTickets(findEvent(_eventId).getTicketList());
	}
	
	public List<User> findAllUsers() {
		return xmlReader.loadUsers();
	}
	
	private List<Event>filterSports(SportType _sport , List<Event> _list){
		List<Event> returnList = new ArrayList<Event>();
		for(Event e : _list){
			if(e.getSport().equals(_sport)){
				returnList.add(e);
			}
		}
		return returnList;
	}
	
	private List<Event>filterDates(int _days , List<Event> _list){
	    Calendar cal = Calendar.getInstance();  
	    cal.setTime(new Date());  // set with current time
	    cal.add(Calendar.DATE, _days); // plus _days    
	    Date date = cal.getTime();  
		List<Event> returnList = new ArrayList<Event>();
		for(Event e : _list){
			if(e.getDate().before(date) || e.getDate().equals(date)){
				returnList.add(e);
			}
		}
		return returnList;
	}
	
	private List<Event>filterTeam(String _team , List<Event> _list){
		List<Event> returnList = new ArrayList<Event>();
		for(Event e : _list){
			if(e.getHomeTeam().equals(_team) || e.getVisitorsTeam().equals(_team)){
				returnList.add(e);
			}
		}
		return returnList;
	}
	
	/*
	 * Searchs all events and returns the ones which match criterias,
	 * Sport - pass null if it doesn't matter
	 * days  - pass 0 if it doesn't matter
	 * team  - pass null if it doesn't matter
	 */
	public List<Event>SearchWithCriterias(SportType _sport, int _days, String _team){
		List<Event> _list = findAllEvents();
		if (_sport != null){
			_list = this.filterSports(_sport, _list);
		}
		if (_days != 0){
			_list = this.filterDates(_days, _list);
		}
		if (_team != null && !_team.isEmpty()){
			_list = this.filterTeam(_team, _list);
		}
		return _list;
	}
	
	
	public List<String> GetAllTeams(){
		Set<String> returnSet = new HashSet<String>();
		for(Event e : findAllEvents()){
			returnSet.add(e.getHomeTeam());
			returnSet.add(e.getVisitorsTeam());
		}
		return new ArrayList<String>(returnSet);
	}
	

	/*
	 * Returns all events with their respective unsold tickets
	 */
	public List<Event> findAllEvents() {
		return filterSoldTicketsForAllEvents(xmlReader.loadEvents());
	}
	
	private List<Ticket> filterSoldTickets(List<Ticket> _toFilter){
		List<Ticket> returnList = new ArrayList<Ticket>();
		for(Ticket t : _toFilter){
			if(t.getOwner().isEmpty() || (!t.getOwner().isEmpty() && t.getResellprice() != 0)){
				returnList.add(t);
			}
		}
		return returnList;
	}
	
	private List<Event> filterSoldTicketsForAllEvents(List<Event> _toFilter){
		List<Event> returnList = new ArrayList<Event>();
		for(Event e : _toFilter){
			e.setTicketList(filterSoldTickets(e.getTicketList()));
			returnList.add(e);
		}
		return returnList;
	}
	
}
