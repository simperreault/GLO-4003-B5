package ca.ulaval.ticketmaster.web.DomaineAffaire;

import javax.servlet.http.HttpSession;

public class DAAuthentication {

	private DAAuthentication() {
	}
	public static boolean isAdmin(HttpSession session) {
		if (session.getAttribute("sesacceslevel") == "Admin")
			return true;
		return false;
	}
	public static boolean isLogged(HttpSession session){
		if (session.getAttribute("sesacceslevel") != null)
		{
			return true;
		}
		return false;
	}
}
