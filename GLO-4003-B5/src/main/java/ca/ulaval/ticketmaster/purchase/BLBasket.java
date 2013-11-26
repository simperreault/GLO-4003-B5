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

	public BLBasket(DataManager m) {
		datamanager = m;
	}

	public void setBasket(ArrayList<Ticket> list, ProxyHttpSession session) {
		session.setAttribute("basket", list);
		session.setAttribute("basketDisplay", datamanager.regroupSimilarTicketsByEvents(list));
	}

	// a cause du cast d'un objet vers arraylist<Ticket> je n'ai pas
	// trouvé de solution pour enlever le warning
	@SuppressWarnings("unchecked")
	public String addToBasket(String eventId, String ticketId, ProxyHttpSession session) {
		if (DAAuthentication.isLogged(session)) {
			ArrayList<Ticket> list;
			if (session.getAttribute("basket") != null) {
				list = (ArrayList<Ticket>) session.getAttribute("basket");
			} else { // le panier est vide
				list = new ArrayList<Ticket>();
			}
			Ticket temp = datamanager.findTicket(UUID.fromString(eventId), UUID.fromString(ticketId));
			list.add(temp);
			this.setBasket(list, session);
		} else {
			// Not logged
		}
		return "redirect:/event/" + eventId;

	}

	@SuppressWarnings("unchecked")
	public String addMultipleTicketsToBasket(String eventId, String ticketId, String nbSimilarTickets, ProxyModel model, ProxyHttpSession session) {
		if (DAAuthentication.isLogged(session)) {
			ArrayList<Ticket> listOfBasket;
			if (session.getAttribute("basket") != null) {
				listOfBasket = (ArrayList<Ticket>) session.getAttribute("basket");
			} else // le panier est vide
			{
				listOfBasket = new ArrayList<Ticket>();
			}

			int nbSimilarTicketsToAdd = Integer.parseInt(nbSimilarTickets);
			Ticket ticketCmp = datamanager.findTicket(UUID.fromString(eventId), UUID.fromString(ticketId));
			List<Ticket> listTickets = datamanager.findSimilarTickets(datamanager.findAllTickets(UUID.fromString(eventId)), ticketCmp.getType(), ticketCmp.getSection());
			listTickets = datamanager.filterListWithList(listTickets, listOfBasket);
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
	public void copyToBasket(String eventId, String ticketId, int amount, ProxyModel model, ProxyHttpSession session) throws UnauthenticatedException {

		if (!DAAuthentication.isLogged(session))
			throw new UnauthenticatedException();

		model.addAttribute("message", "");
		Ticket source = datamanager.findTicket(UUID.fromString(eventId), UUID.fromString(ticketId));
		// Liste des Ticket Displayed
		List<ArrayList<Ticket>> ticketDisplay = (List<ArrayList<Ticket>>) session.getAttribute("basketDisplay");
		// Les ticket dans le basket
		int i = 0;
		Ticket current = ticketDisplay.get(i).get(0);
		// Devrait tt le temps etre en position 0 sinon remettre ligne en haut
		while (!(current.getId().equals(source.getId()))) {
			++i;
			current = ticketDisplay.get(i).get(0);
		}
	
		if (amount > ticketDisplay.get(i).size()) {	// si on ajoute un billet
			ArrayList<Ticket> basket = (ArrayList<Ticket>) session.getAttribute("basket");
			List<Ticket> eventTicket = datamanager.findAllTickets(source.getEvent().getId());
			List<Ticket> similarTicket = datamanager.findSimilarTickets(eventTicket, source.getType(), source.getSection());
			List<Ticket> freeTicket = datamanager.filterListWithList(similarTicket, ticketDisplay.get(i));
			if (freeTicket.size() < amount - ticketDisplay.get(i).size()) {
				model.addAttribute("message", "Erreur : Il ne reste que " + freeTicket.size() + " billets de cette catégorie");
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
	public void purchase(ProxyHttpSession session, ProxyModel model, BindingResult result) throws UnauthenticatedException, InvalidFormExceptions, InvalidPurchaseException {
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
