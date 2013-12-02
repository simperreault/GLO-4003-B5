package ca.ulaval.ticketmaster.users;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import ca.ulaval.ticketmaster.dao.util.DataManager;
import ca.ulaval.ticketmaster.events.tickets.model.TicketType;
import ca.ulaval.ticketmaster.home.DAAuthentication;
import ca.ulaval.ticketmaster.home.Page;
import ca.ulaval.ticketmaster.springproxy.ProxyHttpSession;
import ca.ulaval.ticketmaster.springproxy.ProxyModel;
import ca.ulaval.ticketmaster.users.model.User;
import ca.ulaval.ticketmaster.users.model.UserViewModel;

public class BLUser {
	private DataManager datamanager;

	public BLUser() {
		datamanager = new DataManager();
	}
	public BLUser(DataManager dm){
		datamanager = dm;
	}
	public String createUser(User user, UserViewModel viewmodel, ProxyModel model, BindingResult result,
			ProxyHttpSession session) {

		if (result.hasErrors()) {

			model.addAttribute("user", viewmodel);
			model.addAttribute("typeList", TicketType.values());
			model.addAttribute("error", result.getAllErrors());
			// return "CreateUser";
			return Page.CreateUser.toString();
		}

		if (datamanager.saveUser(user)) {

			model.addAttribute("message", "Utilisateur ajoute");
			session.setAttribute("sesacceslevel", user.getAccessLevel().toString());
			session.setAttribute("sesusername", user.getUsername());
			return Page.Home.toString();
		} else {
			// Complexe : soit on passe d'une certaine facon le XMLReader au
			// Validator,
			// soit on fait la validation ici

			// model.addAttribute("message", "Utilisateur deja present");
			// model.addAttribute("error", "Utilisateur deja present");
			result.addError(new ObjectError("user", "Utilisateur deja existant"));

			model.addAttribute("error", result.getAllErrors());

			model.addAttribute("user", viewmodel);
			model.addAttribute("typeList", TicketType.values());
			model.addAttribute("errorMsg", "Une erreur c'est produite lors de la création du compte");
			// return "CreateUser";
			return Page.CreateUser.toString();
		}

	}

	public void connect(String username, String pWord, ProxyModel model, ProxyHttpSession session) {
		if (!DAAuthentication.isLogged(session)) {
			boolean userIsOk = false;
			User user = datamanager.findUser(username);
			if (user != null) {
				if (user.getPassword().equals(pWord)) {
					userIsOk = true;
					session.setAttribute("sesacceslevel", user.getAccessLevel().toString());
					session.setAttribute("sesusername", username);
				}
			}

			if (!userIsOk) {
				model.addAttribute("loginError", "La combinaison pseudo/mot de passe est invalide");
			}
		}
	}

	public void disconnect(ProxyHttpSession session) {
		session.invalidate();
	}
}
