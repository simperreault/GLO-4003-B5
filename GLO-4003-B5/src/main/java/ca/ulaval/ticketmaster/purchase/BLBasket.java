package ca.ulaval.ticketmaster.purchase;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.validation.BindingResult;

import ca.ulaval.ticketmaster.dao.util.DataManager;
import ca.ulaval.ticketmaster.events.tickets.model.Ticket;
import ca.ulaval.ticketmaster.execptions.InvalidFormExceptions;
import ca.ulaval.ticketmaster.execptions.InvalidPurchaseException;
import ca.ulaval.ticketmaster.execptions.UnauthenticatedException;
import ca.ulaval.ticketmaster.home.DAAuthentication;
import ca.ulaval.ticketmaster.springproxy.ProxyHttpSession;
import ca.ulaval.ticketmaster.springproxy.ProxyModel;

public class BLBasket {
    private DataManager datamanager;

    public BLBasket() {
	datamanager = new DataManager();
    }

    private void setBasket(ArrayList<Ticket> list, ProxyHttpSession session) {
	session.setAttribute("basket", list);
	session.setAttribute("basketDisplay", datamanager.regroupSimilarTicketsByEvents(list));
    }

    // a cause du cast d'un objet vers arraylist<Ticket> je n'ai pas
    // trouv� de solution pour enlever le warning
    @SuppressWarnings("unchecked")
    public String addToBasket(String eventId, String ticketId, ProxyHttpSession session) {
	if (DAAuthentication.isLogged(session)) {
	    ArrayList<Ticket> list;
	    if (session.getAttribute("basket") != null) {
		list = (ArrayList<Ticket>) session.getAttribute("basket");
	    } else { // le panier est vide
		list = new ArrayList<Ticket>();
	    }

	    list.add(datamanager.findTicket(UUID.fromString(eventId), UUID.fromString(ticketId)));
	    this.setBasket(list, session);
	} else {
	    // Not logged
	}
	return "redirect:/event/" + eventId;

    }

    @SuppressWarnings("unchecked")
    public String addMultipleTicketsToBasket(String eventId, String ticketId, String nbSimilarTickets,
	    ProxyModel model, ProxyHttpSession session) {
	if (DAAuthentication.isLogged(session)) {

	    ArrayList<Ticket> listOfBasket;
	    if (session.getAttribute("basket") != null) {
		listOfBasket = (ArrayList<Ticket>) session.getAttribute("basket");
	    } else // le panier est vide
	    {
		// datamanager.countSimilarTickets(_ticketList, _type, _section)
		listOfBasket = new ArrayList<Ticket>();
	    }

	    int nbSimilarTicketsToAdd = Integer.parseInt(nbSimilarTickets);

	    // 0- The initial ticket (it may be taken due to race condition
	    // (i.e. bought) I think @addToBasket
	    // so we have to make sure it isn't taken
	    Ticket ticketCmp = datamanager.findTicket(UUID.fromString(eventId), UUID.fromString(ticketId));

	    // 1- Find similar tickets to the one @ UUID.fromString(ticketId)
	    List<Ticket> listTickets = datamanager.findSimilarTickets(
		    datamanager.findAllTickets(UUID.fromString(eventId)), // should
		    // filter
		    // sold
		    ticketCmp.getType(), ticketCmp.getSection());

	    // 2- Filter tickets already in basket
	    listTickets = datamanager.filterListWithList(listTickets, listOfBasket);

	    // 3- Add them
	    for (int i = 0; i < listTickets.size() && i < nbSimilarTicketsToAdd; ++i) {
		listOfBasket.add(listTickets.get(i));
	    }
	    this.setBasket(listOfBasket, session);

	} else {
	    // Not logged
	}
	return "redirect:/event/" + eventId;

    }

    @SuppressWarnings("unchecked")
    public String removeFromBasket(String eventId, String ticketId, ProxyHttpSession session) {

	// isFound = true;
	if (session.getAttribute("basket") != null) {
	    ArrayList<Ticket> list = (ArrayList<Ticket>) session.getAttribute("basket");
	    int i = 0;
	    boolean isFound = false;
	    while (i < list.size() && !isFound) {
		if (list.get(i).getId().toString().equals(ticketId)) {
		    isFound = true;
		    list.remove(i);
		    this.setBasket(list, session);
		}
		++i;
	    }

	}
	return "redirect:/Basket";
    }

    public void removeAllFromBasket(ProxyHttpSession session) throws UnauthenticatedException {
	if (!DAAuthentication.isLogged(session))
	    throw new UnauthenticatedException();

	ArrayList<Ticket> list = new ArrayList<Ticket>();
	this.setBasket(list, session);
    }

