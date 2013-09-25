/**
 * 
 * @author CP & Mathieu Bolduc
 *	Class repo soon™
 */
package ca.ulaval.ticketmaster.dao.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ca.ulaval.ticketmaster.model.Event;
import ca.ulaval.ticketmaster.model.Ticket;
import ca.ulaval.ticketmaster.model.User;

public class DataManager {
	private static final Logger logger = LoggerFactory.getLogger(DataManager.class);

	private XmlWriter xmlWriter;
	private XmlReader xmlReader;
	
	private int lastEventId;
	private int totalUsers;
	private int totalEvents;
	//private List<User> userList;
	//private List<Event> eventList;
	private HashMap<String,User> userMap;
	private HashMap<Integer,Event> eventMap;
	
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
		// Loader les id d'event pour ne pas écraser un event existant dans le cas de la création d'un nouvel event
		int output[] = xmlReader.readStartupInformation();
		totalEvents = output[0];
		lastEventId = output[1];
		totalUsers = output[2];
		
		// pour le moment on va loader tous les events dans la eventList et les users aussi
		// TODO faire un pattern de repository pis mettre ça clean
		
		List<Event> eventList = xmlReader.loadEvents();
		eventMap = new HashMap<Integer, Event>();
		for(Iterator<Event> it = eventList.iterator(); it.hasNext();)
		{
			Event event = it.next();
			eventMap.put(event.getId(), event);
		}
		List<User> userList = xmlReader.loadUsers();
		userMap = new HashMap<String,User>();
		for(Iterator<User> it = userList.iterator(); it.hasNext();)
		{
			User user = it.next();
			userMap.put(user.getUsername(), user);
		}
	}
	
	/*
	 * Returns an event if it exists, null otherwise
	 */
	public Event getEvent(int _eventId){
		// implémentation sujet à changement
		return xmlReader.loadEvent(_eventId);
	}
	
	/*
	 * Returns a ticket from a specific event if it exists, null otherwise
	 */
	public Ticket getTicket(int _eventId, int _ticketId){
		// implémentation sujet à changement
		return xmlReader.loadTicket(_eventId, _ticketId);
	}

	/*
	 * Returns a user if it exists, null otherwise
	 */
	public User getUser(String _username) {
		// implémentation sujet à changement
		return xmlReader.userAuthenticate(_username);
	}
	
	public boolean saveEvent(Event _event){
		//gestion des ids pour s'assrurer de suivre les ids du fichier de données
		_event.setId(lastEventId + 1);
		if(xmlWriter.writeEvent(_event)){
			lastEventId ++;
			totalEvents ++;
			eventMap.put(_event.getId(), _event);
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
			Event event = eventMap.get(_ticket.getEvent().getId());
			//générer un nouvel id pour le ticket
			int newId = event.getTicketsTotal() + 1;
			_ticket.setId(newId);
			//add
			event.addTicketToList(_ticket);
			eventMap.put(event.getId(), event);
			return xmlWriter.writeTicketToEvent(event.getId(), _ticket);
		}
		return false;
	}
	
	public boolean saveTicketsToEvent(int _eventId ,List<Ticket> _ticketsToAdd){
		//gestion de l'ajout local et au fichier
		for(Iterator<Ticket> it = _ticketsToAdd.iterator(); it.hasNext();)
		{
			Ticket ticket = it.next();
			if(ticket.getEvent() == null){
				return false;
			}
		}
		Event event = eventMap.get(_eventId);
		for(Iterator<Ticket> it = _ticketsToAdd.iterator(); it.hasNext();)
		{
			Ticket toAdd = it.next();
			//générer un nouvel id pour le ticket
			int newId = event.getTicketsTotal() + 1;
			toAdd.setId(newId);
			//add
			event.addTicketToList(toAdd);
			xmlWriter.writeTicketToEvent(it.next().getEvent().getId(), toAdd);
		}
		eventMap.put(_eventId, event);
		return true;
	}
	
	public boolean saveUser(User _user){
		//vérifier que le user n'existe pas déjà
		if(xmlReader.userAuthenticate(_user.getUsername()) == null){
			if(xmlWriter.writeUser(_user)){
				totalUsers++;
				userMap.put(_user.getUsername(), _user);
				return true;
			}
		}
		return false;
	}
	
	public boolean editUser(User _user){
		if(xmlWriter.modifyUser(_user)){
			editUserInLocalData(_user);
			return true;
		}
		return false;
	}
	
	public boolean editEvent(Event _event){
		if(xmlWriter.modifyEvent(_event)){
			editEventInLocalData(_event);
			return true;
		}
		return false;
	}
	
	public boolean editTicket(Ticket _ticket){
		if(xmlWriter.modifyTicket(_ticket)){
			editTicketInLocalData(_ticket);
			return true;
		}
		return false;
	}
	
	public boolean deleteTicket(int _eventId , int _ticketId){
		if (xmlWriter.deleteTicket(_eventId, _ticketId)){
			Event event = eventMap.get(_eventId);
			if(event != null){
				deleteTicketInLocalData(_ticketId,event);
				return true;
			}
		}
		return false;
	}
	
	public boolean deleteTicket(Ticket _ticket){
		if(xmlWriter.deleteTicket(_ticket.getEvent().getId(), _ticket.getId())){
			deleteTicketInLocalData(_ticket.getId(),_ticket.getEvent());
			return true;
		}
		return false;
	}
	
	public boolean deleteUser(String _username){
		if(xmlWriter.deleteUser(_username)){
			User user = userMap.get(_username);
			if(user != null){
				deleteUserInLocalData(user);
			}
			return true;
		}
		return false;
	}
	
	public boolean deleteUser(User _user){
		if(xmlWriter.deleteUser(_user.getUsername())){
			deleteUserInLocalData(_user);
			return true;
		}
		return false;
	}
	
	public boolean deleteEvent(Event _event){
		if( xmlWriter.deleteEvent(_event.getId())){
			deleteEventInLocalData(_event);
			return true;
		}
		return false;
	}
	
	public boolean deleteEvent(int _eventId){
		if( xmlWriter.deleteEvent(_eventId)){
			Event event = eventMap.get(_eventId);
			if(event != null){
				deleteEventInLocalData(event);
			}
			return true;
		}
		return false;
	}
	
	/*
	public boolean loadAllEvents(){
		return false;
	}
	*/
	public List<Ticket> loadAllTickets(int _eventId){
		return getEvent(_eventId).getTicketList();
	}
	
	public int getLastEventId() {
		return lastEventId;
	}

	public void setLastEventId(int lastEventId) {
		this.lastEventId = lastEventId;
	}

	public List<User> getUserList() {
		List<User> list = new ArrayList<User>(userMap.values());
		return list;
	}
