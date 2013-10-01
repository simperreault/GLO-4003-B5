package ca.ulaval.ticketmaster.web.viewmodels;

import org.hibernate.validator.constraints.NotEmpty;

import ca.ulaval.ticketmaster.model.Event;
import ca.ulaval.ticketmaster.model.enums.TicketType;

public class TicketViewModel {
	//public int id;
	public Event event;
	//public ticketType type;
	public TicketType type;
	@NotEmpty(message="Section ne doit pas etre vide")
	public String section;
	@NotEmpty(message="Siege ne doit pas etre vide")
	public String seat;
	public String owner;
	public double price;
	public double resellprice;
	
	public int howMany = 1;
	
	public TicketViewModel() {
	}
	
	public TicketViewModel(Event event) {
		this.event = event;
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

	public int getHowMany() {
		return howMany;
	}

	public void setHowMany(int howMany) {
		this.howMany = howMany;
	}

	public TicketType getType() {
		return type;
	}

	public void setType(TicketType type) {
		this.type = type;
	}
	
}
