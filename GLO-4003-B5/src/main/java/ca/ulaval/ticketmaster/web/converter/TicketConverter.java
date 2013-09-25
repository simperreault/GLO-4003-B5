package ca.ulaval.ticketmaster.web.converter;

import ca.ulaval.ticketmaster.model.Ticket;
import ca.ulaval.ticketmaster.web.viewmodels.TicketViewModel;

public class TicketConverter {

	static public Ticket convert(TicketViewModel viewModel) {
		Ticket entry = new Ticket(2); // TODO Generate random id (Passing data manager)
		entry.setSection(viewModel.getSection());
		entry.setSeat(viewModel.getSeat());
		entry.setOwner(viewModel.getOwner());
		entry.setPrice(viewModel.getPrice());
		entry.setResellprice(viewModel.getResellprice());
		
		return entry;
	}
	
	static public TicketViewModel convert(Ticket entry) {
		TicketViewModel viewModel = new TicketViewModel(); 
		viewModel.setSection(entry.getSection());
		viewModel.setSeat(entry.getSeat());
		viewModel.setOwner(entry.getOwner());
		viewModel.setPrice(entry.getPrice());
		viewModel.setResellprice(entry.getResellprice());
		
		return viewModel;
	}
	
}
