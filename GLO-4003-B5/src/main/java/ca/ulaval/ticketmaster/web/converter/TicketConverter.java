package ca.ulaval.ticketmaster.web.converter;

import ca.ulaval.ticketmaster.dao.util.DataManager;
import ca.ulaval.ticketmaster.model.Ticket;
import ca.ulaval.ticketmaster.model.Ticket.ticketType;
import ca.ulaval.ticketmaster.web.viewmodels.TicketViewModel;

public class TicketConverter {

	static public Ticket convert(TicketViewModel viewModel, DataManager datamanager) {
		Ticket entry = new Ticket();
		entry.setSection(viewModel.getSection());
		entry.setSeat(viewModel.getSeat());
		entry.setOwner(viewModel.getOwner());
		entry.setPrice(viewModel.getPrice());
		entry.setResellprice(viewModel.getResellprice());
		entry.setEvent(viewModel.getEvent());
		entry.setType(ticketType.valueOf(viewModel.getType()));
		
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
