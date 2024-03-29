/**
 * 
 * @author Mathieu Bolduc
 * 
 * */

package ca.ulaval.ticketmaster.events.tickets;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.ticketmaster.events.tickets.model.ReservedTicket;
import ca.ulaval.ticketmaster.events.tickets.model.Ticket;
import ca.ulaval.ticketmaster.events.tickets.model.TicketType;

@RunWith(MockitoJUnitRunner.class)
public class TicketTest {

    @Test
    public void TestTicket() {
	Ticket t = new ReservedTicket();
	assertNotNull(t);
    }

    @Test
    public void TestGetID() {
	Ticket t = new ReservedTicket();
	assertNotNull(t.getId());
    }

    @Test
    public void TestgetType() {
	Ticket t = new ReservedTicket();
	t.setType(TicketType.GENERAL);
	assertEquals(t.getType(), TicketType.GENERAL);
    }

    @Test
    public void TestsetType() {
	Ticket t = new ReservedTicket();
	t.setType(TicketType.GENERAL);
	assertEquals(t.getType(), TicketType.GENERAL);
    }

    @Test
    public void TestgetSection() {
	Ticket t = new ReservedTicket();
	t.setSection("A");
	assertEquals(t.getSection(), "A");
    }

    @Test
    public void TestsetSection() {
	Ticket t = new ReservedTicket();
	t.setSection("A");
	assertEquals(t.getSection(), "A");
    }

    @Test
    public void TestgetSeat() {
	Ticket t = new ReservedTicket();
	t.setSeat("22");
	assertEquals(t.getSeat(), "22");
    }

    @Test
    public void TestsetSeat() {
	Ticket t = new ReservedTicket();
	t.setSeat("22");
	assertEquals(t.getSeat(), "22");
    }

    @Test
    public void TestgetOwner() {
	Ticket t = new ReservedTicket();
	t.setSeat("bob");
	assertEquals(t.getSeat(), "bob");
    }

    @Test
    public void TestsetOwner() {
	Ticket t = new ReservedTicket();
	t.setOwner("bob");
	assertEquals(t.getOwner(), "bob");
    }
}
