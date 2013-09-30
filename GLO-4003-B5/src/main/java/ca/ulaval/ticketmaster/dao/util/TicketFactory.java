package ca.ulaval.ticketmaster.dao.util;

import ca.ulaval.ticketmaster.model.Event;
import ca.ulaval.ticketmaster.model.Ticket;
import ca.ulaval.ticketmaster.model.Ticket.ticketType;

public class TicketFactory {

	public TicketFactory(){
		
	}
	
	public Ticket CreateGeneralTicket(Event _event, double _price){
		return new Ticket(_event,ticketType.AdmissionGenerale,"","","",_price);
	}
}
