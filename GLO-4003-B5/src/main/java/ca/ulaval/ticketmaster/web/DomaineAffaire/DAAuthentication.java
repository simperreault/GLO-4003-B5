package ca.ulaval.ticketmaster.web.DomaineAffaire;

import ca.ulaval.ticketmaster.web.DomaineAffaire.proxy.ProxyHttpSession;

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
