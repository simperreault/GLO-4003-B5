package ca.ulaval.ticketmaster.users;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import ca.ulaval.ticketmaster.dao.util.DataManager;
import ca.ulaval.ticketmaster.events.tickets.model.TicketType;
import ca.ulaval.ticketmaster.exceptions.InvalidFormExceptions;
import ca.ulaval.ticketmaster.exceptions.UnauthorizedException;
import ca.ulaval.ticketmaster.home.DAAuthentication;
import ca.ulaval.ticketmaster.springproxy.ProxyHttpSession;
import ca.ulaval.ticketmaster.springproxy.ProxyModel;
import ca.ulaval.ticketmaster.users.model.User;
import ca.ulaval.ticketmaster.users.model.User.AccessLevel;
import ca.ulaval.ticketmaster.users.model.UserConverter;
import ca.ulaval.ticketmaster.users.model.UserViewModel;

public class BLUser {
    private DataManager datamanager;

    public BLUser() {
	datamanager = new DataManager();
    }

    public BLUser(DataManager dm) {
	datamanager = dm;
    }

    public void createUser(User user, UserViewModel viewmodel, ProxyModel model, BindingResult result,
	    ProxyHttpSession session) throws InvalidFormExceptions {

	if (result.hasErrors()) {
	    model.addAttribute("user", viewmodel);
	    model.addAttribute("typeList", TicketType.values());
	    model.addAttribute("error", result.getAllErrors());
	    throw new InvalidFormExceptions();
	}

	if (datamanager.saveUser(user)) {
	    model.addAttribute("message", "Utilisateur ajoute");
	    session.setAttribute("sesacceslevel", user.getAccessLevel().toString());
	    session.setAttribute("sesusername", user.getUsername());
	} else {
	    result.addError(new ObjectError("user", "Utilisateur deja existant"));

	    model.addAttribute("error", result.getAllErrors());

	    model.addAttribute("user", viewmodel);
	    model.addAttribute("typeList", TicketType.values());
	    model.addAttribute("errorMsg", "Une erreur c'est produite lors de la création du compte");
	    throw new InvalidFormExceptions();
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

    public void setUserInfo(ProxyModel model, ProxyHttpSession session) throws UnauthorizedException {
	if (!DAAuthentication.isLogged(session))
	    throw new UnauthorizedException();

	User user = datamanager.findUser((String) session.getAttribute("sesusername"));
	model.addAttribute("user", UserConverter.convert(user));
    }

    public void editUser(UserViewModel viewUser, BindingResult result, ProxyModel model,
	    ProxyHttpSession session) throws InvalidFormExceptions {
	if (result.hasErrors()) {
	    model.addAttribute("user", viewUser);
	    model.addAttribute("typeList", TicketType.values());
	    model.addAttribute("error", result.getAllErrors());
	    throw new InvalidFormExceptions();
	}
	
	User user = UserConverter.convert(viewUser);
	if (session.getAttribute("sesacceslevel").equals(AccessLevel.Admin.toString()))
	    user.setAccessLevel(AccessLevel.Admin);

	datamanager.editUser(user);
    }
}
