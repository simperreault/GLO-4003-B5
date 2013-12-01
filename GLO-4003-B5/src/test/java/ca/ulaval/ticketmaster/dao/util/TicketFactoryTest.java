package ca.ulaval.ticketmaster.dao.util;

import static org.junit.Assert.*;

import java.util.UUID;

import org.junit.Test;

import ca.ulaval.ticketmaster.events.model.Event;
import ca.ulaval.ticketmaster.events.tickets.model.*;

public class TicketFactoryTest {

	@Test
	public void testTicketFactory()  {
	assertNotNull(new TicketFactory());
	}

	@Test
	public void testCreateTicketEventTicketTypeStringStringStringDoubleDouble(){
		assertTrue(TicketFactory.CreateTicket(new Event(), TicketType.GENERAL, "1", "1", "1", 1, 0) instanceof GeneralTicket);
		assertTrue(TicketFactory.CreateTicket(new Event(), TicketType.RESERVED, "1", "1", "1", 1, 0) instanceof ReservedTicket);
		assertTrue(TicketFactory.CreateTicket(new Event(), TicketType.SEASON, "1", "1", "1", 1, 0) instanceof SectionTicket);
		assertTrue(TicketFactory.CreateTicket(new Event(), TicketType.SIMPLE, "1", "1", "1", 1, 0) instanceof SectionTicket);
	}

	@Test
	public void testCreateTicket()  {
		assertTrue(TicketFactory.CreateTicket() instanceof ReservedTicket);

	}

	@Test
	public void testCreateExistingTicket()  {
		assertTrue(TicketFactory.CreateExistingTicket(UUID.randomUUID(),new Event(), TicketType.GENERAL, "1", "1", "1", 1, 0) instanceof GeneralTicket);
		assertTrue(TicketFactory.CreateExistingTicket(UUID.randomUUID(),new Event(), TicketType.RESERVED, "1", "1", "1", 1, 0) instanceof ReservedTicket);
		assertTrue(TicketFactory.CreateExistingTicket(UUID.randomUUID(),new Event(), TicketType.SEASON, "1", "1", "1", 1, 0) instanceof SectionTicket);
		assertTrue(TicketFactory.CreateExistingTicket(UUID.randomUUID(),new Event(), TicketType.SIMPLE, "1", "1", "1", 1, 0) instanceof SectionTicket);
	
	}

	@Test
	public void testCreateTicketUUID() throws Exception {
		assertTrue(TicketFactory.CreateTicket(UUID.randomUUID()) instanceof ReservedTicket);
	}

}
