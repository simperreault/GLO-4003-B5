package ca.ulaval.ticketmaster.events.tickets.model;

import ca.ulaval.ticketmaster.dao.util.DataManager;
import ca.ulaval.ticketmaster.dao.util.TicketFactory;

public class TicketConverter {

    static public Ticket convert(TicketViewModel viewModel, DataManager datamanager) {
	Ticket entry = TicketFactory.CreateTicket(viewModel.getEvent(), viewModel.getType(),
		viewModel.getSection(), viewModel.getSeat(), viewModel.getOwner(),
		Double.parseDouble(viewModel.getPrice()), Double.parseDouble(viewModel.getResellprice()));
	return entry;
    }

    static public TicketViewModel convert(Ticket entry) {
	TicketViewModel viewModel = new TicketViewModel(entry.getEvent());
	viewModel.setSection(entry.getSection());
	viewModel.setSeat(entry.getSeat());
	viewModel.setOwner(entry.getOwner());
	viewModel.setPrice(entry.getPrice() + "");
	viewModel.setResellprice(entry.getResellprice() + "");
	viewModel.setEvent(entry.getEvent());
	viewModel.setType(entry.getType());

	return viewModel;
    }

}
