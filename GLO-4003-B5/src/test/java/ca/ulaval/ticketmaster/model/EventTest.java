package ca.ulaval.ticketmaster.model;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.ticketmaster.model.Event.Sport;

@RunWith(MockitoJUnitRunner.class)
public class EventTest {

	@Test
	public void testEventInt() {
		Event e = new Event(1);
		assertNotNull(e);
	}

	@Test
	public void testEventIntBooleanIntIntSportStringStringStringStringStringDateDate() {
		Date date = new Date();
		Event e = new Event(1, false, 0, 0, Sport.Football, "M", "Potatoes", "Seotatop", "Somewhere", "SomewhereStadium",date , date);
		assertNotNull(e);
		assertEquals(e.getSport(),Sport.Football);
	}

	@Test
	public void testToString() {
		Date date = new Date();
		Event e = new Event(1, false, 0, 0, Sport.Football, "M", "Potatoes", "Seotatop", "Somewhere", "SomewhereStadium",date , date);
		assertNotNull(e.toString());		
	}

	@Test
	public void testGetTicketList() {
		Date date = new Date();
		Event e = new Event(1, false, 0, 0, Sport.Football, "M", "Potatoes", "Seotatop", "Somewhere", "SomewhereStadium",date , date);
		ArrayList<Ticket> a = new ArrayList<Ticket>();
		a.add(new Ticket(1));
		e.setTicketList(a);
		assertEquals(e.getTicketList(),a);
	}

	@Test
	public void testSetTicketList() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetId() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetId() {
		fail("Not yet implemented");
	}

	@Test
	public void testIsOpen() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetOpen() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetTicketsTotal() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetTicketsTotal() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetTicketsAvailable() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetTicketsAvailable() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetSport() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetSport() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetGender() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetGender() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetHomeTeam() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetHomeTeam() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetVisitorsTeam() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetVisitorsTeam() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetLocation() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetLocation() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetStadium() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetStadium() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetDate() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetDate() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetTime() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetTime() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetSectionList() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetSectionList() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddTicketToList() {
		fail("Not yet implemented");
	}

	@Test
	public void testRemoveTicketFromList() {
		fail("Not yet implemented");
	}

	@Test
	public void testChangeValuesFromEventObject() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindAndEditTicket() {
		fail("Not yet implemented");
	}

}
