package ca.ulaval.ticketmaster.web.DomaineAffaire;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import ca.ulaval.ticketmaster.dao.util.DataManager;
import ca.ulaval.ticketmaster.model.User;
import ca.ulaval.ticketmaster.model.enums.TicketType;
import ca.ulaval.ticketmaster.web.DomaineAffaire.proxy.ProxyHttpSession;
import ca.ulaval.ticketmaster.web.DomaineAffaire.proxy.ProxyModel;
import ca.ulaval.ticketmaster.web.viewmodels.UserViewModel;

public class DAUser {
    private DataManager datamanager;

    public DAUser() {
	datamanager = new DataManager();
    }

    public String createUser(User user, UserViewModel viewmodel, ProxyModel model, BindingResult result,
	    ProxyHttpSession session) {

	if (result.hasErrors()) {

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
	    // Complexe : soit on passe d'une certaine facon le XMLReader au
	    // Validator,
	    // soit on fait la validation ici

	    // model.addAttribute("message", "Utilisateur deja present");
	    // model.addAttribute("error", "Utilisateur deja present");
	    result.addError(new ObjectError("user", "Utilisateur deja existant"));

	    model.addAttribute("error", result.getAllErrors());

	    model.addAttribute("user", viewmodel);
	    model.addAttribute("typeList", TicketType.values());
	    model.addAttribute("errorMsg", "Une �rreur c'est produite lors de la cr�ation du compte");
	    return "CreateUser";
	}

    }

    public String connect(String username, String pWord, ProxyModel model, ProxyHttpSession session) {
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

		model.addAttribute("errorMsg", "La combinaison pseudo/mot de passe est invalide");
		// model.addAttribute("currentPage", "Home.jsp");
	    }
	}
	// TODO Changer pour current page
	return Page.Home.toString();

    }

    public String disconnect(ProxyHttpSession session) {
	session.invalidate();
	// TODO changer pour current page
	return Page.Home.toString();
    }
}