/**
 * 
 * @author Mathieu Bolduc
 * 
 * */

package ca.ulaval.ticketmaster.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.ticketmaster.model.Ticket.ticketType;

@RunWith(MockitoJUnitRunner.class)
public class TicketTest {

	@Test
	public void TestTicket(){
		Ticket t = new Ticket(1);
		assertNotNull(t);
		assertEquals(t.getId(),1);
	}
	
	@Test
	public void TestGetID(){
		Ticket t = new Ticket(1);
		assertEquals(t.getId(),1);
	}
	
	@Test
	public void TestsetId() {
		Ticket t = new Ticket(1);
		t.setId(2);
		assertEquals(t.getId(),2);
	}
	
	@Test
	public void TestgetType() {
		Ticket t = new Ticket(1);
		t.setType(ticketType.GENERAL);
		assertEquals(t.getType(),ticketType.GENERAL);
	}
	
	@Test
	public void TestsetType() {
		Ticket t = new Ticket(1);
		t.setType(ticketType.GENERAL);
		assertEquals(t.getType(),ticketType.GENERAL);
	}
	
	@Test
	public void TestgetSection() {
		Ticket t = new Ticket(1);
		t.setSection("A");
		assertEquals(t.getSection(),"A");
	}
	
	@Test
	public void TestsetSection() {
		Ticket t = new Ticket(1);
		t.setSection("A");
		assertEquals(t.getSection(),"A");
	}
	
	@Test
	public void TestgetSeat() {
		Ticket t = new Ticket(1);
		t.setSeat("22");
		assertEquals(t.getSeat(),"22");
	}
	
	@Test
	public void TestsetSeat() {
		Ticket t = new Ticket(1);
		t.setSeat("22");
		assertEquals(t.getSeat(),"22");
	}
	
	@Test
	public void TestgetOwner() {
		Ticket t = new Ticket(1);
		t.setSeat("bob");
		assertEquals(t.getSeat(),"bob");
	}
	
	@Test
	public void TestsetOwner() {
		Ticket t = new Ticket(1);
		t.setOwner("bob");
		assertEquals(t.getOwner(),"bob");
	}
	/**
	@Test
	public void TestgetPrice() {
		Ticket t = new Ticket(1);
		t.setPrice(12.12);
		assertEquals(t.getPrice(), 12.12);
	}
	
	public void TestsetPrice() {
		Ticket t = new Ticket(1);
		t.setPrice(12.12);
		assertEquals(t.getPrice(), 12.12);
	}
	
	
	public double getResellprice() {
		return resellprice;
	}
	public void setResellprice(double resellprice) {
		this.resellprice = resellprice;
	}
	public Event getEvent() {
		return event;
	}
	public void setEvent(Event event) {
		this.event = event;
	}
	
	*/
}
