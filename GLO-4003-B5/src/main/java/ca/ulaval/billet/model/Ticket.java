/**
 * 
 * @author CP
 *	La javadoc, la javadoc !
 */

package ca.ulaval.billet.model;

import java.util.Iterator;



public class Ticket {

	public enum ticketType {
		SINGLE, GENERAL, SEASON, RESERVED
	}
	
	private int id;
	private Event event;
	private ticketType type;
	private String section;
	private String seat;
	private String owner;
	private double price;
	private double resellprice;
	
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
		return  "\n id: " + id +  " type: " + type.name() + " section: " + section + " seat: " + seat + " owner: " + owner + " price: " + price + " resellPrice: " + resellprice;
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
