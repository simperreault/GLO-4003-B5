package ca.ulaval.ticketmaster.web;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import ca.ulaval.ticketmaster.dao.util.DataManager;
import ca.ulaval.ticketmaster.model.Event;
import ca.ulaval.ticketmaster.model.Ticket;
import ca.ulaval.ticketmaster.model.User;
import ca.ulaval.ticketmaster.model.enums.SportType;
import ca.ulaval.ticketmaster.model.enums.TicketType;
import ca.ulaval.ticketmaster.web.converter.EventConverter;
import ca.ulaval.ticketmaster.web.converter.TicketConverter;
import ca.ulaval.ticketmaster.web.viewmodels.EventViewModel;
import ca.ulaval.ticketmaster.web.viewmodels.TicketViewModel;
import ca.ulaval.ticketmaster.web.viewmodels.UserViewModel;

public class GlobalController {
	public enum Page {
		//TODO coder une page erreur 403
		Home("Home"),Error403("Home"), CUser("CreateUser"), EventList("EventList"), TicketList("TicketList"), Detail("detail"),EventAdd("EventAdd"),TicketAdd("TicketAdd");
		private final String name;

		Page(String str) {
			name = str;
		}

		@Override
		public String toString() {
			return name;
		}
	}

	private DataManager datamanager;
	
	public GlobalController() {
		datamanager = new DataManager();
		// this.session = session;

	}
	private String setCurrent(String page,HttpSession session)
	{
		session.setAttribute("currentPage",page);
		return page;
		
	}
	public boolean isAdmin(HttpSession session) {
		if (session.getAttribute("sesacceslevel") == "Admin")
			return true;
		return false;
	}
	public boolean isLogged(HttpSession session){
		if (session.getAttribute("sesacceslevel") != null)
		{
			return true;
		}
		return false;
	}

	public String createUser(User user,UserViewModel viewmodel, Model model,BindingResult result, HttpSession session) {
		
	  	if (result.hasErrors()) {
    		System.out.println("CreateUser:POST:ERRORS");
    		model.addAttribute("user", viewmodel);
    		model.addAttribute("typeList", TicketType.values());
    	    model.addAttribute("error", result.getAllErrors());
    	    return "CreateUser";
    	}
		if (datamanager.saveUser(user)) {
			model.addAttribute("message", "Utilisateur ajoute");
			session.setAttribute("sesacceslevel", user.getAccessLevel().toString());
			session.setAttribute("sesusername", user.getUsername());
			return Page.Home.toString();
		} else {
			//Complexe : soit on passe d'une certaine facon le XMLReader au Validator,
			// soit on fait la validation ici
			
			//model.addAttribute("message", "Utilisateur deja present");
    		//model.addAttribute("error", "Utilisateur deja present");
			result.addError(new ObjectError("user", "Utilisateur deja existant"));
			
    	    model.addAttribute("error", result.getAllErrors());
    	    
    		model.addAttribute("user", viewmodel);
    		model.addAttribute("typeList", TicketType.values());
    		model.addAttribute("errorMsg", "Une érreur c'est produite lors de la création du compte");
			return Page.Home.toString();
		}

	}

	public String connect(String username, String pWord, Model model, HttpSession session) {
		int i = 0;

		boolean userIsOk = false;
		User user = datamanager.findUser(username);
		i = 1;
		if (user != null) {
			if (user.getPassword().equals(pWord)) {
				userIsOk = true;
				session.setAttribute("sesacceslevel", user.getAccessLevel().toString());
				session.setAttribute("sesusername", username);
			}
		}
		i = 2;

		if (!userIsOk) {

			model.addAttribute("errorMsg", "La combinaison pseudo/mot de passe est invalide");
			// model.addAttribute("currentPage", "Home.jsp");
		}
		// TODO Changer pour current page
		return Page.Home.toString();

	}

	public String disconnect(HttpSession session) {
		session.invalidate();
	//	session.setAttribute("sesacceslevel", null);
		//session.setAttribute("sesusername", null);
		// TODO changer pour current page
		return Page.Home.toString();
	}

	public String list(Model model) {
		// System.out.println("/list elem count : " +
		// datamanager.findAllEvents().size());
		model.addAttribute("EventList", datamanager.findAllEvents());
		return Page.EventList.toString();
	}

	public String getTickedEvent(String idEvent, Model model) {
		model.addAttribute("eventID", UUID.fromString(idEvent));
		model.addAttribute("ticketList", datamanager.findAllTickets(UUID.fromString(idEvent)));
		return Page.TicketList.toString();
	}

	public String getTickedEvent(String idEvent1, String idEvent2, Model model) {
		model.addAttribute("ticket", datamanager.findTicket(UUID.fromString(idEvent1), UUID.fromString(idEvent2)));
		return Page.Detail.toString();
	}

	public String getAddEvent(Model model, HttpSession session)
	{
		if (isAdmin(session))
		{
		model.addAttribute("event", new EventViewModel());
		model.addAttribute("sportList", SportType.values());
		return Page.EventAdd.toString();
		}else{
			
			return Page.Error403.toString();
		}
	}
	
	public String addEvent(EventViewModel viewmodel,BindingResult result,Model model, HttpSession session)
	{
		if (isAdmin(session))
		{
			if (result.hasErrors()) {
				model.addAttribute("sportList", SportType.values());
				model.addAttribute("error", result.getAllErrors());
				model.addAttribute("event", viewmodel);
				return Page.EventAdd.toString();
			}
			
			try {
				Event event = EventConverter.convert(viewmodel, datamanager);
				datamanager.saveEvent(event);
			} catch (ParseException e) {
				// Error happen, This event will not be saved
				model.addAttribute("sportList", SportType.values());
				model.addAttribute("error", "Erreur dans le format de la date (dd/mm/yyyy HH:MM)");
				model.addAttribute("event", viewmodel);
				return Page.EventAdd.toString();
			}
			return "redirect:/event/list";
			
		}else{
			return Page.Error403.toString();
		}
	}
	public String deleteEvent(String eventId){
		datamanager.deleteEvent(UUID.fromString(eventId));
		return "redirect:/event/list";
	}
	
	public String getAddTicket(String eventId, Model model, HttpSession session)
	{
		if (isAdmin(session))
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
		if (isAdmin(session))
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
	@SuppressWarnings("unchecked")// a cause du cast d'un objet vers arraylist<Ticket> je n'ai pas trouvé de solution pour enlever le warning
	public String addToBasket(String eventId,String ticketId,Model model, HttpSession session){
		if (isLogged(session))
		{
			ArrayList<Ticket> list;
			if (session.getAttribute("basket") != null)
			{
				list = (ArrayList<Ticket>)session.getAttribute("basket");
				model.addAttribute("msg", "old array");
			}else //le panier est vide
			{
				 list=  new ArrayList<Ticket>();
					model.addAttribute("msg", "New array");
			}
			list.add(datamanager.findTicket(UUID.fromString(eventId), UUID.fromString(ticketId)));
			session.setAttribute("basket", list);
		}else{
			model.addAttribute("msg", "Veuillez vous connecter pour acheter des billets");
			//TODO coder un message qui demande de se logger
		}
		return "redirect:/event/" + eventId;
	}
}