/*
	public void setUserList(List<User> userList) {
		this.userList = userList;
	}
*/
	public List<Event> getEventList() {
		List<Event> list = new ArrayList<Event>(eventMap.values());
		return list;
	}
	/*
	public void setEventList(List<Event> eventList) {
		this.eventList = eventList;
	}
	*/
	private boolean deleteTicketInLocalData(int _ticketId, Event _event){
		if(eventMap.containsKey(_event.getId())){
			_event.removeTicketFromList(_ticketId);
			eventMap.put(_event.getId(), _event);
			return true;
		}
		return false;
	}
	
	private boolean deleteEventInLocalData(Event _event){
		if(eventMap.containsKey(_event.getId())){
			eventMap.remove(_event.getId());
			return true;
		}
		return false;
	}
	
	private boolean deleteUserInLocalData(User _user){
		if(userMap.containsKey(_user.getUsername())){
			userMap.remove(_user.getUsername());
			return true;
		}
		return false;
	}
	
	private boolean editTicketInLocalData(Ticket _ticket){
		if(eventMap.containsKey(_ticket.getEvent().getId())){
			Event event = eventMap.get(_ticket.getEvent().getId());
			if(event.findAndEditTicket(_ticket)){
				eventMap.put(event.getId(), event);
				return true;
			}
		}
		return false;
	}
	
	private boolean editEventInLocalData(Event _event){
		if(eventMap.containsKey(_event.getId())){
			Event event = eventMap.get(_event.getId());
			event.changeValuesFromEventObject(_event);
			eventMap.put(event.getId(), event);
			return true;
		}
		return false;
	}
	
	private boolean editUserInLocalData(User _user){
		if(userMap.containsKey(_user.getUsername())){
			User user = userMap.get(_user.getUsername());
			user.changeValuesFromUserObject(_user);
			userMap.put(user.getUsername(), user);
			return true;
		}
		return false;
	}
}
