package ca.ulaval.ticketmaster.model;

import java.util.UUID;

public class GeneralTicket extends Ticket {
	public GeneralTicket(Event _event, ticketType _type, String _owner,double _price, double _resellprice) {
		super(_event,_type,_owner,_price, _resellprice);
	}

	public GeneralTicket(UUID _id, Event _event){
		super(_id,_event);
	}
	
	public GeneralTicket(Event _event){
		super(_event);
	}
	
	public GeneralTicket(UUID _id){
		super(_id);
	}
	public GeneralTicket() {
		super();
	}

	public GeneralTicket(UUID _id, Event _event, ticketType _type,
			String _owner, double _price, double _resellprice) {
		super(_id,_event,_type,_owner,_price, _resellprice);
		
	}

}
