package ca.ulaval.ticketmaster.model;

import java.util.UUID;

import ca.ulaval.ticketmaster.model.enums.TicketType;

public class SectionTicket extends GeneralTicket {

    private String section = "";

    public SectionTicket() {
	super();
    }

    public SectionTicket(Event _event, TicketType _type, String _owner, double _price, double _resellprice) {
	super(_event, _type, _owner, _price, _resellprice);
    }

    public SectionTicket(UUID _id, Event _event) {
	super(_id, _event);
    }

    public SectionTicket(Event _event) {
	super(_event);
    }

    public SectionTicket(UUID _id) {
	super(_id);
    }

    public SectionTicket(Event _event, TicketType _type, String _section, String _owner, double _price,
	    double _resellprice) {
	super(_event, _type, _owner, _price, _resellprice);
	section = _section;
    }

    public SectionTicket(UUID _id, Event _event, TicketType _type, String _section, String _owner,
	    double _price, double _resellprice) {
	super(_id, _event, _type, _owner, _price, _resellprice);
	section = _section;
    }

    public String getSection() {
	return section;
    }

    public void setSection(String section) {
	this.section = section;
    }

    public String toString() {
	return super.toString() + " section: " + section;
    }

    public void changeValuesFromTicketObject(Ticket _ticket) {
	super.changeValuesFromTicketObject(_ticket);
	section = _ticket.getSection();
    }
}
