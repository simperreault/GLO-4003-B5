package ca.ulaval.ticketmaster.events.tickets.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import ca.ulaval.ticketmaster.dao.util.DataManager;
import ca.ulaval.ticketmaster.dao.util.TicketFactory;
import ca.ulaval.ticketmaster.events.tickets.model.Ticket;
import ca.ulaval.ticketmaster.events.tickets.model.TicketConverter;
import ca.ulaval.ticketmaster.events.tickets.model.TicketType;
import ca.ulaval.ticketmaster.events.tickets.model.TicketViewModel;

//Note : extends UserConverter afin d'avoir la classe comme "verifiee" par EclEmma
public class TicketConverterTest extends TicketConverter {

    @Test
    public void convertEntryToviewmodel() {
	Ticket t = TicketFactory.CreateTicket(null, TicketType.SEASON, "section", "seat", "owner", 1.0, 1.0);
	TicketViewModel viewmodel = TicketConverter.convert(t);

	assertEquals(t.getEvent(), viewmodel.event);
	assertEquals(t.getOwner(), viewmodel.owner);
	assertEquals(t.getPrice(), Double.parseDouble(viewmodel.price), 0.001);
	assertEquals(t.getResellprice(), Double.parseDouble(viewmodel.resellprice), 0.001);
	assertEquals(t.getSeat(), viewmodel.seat);
	assertEquals(t.getSection(), viewmodel.section);
    }

    @Test
    public void convertviewmodelToEntry() {
	TicketViewModel viewmodel = new TicketViewModel();
	viewmodel.setEvent(null);
	viewmodel.setOwner("owner");
	viewmodel.setPrice("1.0");
	viewmodel.setResellprice("1.0");
	viewmodel.setSeat("");
	viewmodel.setSection("section");
	viewmodel.setType(TicketType.SEASON);

	Ticket t = TicketConverter.convert(viewmodel, new DataManager());

	assertEquals(t.getEvent(), viewmodel.getEvent());
	assertEquals(t.getOwner(), viewmodel.getOwner());
	assertEquals(t.getPrice(), Double.parseDouble(viewmodel.getPrice()), 0.001);
	assertEquals(t.getResellprice(), Double.parseDouble(viewmodel.getResellprice()), 0.001);
	assertEquals(t.getSeat(), viewmodel.getSeat());
	assertEquals(t.getSection(), viewmodel.getSection());
	assertEquals(t.getType(), viewmodel.getType());
    }
}
