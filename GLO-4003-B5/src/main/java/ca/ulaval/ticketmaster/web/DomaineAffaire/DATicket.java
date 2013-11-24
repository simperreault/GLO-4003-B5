package ca.ulaval.ticketmaster.web.DomaineAffaire;

import java.util.UUID;

import org.springframework.validation.BindingResult;

import ca.ulaval.ticketmaster.dao.util.DataManager;
import ca.ulaval.ticketmaster.model.Event;
import ca.ulaval.ticketmaster.model.Ticket;
import ca.ulaval.ticketmaster.model.enums.TicketType;
import ca.ulaval.ticketmaster.web.DomaineAffaire.proxy.ProxyHttpSession;
import ca.ulaval.ticketmaster.web.DomaineAffaire.proxy.ProxyModel;
import ca.ulaval.ticketmaster.web.converter.TicketConverter;
import ca.ulaval.ticketmaster.web.viewmodels.TicketViewModel;
import exceptions.UnauthenticatedException;
import exceptions.UnauthorizedException;

public class DATicket {
    private DataManager datamanager;

    public DATicket() {
	datamanager = new DataManager();
    }

    public void getAddTicket(String eventId, ProxyModel model, ProxyHttpSession session) throws UnauthenticatedException {
	if (!DAAuthentication.isAdmin(session))
		throw new UnauthenticatedException();
		
	    model.addAttribute("ticket", new TicketViewModel(new Event(UUID.fromString(eventId))));
	    model.addAttribute("ticketlist", TicketType.values());
    }

    public void addTicket(String eventId, TicketViewModel viewmodel, BindingResult result,
	    ProxyModel model, ProxyHttpSession session) throws UnauthorizedException {
	if (!DAAuthentication.isAdmin(session))
		throw new UnauthorizedException();
		
	    if (result.hasErrors()) {
		model.addAttribute("error", result.getAllErrors());
		model.addAttribute("ticket", viewmodel);
		model.addAttribute("ticketlist", TicketType.values());
		//return Page.TicketAdd.toString();		//TODO
	    }

	    viewmodel.setEvent(datamanager.findEvent(UUID.fromString(eventId)));
	    for (int i = 0; i < viewmodel.howMany; ++i) {
		Ticket ticket = TicketConverter.convert(viewmodel, datamanager);
		datamanager.saveTicket(ticket); // TODO What if save failed ?
	    }
    }

    public String deleteTicket(String eventId, String ticketId, ProxyHttpSession session) {
	datamanager.deleteTicket(UUID.fromString(eventId), UUID.fromString(ticketId));
	return "redirect:/event/" + eventId;

    }
}
