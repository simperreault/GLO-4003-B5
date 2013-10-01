/**
 * 
 * @author CP
 *	La javadoc, la javadoc !
 */

package ca.ulaval.ticketmaster.model;

import java.util.UUID;

import ca.ulaval.ticketmaster.model.Ticket.ticketType;

public class ReservedTicket extends SectionTicket {
	private String seat = "";
	
	public ReservedTicket(Event _event, ticketType _type, String _section, String _seat, String _owner, double _price, double _resellprice){
		super( _event,  _type, _section,  _owner,  _price, _resellprice);
		seat = _seat;
	}
	public ReservedTicket(Event _event, ticketType _type, String _owner,double _price, double _resellprice) {
		super(_event,_type,_owner,_price, _resellprice);
	}

	public ReservedTicket(UUID _id, Event _event){
		super(_id,_event);
	}
	
	public ReservedTicket(Event _event){
		super(_event);
	}
	
	public ReservedTicket(UUID _id){
		super(_id);
	}
	public ReservedTicket() {
		super();
	}
	public ReservedTicket(UUID _id, Event _event, ticketType _type,
			String _section, String _seat, String _owner, double _price,
			double _resellprice) {
		super( _id,  _event,  _type,
			 _section,  _owner,  _price,
			 _resellprice);
		seat = _seat;
	}
	public String getSeat() {
		return seat;
	}
	public void setSeat(String seat) {
		this.seat = seat;
	}
	public String toString(){
		return  super.toString()  + " seat: " + seat ;
	}
	
	public void changeValuesFromTicketObject(Ticket _ticket){
		super.changeValuesFromTicketObject(_ticket);
		seat = _ticket.getSeat();

	}
}

