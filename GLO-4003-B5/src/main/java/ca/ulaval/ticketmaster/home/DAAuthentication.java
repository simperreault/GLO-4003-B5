package ca.ulaval.ticketmaster.home;

import ca.ulaval.ticketmaster.springproxy.ProxyHttpSession;
import ca.ulaval.ticketmaster.users.model.User.AccessLevel;

public class DAAuthentication {
    public static boolean isAdmin(ProxyHttpSession session) {
	if (session.getAttribute("sesacceslevel") == AccessLevel.Admin)
	    return true;
	return false;
    }

    public static boolean isLogged(ProxyHttpSession session) {
	if (session.getAttribute("sesacceslevel") != null) {
	    return true;
	}
	return false;
    }
}
