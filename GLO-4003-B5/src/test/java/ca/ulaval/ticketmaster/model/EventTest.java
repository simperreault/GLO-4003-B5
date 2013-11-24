package ca.ulaval.ticketmaster.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.ticketmaster.dao.util.TicketFactory;
import ca.ulaval.ticketmaster.events.model.Event;
import ca.ulaval.ticketmaster.events.model.SportType;
import ca.ulaval.ticketmaster.events.tickets.model.Ticket;
import ca.ulaval.ticketmaster.events.tickets.model.TicketType;

@RunWith(MockitoJUnitRunner.class)
public class EventTest {

    @Test
    public void testEventInt() {
	Event e = new Event(UUID.randomUUID());
	assertNotNull(e);
	Event ee = new Event();
	assertNotNull(ee);
    }

    @Test
    public void testEventIntBooleanIntIntSportStringStringStringStringStringDateDate() {
	Date date = new Date();
	Event e = new Event(false, SportType.FOOTBALL, "M", "Potatoes", "Seotatop", "Somewhere",
		"SomewhereStadium", date, date);
	assertNotNull(e);
	assertEquals(e.getSport(), SportType.FOOTBALL);
    }

    @Test
    public void testToString() {
	Date date = new Date();
	Event e = new Event(false, SportType.FOOTBALL, "M", "Potatoes", "Seotatop", "Somewhere",
		"SomewhereStadium", date, date);
	assertNotNull(e.toString());
    }

    @Test
    public void testGettersSetters() {
	Date date = new Date();
	Event e = new Event(false, SportType.FOOTBALL, "M", "Potatoes", "Seotatop", "Somewhere",
		"SomewhereStadium", date, date);
	ArrayList<Ticket> a = new ArrayList<Ticket>();
	// Test ticket list
	a.add(TicketFactory.CreateTicket());
	e.setTicketList(a);
	assertEquals(e.getTicketList(), a);
	// id
	UUID u = UUID.randomUUID();
	e.setId(u);
	assertEquals(e.getId(), u);
	// open
	e.setOpen(true);
	assertTrue(e.isOpen());
	// tick total
	e.setTicketsTotal(1);
	assertEquals(e.getTicketsTotal(), 1);
	// tick avail
	e.setTicketsAvailable(1);
	assertEquals(e.getTicketsAvailable(), 1);
	// sport
	e.setSport(SportType.SOCCER);
	assertEquals(e.getSport(), SportType.SOCCER);
	// gender
	e.setGender("F");
	assertEquals(e.getGender(), "F");
	// home
	e.setHomeTeam("A");
	assertEquals(e.getHomeTeam(), "A");
	// visit
	e.setVisitorsTeam("V");
	assertEquals(e.getVisitorsTeam(), "V");
	// loc
	e.setLocation("HUH");
	assertEquals(e.getLocation(), "HUH");
	// stad
	e.setStadium("luls");
	assertEquals(e.getStadium(), "luls");
	// datetime
	date = new Date();
	e.setDate(date);
	assertEquals(e.getDate(), date);
	e.setTime(date);
	assertEquals(e.getTime(), date);
	// section
	e.setSectionList(new ArrayList<String>());
	assertNotNull(e.getSectionList());
	// add
	e.addTicketToList(TicketFactory.CreateTicket());
	assertEquals(e.getTicketsTotal(), 2);
	assertEquals(e.getTicketsAvailable(), 2);
	UUID u2 = UUID.randomUUID();
	Ticket tick = TicketFactory.CreateTicket(u2);
	tick.setOwner("aaa");
	tick.setResellprice(3);
	e.addTicketToList(tick);
	assertEquals(e.getTicketsAvailable(), 3);
	// remove
	e.removeTicketFromList(u2);
	assertEquals(e.getTicketList().size(), 2);
	// change event obj
	Event ee = new Event(false, SportType.FOOTBALL, "M", "Potatoes", "Seotatop", "Somewhere",
		"SomewhereStadium", date, date);
	e.changeValuesFromEventObject(ee);
	assertEquals(e.isOpen(), false);

    }

    @Test
    public void testFindAndEditTicket() {
	Date date = new Date();
	Event e = new Event(false, SportType.FOOTBALL, "M", "Potatoes", "Seotatop", "Somewhere",
		"SomewhereStadium", date, date);
	Ticket t = TicketFactory.CreateTicket();
	e.addTicketToList(t);
	assertEquals(e.findAndEditTicket(t), true);
	assertEquals(e.findAndEditTicket(TicketFactory.CreateTicket()), false);
    }
    
    @Test
    public void testlistSections(){
    	Date date = new Date();
    	Event e = new Event(false, SportType.FOOTBALL, "M", "Potatoes", "Seotatop", "Somewhere",
    		"SomewhereStadium", date, date);
    	Ticket t = TicketFactory.CreateTicket(e, TicketType.SIMPLE, "1", "", "", 1, 1);
    	Ticket t1 = TicketFactory.CreateTicket(e, TicketType.SIMPLE, "1", "", "", 1, 1);
    	Ticket t2 = TicketFactory.CreateTicket(e, TicketType.SIMPLE, "2", "", "", 1, 1);
    	e.addTicketToList(t);
    	e.addTicketToList(t1);
    	e.addTicketToList(t2);
    	List<String> ls = e.listExistingSections();
    	assertTrue(ls.contains(t1.getSection()));
    	assertTrue(ls.contains(t2.getSection()));
    }

}
