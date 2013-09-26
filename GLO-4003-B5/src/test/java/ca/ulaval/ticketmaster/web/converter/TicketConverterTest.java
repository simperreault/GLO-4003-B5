package ca.ulaval.ticketmaster.web.converter;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import ca.ulaval.ticketmaster.dao.util.DataManager;
import ca.ulaval.ticketmaster.model.Ticket;
import ca.ulaval.ticketmaster.web.viewmodels.TicketViewModel;

public class TicketConverterTest {
	
	@Test
	public void convertEntryToviewmodel() {
		Ticket t = new Ticket(1);
		t.setEvent(null);
		t.setOwner("owner");
		t.setPrice(1.0);
		t.setResellprice(1.0);
		t.setSeat("seat");
		t.setSection("section");
		
		TicketViewModel viewmodel = TicketConverter.convert(t);
		
		assertEquals(t.getEvent(), viewmodel.event);
		assertEquals(t.getOwner(), viewmodel.owner);
		assertEquals(t.getPrice(), viewmodel.price, 0.001);
		assertEquals(t.getResellprice(), viewmodel.resellprice, 0.001);
		assertEquals(t.getSeat(), viewmodel.seat);
		assertEquals(t.getSection(), viewmodel.section);
	}
	
	@Test
	public void convertviewmodelToEntry() {
		TicketViewModel viewmodel = new TicketViewModel();
		viewmodel.setEvent(null);
		viewmodel.setOwner("owner");
		viewmodel.setPrice(1.0);
		viewmodel.setResellprice(1.0);
		viewmodel.setSeat("seat");
		viewmodel.setSection("section");
		
		Ticket t = TicketConverter.convert(viewmodel, new DataManager());
		
		assertEquals(t.getEvent(), viewmodel.getEvent());
		assertEquals(t.getOwner(), viewmodel.getOwner());
		assertEquals(t.getPrice(), viewmodel.getPrice(), 0.001);
		assertEquals(t.getResellprice(), viewmodel.getResellprice(), 0.001);
		assertEquals(t.getSeat(), viewmodel.getSeat());
		assertEquals(t.getSection(), viewmodel.getSection());
	}
}