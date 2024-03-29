/**
 * 
 * @author CP & Mathieu Bolduc
 *	Class repo soon�
 */
package ca.ulaval.ticketmaster.dao.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import ca.ulaval.ticketmaster.events.model.Event;
import ca.ulaval.ticketmaster.events.tickets.model.Ticket;
import ca.ulaval.ticketmaster.users.model.User;

@Repository
public class DataManager {
    // private static final Logger logger =
    // LoggerFactory.getLogger(DataManager.class);

    private XmlWriter xmlWriter;
    private XmlReader xmlReader;
    private MailUtil mailSender;

    private int totalUsers;
    private int totalEvents;

    /*
     * public static void main(String[] args){ DataManager test = new
     * DataManager(); UUID eventid =
     * UUID.fromString("14be9fba-dfcd-41ca-a317-b61e6fc159f3"); UUID ticketid =
     * UUID.fromString("8ff5a890-96ab-43a7-a6aa-2cdb06fcab01"); User user = new
     * User("CarloBoutet", "123", "Mathieu", "Bolduc", "bolduc_mathieu@live.ca",
     * AccessLevel.User, SportType.FOOTBALL.toString(), "M", TicketType.GENERAL,
     * "Qu�bec"); Ticket testT = test.findTicket(eventid, ticketid);
     * List<Ticket> testList = new ArrayList<Ticket>(); testList.add(testT);
     * test.buyTickets(testList, "CarloBoutet"); //boolean mytestbrah =
     * test.buyTicket(eventid, ticketid, "mrTito2");
     * //System.out.println(mytestbrah); }
     */
    public boolean reconnect(String _file) {
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

    public DataManager() {
	LoadStartupInformation();
    }

    private void LoadStartupInformation() {
	xmlWriter = new XmlWriter();
	xmlReader = new XmlReader();
	mailSender = new MailUtil();
	// Loader les id d'event pour ne pas �craser un event existant dans le
	// cas de la cr饌tion d'un nouvel event
	int output[] = xmlReader.readStartupInformation();
	totalEvents = output[0];
	totalUsers = output[1];
    }

    /**
     * Returns an event if it exists, null otherwise
     */
    public Event findEvent(UUID _eventId) {
	// impl�mentation sujet changement
	return xmlReader.loadEvent(_eventId);
    }

    /**
     * Returns a ticket from a specific event if it exists, null otherwise
     */
    public Ticket findTicket(UUID _eventId, UUID _ticketId) {
	// impl�mentation sujet changement
	return xmlReader.loadTicket(_eventId, _ticketId);
    }

    /**
     * Returns a user if it exists, null otherwise
     */
    public User findUser(String _username) {
	// impl�mentation sujet changement
	return xmlReader.userAuthenticate(_username);
    }

    public boolean saveEvent(Event _event) {
	if (xmlWriter.writeEvent(_event)) {
	    totalEvents++;
	    return true;
	}
	return false;
    }

    /**
     * Adds a ticket to the local event linked in the ticket received and adds a
     * ticket to the data of the website Be sure to have a linked event in the
     * ticket // the id is generated for the ticket Return a boolean
     * representing if the operation succeeded
     */
    public boolean saveTicket(Ticket _ticket) {
	// gestion de l'ajout local et au fichier
	if (_ticket.getEvent() != null) {
	    Event event = _ticket.getEvent();
	    // add
	    event.addTicketToList(_ticket);
	    return xmlWriter.writeTicketToEvent(event.getId(), _ticket);
	}
	return false;
    }

    public boolean saveTicketsToEvent(UUID _eventId, List<Ticket> _ticketsToAdd) {
	for (Iterator<Ticket> it = _ticketsToAdd.iterator(); it.hasNext();) {
	    Ticket ticket = it.next();
	    if (ticket.getEvent() == null) {
		return false;
	    }
	}
	Event event = this.findEvent(_eventId);
	for (Iterator<Ticket> it = _ticketsToAdd.iterator(); it.hasNext();) {
	    Ticket toAdd = it.next();
	    event.addTicketToList(toAdd);
	    xmlWriter.writeTicketToEvent(it.next().getEvent().getId(), toAdd);
	}
	return true;
    }

    public boolean saveUser(User _user) {
	// v駻ifier que le user n'existe pas d駛�
	if (xmlReader.userAuthenticate(_user.getUsername()) == null) {
	    if (xmlWriter.writeUser(_user)) {
		totalUsers++;
		return true;
	    }
	}
	return false;
    }

    public boolean editUser(User _user) {
	return xmlWriter.modifyUser(_user);
    }

    public boolean editEvent(Event _event) {
	return xmlWriter.modifyEvent(_event);
    }

    public boolean editTicket(Ticket _ticket) {
	return xmlWriter.modifyTicket(_ticket);
    }

    private boolean verifyTicket(Ticket _ticket) {
	// Regarder si le billet est disponible � l'achat
	if (_ticket.getOwner().equals("") || _ticket.getResellprice() != 0) {
	    return true;
	}
	return false;
    }

    /*
     * Returns null if all tickets are available and action was completed or
     * returns a list of the tickets that are not available for the purchase
     */
    public ArrayList<Ticket> buyTickets(ArrayList<Ticket> _ticketList, String _owner) {
	ArrayList<Ticket> invalidTicketList = new ArrayList<Ticket>();
	// Regarder si les billets sont disponible � l'achat
	for (Iterator<Ticket> it = _ticketList.iterator(); it.hasNext();) {
	    Ticket ticket = it.next();
	    if (!verifyTicket(ticket)) {
		invalidTicketList.add(ticket);
	    }
	}
	User owner = findUser(_owner);
	// si il n'y a pas de billets invalide proc�der � l'achat et le owner
	// est valide
	if (invalidTicketList.isEmpty() && owner != null) {
	    for (Iterator<Ticket> it = _ticketList.iterator(); it.hasNext();) {
		Ticket ticket = it.next();
		buyTicket(ticket, owner);
	    }
	    // cr�er les donn�es de transaction
	    UUID transactionId = UUID.randomUUID();
	    xmlWriter.writeTransaction(transactionId, _ticketList, _owner);
	    sendConfirmationMail(owner, transactionId, _ticketList);
	    return null;
	}
	return invalidTicketList;
    }

    private void sendConfirmationMail(User _receiver, UUID _transactionId, List<Ticket> _ticketList) {
	mailSender.sendTransactionMail(_receiver, _transactionId, _ticketList);
    }

    private void buyTicket(Ticket _ticket, User _owner) {
	_ticket.setOwner(_owner.getUsername());
	// modifier le liste de billets de l'utilisateur
	_owner.addTicketToList(_ticket.getEvent().getId(), _ticket.getId());
	xmlWriter.modifyUser(_owner);
	// modifier le ticket correspondant
	xmlWriter.modifyTicket(_ticket);
    }

    public boolean deleteTicket(UUID _eventId, UUID _ticketId) {
	return xmlWriter.deleteTicket(_eventId, _ticketId);
    }

    public boolean deleteTicket(Ticket _ticket) {
	return xmlWriter.deleteTicket(_ticket.getEvent().getId(), _ticket.getId());
    }

    public boolean deleteUser(String _username) {
	return xmlWriter.deleteUser(_username);
    }

    public boolean deleteEvent(Event _event) {
	return xmlWriter.deleteEvent(_event.getId());
    }

    public boolean deleteEvent(UUID _eventId) {
	return xmlWriter.deleteEvent(_eventId);
    }

    public List<User> findAllUsers() {
	return xmlReader.loadUsers();
    }

    /**
     * Returns the list of tickets of a transaction
     * 
     * @param _uuid
     *            valid uuid of transaction
     * @param _user
     *            valid user name
     * @return list of tickets of the specified transaction or null if it
     *         doesn't exist
     */
    public List<Ticket> findTransaction(UUID _transactionId, String _user) {
	if (_transactionId != null && _user != null)
	    return xmlReader.readTransaction(_transactionId, _user);
	else
	    return null;
    }

    /**
     * Returns all events with their respective unsold tickets
     */
    public List<Event> findAllEventsWithAllTickets() {
	return xmlReader.loadEvents();
    }

}
