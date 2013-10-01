package ca.ulaval.ticketmaster.web.converter;

import ca.ulaval.ticketmaster.dao.util.DataManager;
import ca.ulaval.ticketmaster.dao.util.TicketFactory;
import ca.ulaval.ticketmaster.model.Ticket;
import ca.ulaval.ticketmaster.model.Ticket.ticketType;
import ca.ulaval.ticketmaster.web.viewmodels.TicketViewModel;

public class TicketConverter {

	static public Ticket convert(TicketViewModel viewModel, DataManager datamanager) {
		Ticket entry = TicketFactory.CreateTicket(viewModel.getEvent(),
				ticketType.valueOf(viewModel.getType()),
				viewModel.getSection(),
				viewModel.getSeat(),
				viewModel.getOwner(),
				viewModel.getPrice(),
				viewModel.getResellprice());
		return entry;
	}
	
	static public TicketViewModel convert(Ticket entry) {
		TicketViewModel viewModel = new TicketViewModel(entry.getEvent()); 
		viewModel.setSection(entry.getSection());
		viewModel.setSeat(entry.getSeat());
		viewModel.setOwner(entry.getOwner());
		viewModel.setPrice(entry.getPrice());
		viewModel.setResellprice(entry.getResellprice());
		viewModel.setEvent(entry.getEvent());
		viewModel.setType(entry.getType().toString());
		
		return viewModel;
	}
	
}
