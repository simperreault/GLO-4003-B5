/**
 * 
 * @author CP
 *	La javadoc, la javadoc !
 */

package ca.ulaval.billet.model;



public class Ticket {

	public enum ticketType {
		SINGLE, GENERAL, SEASON, RESERVED
	}
	
	private int id;
	private Event event;
	private ticketType type;
	private String section;
	private int seat;
	private String owner;
	private double price;
	private double resellprice;
	
	public Ticket(int _id, Event _event){
		
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
	public int getSeat() {
		return seat;
	}
	public void setSeat(int seat) {
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
	
}
