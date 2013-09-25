/**
 * 
 * @author CP
 *	La javadoc, la javadoc !
 */
package ca.ulaval.ticketmaster.dao.util;

import java.util.List;

import ca.ulaval.ticketmaster.model.Event;
import ca.ulaval.ticketmaster.model.Ticket;
import ca.ulaval.ticketmaster.model.User;

public class DataManager {

	private XmlWriter xmlWriter;
	private XmlReader xmlReader;
	
	private int lastEventId;
	private int totalUsers;
	private int totalEvents;
	private List<User> userList;
	private List<Event> eventList;
	
	

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
		eventList = xmlReader.loadEvents();
		userList = xmlReader.loadUsers();
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
		return false;
	}
	
	public boolean saveTicket(Ticket _ticket){
		
		return false;
	}
	
	public boolean loadAllEvents(){
		return false;
	}
	
	public List<Ticket> loadAllTickets(int _eventId){
		return getEvent(_eventId).getTicketList();
	}
	
	public boolean loadUser(){
		return false;
	}
	
	public boolean saveUser(){
		return false;
	}

	public int getLastEventId() {
		return lastEventId;
	}

	public void setLastEventId(int lastEventId) {
		this.lastEventId = lastEventId;
	}

	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

	public List<Event> getEventList() {
		return eventList;
	}

	public void setEventList(List<Event> eventList) {
		this.eventList = eventList;
	}
}
