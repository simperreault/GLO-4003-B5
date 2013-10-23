package ca.ulaval.ticketmaster.web.DomaineAffaire;

import java.util.ArrayList;
import java.util.UUID;

import ca.ulaval.ticketmaster.dao.util.DataManager;
import ca.ulaval.ticketmaster.model.Ticket;
import ca.ulaval.ticketmaster.web.DomaineAffaire.proxy.ProxyHttpSession;
import ca.ulaval.ticketmaster.web.DomaineAffaire.proxy.ProxyModel;

public class DABasket {
    private DataManager datamanager;

    public DABasket() {
	datamanager = new DataManager();
    }

    // a cause du cast d'un objet vers arraylist<Ticket> je n'ai pas
    // trouvé de solution pour enlever le warning
    @SuppressWarnings("unchecked")
    public String addToBasket(String eventId, String ticketId, ProxyModel model, ProxyHttpSession session) {
	if (DAAuthentication.isLogged(session)) {
	    ArrayList<Ticket> list;
	    if (session.getAttribute("basket") != null) {
		list = (ArrayList<Ticket>) session.getAttribute("basket");
		model.addAttribute("msg", "old array");
	    } else // le panier est vide
	    {
		list = new ArrayList<Ticket>();
		model.addAttribute("msg", "New array");
	    }
	    list.add(datamanager.findTicket(UUID.fromString(eventId), UUID.fromString(ticketId)));
	    session.setAttribute("basket", list);
	} else {
	    model.addAttribute("msg", "Veuillez vous connecter pour acheter des billets");
	    // TODO coder un message qui demande de se logger
	}
	return "redirect:/event/" + eventId;

    }

    @SuppressWarnings("unchecked")
    public String removeFromBasket(String eventId, String ticketId, ProxyModel model, ProxyHttpSession session) {

	// isFound = true;
	if (session.getAttribute("basket") != null) {
	    ArrayList<Ticket> list = (ArrayList<Ticket>) session.getAttribute("basket");
	    int i = 0;
	    boolean isFound = false;
	    while (i < list.size() && !isFound) {
		if (list.get(i).getId().toString().equals(ticketId)) {
		    isFound = true;
		    list.remove(i);
		    session.setAttribute("basket", list);
		}
		++i;
	    }

	}
	return "redirect:/Basket";
    }

    @SuppressWarnings("unchecked")
    public String copyToBasket(String eventId, String ticketId, ProxyHttpSession session) {
	if (DAAuthentication.isLogged(session)) {
	    ArrayList<Ticket> list;
	    if (session.getAttribute("basket") != null) {
		list = (ArrayList<Ticket>) session.getAttribute("basket");
		list.add(datamanager.findTicket(UUID.fromString(eventId), UUID.fromString(ticketId)));
		session.setAttribute("basket", list);
	    }

	}
	return "redirect:/Basket";

    }
}
