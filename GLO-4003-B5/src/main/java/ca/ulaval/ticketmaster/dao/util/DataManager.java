/**
 * 
 * @author CP & Mathieu Bolduc
 *	Class repo soon�
 */
package ca.ulaval.ticketmaster.dao.util;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import ca.ulaval.ticketmaster.model.Event;
import ca.ulaval.ticketmaster.model.Ticket;
import ca.ulaval.ticketmaster.model.User;

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
		// Loader les id d'event pour ne pas �craser un event existant dans le cas de la cr饌tion d'un nouvel event
		int output[] = xmlReader.readStartupInformation();
		totalEvents = output[0];
		totalUsers = output[1];
	}
	
	/*
	 * Returns an event if it exists, null otherwise
	 */
	public Event findEvent(UUID _eventId){
		// impl�mentation sujet changement
		return xmlReader.loadEvent(_eventId);
	}
	
	/*
	 * Returns a ticket from a specific event if it exists, null otherwise
	 */
	public Ticket findTicket(UUID _eventId, UUID _ticketId){
		// impl�mentation sujet changement
		return xmlReader.loadTicket(_eventId, _ticketId);
	}

	/*
	 * Returns a user if it exists, null otherwise
	 */
	public User findUser(String _username) {
		// impl�mentation sujet changement
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
		//v駻ifier que le user n'existe pas d駛�
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
		return findEvent(_eventId).getTicketList();
	}
	
	public List<User> findAllUsers() {
		return xmlReader.loadUsers();
	}

	public List<Event> findAllEvents() {
		return xmlReader.loadEvents();
	}
}
