package ca.ulaval.ticketmaster.springproxy;

import javax.servlet.http.HttpSession;

public class ProxyHttpSession {

    private HttpSession session;

    private ProxyHttpSession(HttpSession session) {
	this.session = session;
    }

    static public ProxyHttpSession create(HttpSession session) {
	return new ProxyHttpSession(session);
    }

    public Object getAttribute(String string) {
	return session.getAttribute(string);
    }

    public void setAttribute(String string, Object obj) {
	this.session.setAttribute(string, obj);
    }

    public void invalidate() {
	this.session.invalidate();
    }
}