    @SuppressWarnings("unchecked")
    public void copyToBasket(String eventId, String ticketId, int amount, ProxyModel model,
	    ProxyHttpSession session) throws UnauthenticatedException {

	if (!DAAuthentication.isLogged(session))
	    throw new UnauthenticatedException();

	model.addAttribute("message", "");
	Ticket source = datamanager.findTicket(UUID.fromString(eventId), UUID.fromString(ticketId));
	// Liste des Ticket Displayed
	List<ArrayList<Ticket>> ticketDisplay = (List<ArrayList<Ticket>>) session
		.getAttribute("basketDisplay");
	// Les ticket dans le basket
	int i = 0;
	Ticket current = ticketDisplay.get(i).get(0);
	// Trouver � quelle section de BasketDisplay le source Ticket appartient
	// while (!((current.getType().equals(source.getType())) &&
	// (current.getEvent().getId().equals(source.getEvent().getId()) &&
	// (current.getSection().equals(source.getSection())))))
	// Devrait tt le temps etre en position 0 sinon remettre ligne en haut
	while (!(current.getId().equals(source.getId()))) {
	    ++i;
	    current = ticketDisplay.get(i).get(0);
	}
	// si on ajoute un billet
	if (amount > ticketDisplay.get(i).size()) {
	    ArrayList<Ticket> basket = (ArrayList<Ticket>) session.getAttribute("basket");
	    // Les tickets de l'�vent du source Ticket
	    List<Ticket> eventTicket = datamanager.findAllTickets(source.getEvent().getId());
	    // Trouver les Tickets similaires
	    List<Ticket> similarTicket = datamanager.findSimilarTickets(eventTicket, source.getType(),
		    source.getSection());
	    // donne les billets similaire qui ne sont pas dans le panier
	    List<Ticket> freeTicket = datamanager.filterListWithList(similarTicket, ticketDisplay.get(i));
	    // s'il n'y a pas asser de ticket
	    if (freeTicket.size() < amount - ticketDisplay.get(i).size()) {
		model.addAttribute("message", "Erreur : Il ne reste que " + freeTicket.size()
			+ " billets de cette cat�gorie");
	    } else {
		for (int j = 0; j < amount - ticketDisplay.get(i).size(); ++j) {
		    basket.add(freeTicket.get(j));
		}
		this.setBasket(basket, session);
	    }
	} else { // si on supprime un billet
	    for (int j = (ticketDisplay.get(i).size() - amount) - 1; j >= 0; --j) {
		this.removeFromBasket(eventId, ticketDisplay.get(i).get(j).getId().toString(), session);
	    }
	    this.setBasket((ArrayList<Ticket>) session.getAttribute("basket"), session);
	}
    }

    @SuppressWarnings("unchecked")
    public void purchase(ProxyHttpSession session, ProxyModel model, BindingResult result)
	    throws UnauthenticatedException, InvalidFormExceptions, InvalidPurchaseException {
	if (!DAAuthentication.isLogged(session))
	    throw new UnauthenticatedException();

	if (result.hasErrors()) {
	    model.addAttribute("error", result.getAllErrors());
	    throw new InvalidFormExceptions();
	}

	ArrayList<Ticket> list = new ArrayList<>();
	ArrayList<Ticket> invalidTickets = null;

	if (session.getAttribute("singleTicket") != null)
	    list = (ArrayList<Ticket>) session.getAttribute("singleTicket");
	else if (session.getAttribute("basket") != null)
	    list = (ArrayList<Ticket>) session.getAttribute("basket");

	if (list.size() > 0) {
	    invalidTickets = datamanager.buyTickets(list, (String) session.getAttribute("sesusername"));

	    if (!(invalidTickets == null))
		throw new InvalidPurchaseException();

	} else {
	    model.addAttribute("message", "Erreur : Le panier est vide");
	}
    }

    public String buySingleTicket(String eventId, String ticketId, ProxyHttpSession session) {
	if (DAAuthentication.isLogged(session)) {
	    ArrayList<Ticket> list = new ArrayList<>();

	    list.add(datamanager.findTicket(UUID.fromString(eventId), UUID.fromString(ticketId)));
	    // invalidTickets = datamanager.buyTickets(list, (String)
	    // session.getAttribute("sesusername"));
	    // invalidTickets == null &&
	    if (list.size() == 1) // ticket is valid
	    {
		session.setAttribute("singleTicket", list);
	    }
	}
	return "redirect:/purchase/Purchase";

    }

}
