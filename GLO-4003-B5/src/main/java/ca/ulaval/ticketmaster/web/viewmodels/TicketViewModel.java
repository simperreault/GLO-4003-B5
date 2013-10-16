package ca.ulaval.ticketmaster.web.viewmodels;

import org.hibernate.validator.constraints.NotEmpty;

import ca.ulaval.ticketmaster.model.Event;
import ca.ulaval.ticketmaster.model.enums.TicketType;
import ca.ulaval.ticketmaster.web.viewmodels.validator.CheckIsPrice;

public class TicketViewModel {

    public Event event;
    public TicketType type;
    @NotEmpty(message = "'Section' ne doit pas etre vide.")
    public String section = "";
    @NotEmpty(message = "'Siège' ne doit pas etre vide.")
    public String seat = "";
    //@NotEmpty(message = "'Propriétaire' ne doit pas etre vide.")
    public String owner = "";
    @CheckIsPrice(message = "'Prix' doit être Numérique, différent de 0 et positif.")
    public String price = "0.0";
    // @CheckIsPrice(message =
    // "'Prix de revente' doit être Numérique, différent de 0 et positif.")
    public String resellprice = "0.0";

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

    public String getPrice() {
	return price;
    }

    public void setPrice(String price) {
	this.price = price;
    }

    public String getResellprice() {
	return resellprice;
    }

    public void setResellprice(String resellprice) {
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
