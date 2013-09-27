/**
 * 
 * @author CP
 *	La javadoc, la javadoc !
 */

package ca.ulaval.ticketmaster.model;


public class Ticket {

	public enum ticketType {
		SINGLE, GENERAL, SEASON, RESERVED
	}
	
	private int id;
	private Event event;
	private ticketType type = ticketType.SEASON;
	private String section = "";
	private String seat = "";
	private String owner = "";
	private double price = 0.0;
	private double resellprice = 0.0;
	
	public Ticket(int _id, Event _event){
		id = _id;
		event = _event;
	}
	
	public Ticket(int _id){
		id = _id;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public ticketType getType() {
		return type;
	}
	public void setType(ticketType type) {
		this.type = type;
	}
	public String getSection() {
		return section;
	}
	public void setSection(String section) {
		this.section = section;
	}
	public String getSeat() {
		return seat;
	}
	public void setSeat(String seat) {
		this.seat = seat;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
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
	
	public String toString(){
		return  "TOSTRING Ticket"; //"\n id: " + id +  " type: " +  type.name() + " section: " + section + " seat: " + seat + " owner: " + owner + " price: " + price + " resellPrice: " + resellprice;
	}
	
	public void changeValuesFromTicketObject(Ticket _ticket){
		type = _ticket.getType();
		section = _ticket.getSection();
		seat = _ticket.getSeat();
		owner = _ticket.getOwner();
		price = _ticket.getPrice();
		resellprice = _ticket.getResellprice();
	}
}
