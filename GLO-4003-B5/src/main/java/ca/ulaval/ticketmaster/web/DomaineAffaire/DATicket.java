package ca.ulaval.ticketmaster.web.DomaineAffaire;

import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import ca.ulaval.ticketmaster.dao.util.DataManager;
import ca.ulaval.ticketmaster.model.Event;
import ca.ulaval.ticketmaster.model.Ticket;
import ca.ulaval.ticketmaster.model.enums.TicketType;
import ca.ulaval.ticketmaster.web.converter.TicketConverter;
import ca.ulaval.ticketmaster.web.viewmodels.TicketViewModel;

public class DATicket {
	private DataManager datamanager;
	public DATicket(){
		datamanager = new DataManager();
	}
	

	public static String getAddTicket(String eventId, Model model, HttpSession session)
	{
		if (DAAuthentication.isAdmin(session))
		{
			model.addAttribute("ticket", new TicketViewModel(new Event(UUID.fromString(eventId))));
			model.addAttribute("ticketlist", TicketType.values());
		return Page.TicketAdd.toString();
		}else{
			return Page.Error403.toString();
		}
	}
	
	public String addTicket(String eventId, TicketViewModel viewmodel, BindingResult result,Model model, HttpSession session)
	{
		if (DAAuthentication.isAdmin(session))
		{
			if (result.hasErrors()) {
			    model.addAttribute("error", result.getAllErrors());
			    model.addAttribute("ticket", viewmodel);
			    model.addAttribute("ticketlist", TicketType.values());
			    return Page.TicketAdd.toString();
			}

			viewmodel.setEvent(datamanager.findEvent(UUID.fromString(eventId)));
			for (int i = 0; i < viewmodel.howMany; ++i) {
			    Ticket ticket = TicketConverter.convert(viewmodel, datamanager);
			    datamanager.saveTicket(ticket); // TODO What if save failed ?
			}
			return "redirect:/event/" + eventId;
			
		}else{
			return Page.Error403.toString();
		}
	}
	
	public String deleteTicket(String eventId,String ticketId, HttpSession session){
		datamanager.deleteTicket(UUID.fromString(eventId), UUID.fromString(ticketId));
		 return "redirect:/event/" + eventId;
		
	}
}
