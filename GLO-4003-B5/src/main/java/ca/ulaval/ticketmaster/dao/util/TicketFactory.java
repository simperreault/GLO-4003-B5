package ca.ulaval.ticketmaster.dao.util;

import java.util.UUID;

import ca.ulaval.ticketmaster.events.model.Event;
import ca.ulaval.ticketmaster.events.tickets.model.GeneralTicket;
import ca.ulaval.ticketmaster.events.tickets.model.ReservedTicket;
import ca.ulaval.ticketmaster.events.tickets.model.SectionTicket;
import ca.ulaval.ticketmaster.events.tickets.model.Ticket;
import ca.ulaval.ticketmaster.events.tickets.model.TicketType;

public class TicketFactory {

    public TicketFactory() {

    }

    /*
     * General call which handles all cases
     */
    public static Ticket CreateTicket(Event _event, TicketType _type, String _section, String _seat,
	    String _owner, double _price, double _resellprice) {
	switch (_type) {
	case GENERAL:
	    return new GeneralTicket(_event, _type, _owner, _price, _resellprice);
	case RESERVED:
	    return new ReservedTicket(_event, _type, _section, _seat, _owner, _price, _resellprice);
	case SEASON:
	    return new SectionTicket(_event, _type, _section, _owner, _price, _resellprice);
	case SIMPLE:
	    return new SectionTicket(_event, _type, _section, _owner, _price, _resellprice);
	}
	return null;
    }

    public static Ticket CreateTicket() {
	return new ReservedTicket();
    }

    public static Ticket CreateExistingTicket(UUID _id, Event _event, TicketType _type, String _section,
	    String _seat, String _owner, double _price, double _resellprice) {
	switch (_type) {
	case GENERAL:
	    return new GeneralTicket(_id, _event, _type, _owner, _price, _resellprice);
	case RESERVED:
	    return new ReservedTicket(_id, _event, _type, _section, _seat, _owner, _price, _resellprice);
	case SEASON:
	    return new SectionTicket(_id, _event, _type, _section, _owner, _price, _resellprice);
	case SIMPLE:
	    return new SectionTicket(_id, _event, _type, _section, _owner, _price, _resellprice);
	}
	return null;
    }

    public static Ticket CreateTicket(UUID _id) {
	return new ReservedTicket(_id);
    }
}
