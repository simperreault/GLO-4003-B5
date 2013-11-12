package ca.ulaval.ticketmaster.web.DomaineAffaire;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.validation.BindingResult;

import ca.ulaval.ticketmaster.dao.util.DataManager;
import ca.ulaval.ticketmaster.model.Ticket;
import ca.ulaval.ticketmaster.model.enums.PaymentType;
import ca.ulaval.ticketmaster.model.enums.SportType;
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
			//	datamanager.countSimilarTickets(_ticketList, _type, _section)
				list = new ArrayList<Ticket>();
				model.addAttribute("msg", "New array");
			}
			
			list.add(datamanager.findTicket(UUID.fromString(eventId), UUID.fromString(ticketId)));
			List<ArrayList<Ticket>> tmp =datamanager.regroupSimilarTicketsByEvents(list);
			session.setAttribute("basketD",datamanager.regroupSimilarTicketsByEvents(list));		
			session.setAttribute("basket", list);
			ArrayList<Ticket> x = tmp.get(0);
			//ArrayList<Ticket> test = tmp.get(1).get(0);
		} else {
			model.addAttribute("msg", "Veuillez vous connecter pour acheter des billets");
			// TODO coder un message qui demande de se logger
		}
		return "redirect:/event/" + eventId;

	}
	
	@SuppressWarnings("unchecked")
	public String addMultipleTicketsToBasket(String eventId, String ticketId, String nbSimilarTickets,
			ProxyModel model, ProxyHttpSession session) {
		
		int totalTicketsNbAdded = 0; //possiblement utilisé plus tard
		
		if (DAAuthentication.isLogged(session)) {
			
			ArrayList<Ticket> listOfBasket;
			if (session.getAttribute("basket") != null) {
				listOfBasket = (ArrayList<Ticket>) session.getAttribute("basket");
				model.addAttribute("msg", "old array");
			} else // le panier est vide
			{
			//	datamanager.countSimilarTickets(_ticketList, _type, _section)
				listOfBasket = new ArrayList<Ticket>();
				model.addAttribute("msg", "New array");
			}
			
			int nbSimilarTicketsToAdd = Integer.parseInt(nbSimilarTickets);
			
			//0- The initial ticket (it may be taken due to race condition (i.e. bought) I think @addToBasket
			// so we have to make sure it isn't taken
			Ticket ticketCmp = datamanager.findTicket(UUID.fromString(eventId), UUID.fromString(ticketId));
			
			//1- Find similar tickets to the one @ UUID.fromString(ticketId)
			List<Ticket> listTickets = datamanager.findSimilarTickets(
					datamanager.findAllTickets(UUID.fromString(eventId)), //should filter sold
					ticketCmp.getType(), 
					ticketCmp.getSection());
			
			//2- Filter tickets already in basket
			listTickets = datamanager.filterListWithList(listTickets, listOfBasket);
			
			//3- Add them
			for ( int i = 0; i < listTickets.size() && i < nbSimilarTicketsToAdd ; ++i )
			{
				++totalTicketsNbAdded;
				System.out.println("Adding ticket : " + listTickets.get(i).getId());
				listOfBasket.add(listTickets.get(i));
			}

			List<ArrayList<Ticket>> tmp = datamanager.regroupSimilarTicketsByEvents(listOfBasket);
			session.setAttribute("basketD", datamanager.regroupSimilarTicketsByEvents(listOfBasket));		
			session.setAttribute("basket", listOfBasket);
			
			//ArrayList<Ticket> x = tmp.get(0); //wtf ?
			//ArrayList<Ticket> test = tmp.get(1).get(0);
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
					session.setAttribute("basketD",datamanager.regroupSimilarTicketsByEvents(list));	
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

	@SuppressWarnings("unchecked")
	public String purchase(ProxyHttpSession session, ProxyModel model, BindingResult result) {
		if (DAAuthentication.isLogged(session)) {
			if (result.hasErrors()) {
				model.addAttribute("error", result.getAllErrors());
				return "Purchase";
			}
			
			ArrayList<Ticket> list;
			ArrayList<Ticket> invalidTickets;

			if (session.getAttribute("basket") != null) {
				System.out.println("basket");
				list = (ArrayList<Ticket>) session.getAttribute("basket");
				invalidTickets = datamanager.buyTickets(list, (String) session.getAttribute("sesusername"));

				session.setAttribute("basket", list);

				if (invalidTickets == null) {
					model.addAttribute("message", "Billets valide");
					return "redirect:/Confirmation";
				} else {
					model.addAttribute("message", "Erreur : Billets invalides");
					return "redirect:/Confirmation";
				}

			} else {
				model.addAttribute("message", "Erreur : Le panier est vide");
			}

		} else {
			System.out.println("pas loggé");
		}
		return "redirect:/Purchase";

	}

}
