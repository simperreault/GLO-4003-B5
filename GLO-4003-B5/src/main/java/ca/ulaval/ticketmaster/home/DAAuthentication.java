package ca.ulaval.ticketmaster.home;

import ca.ulaval.ticketmaster.springproxy.ProxyHttpSession;

public class DAAuthentication {

    private DAAuthentication() {
    }

    public static boolean isAdmin(ProxyHttpSession session) {
	if (session.getAttribute("sesacceslevel") == "Admin")
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
