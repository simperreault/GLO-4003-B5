package ca.ulaval.ticketmaster.home;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpSession;

import ca.ulaval.ticketmaster.springproxy.ProxyHttpSession;

@RunWith(MockitoJUnitRunner.class)
public class TestDAAuthentication {

	private ProxyHttpSession session;

	@Before
	public void setUp() {
		session = ProxyHttpSession.create(new MockHttpSession());
	}

	@Test
	public void isAdmin() {
		assertEquals(false, DAAuthentication.isAdmin(session));
		session.setAttribute("sesacceslevel", "Admin");
		assertEquals(true, DAAuthentication.isAdmin(session));
	}

	@Test
	public void isLogged() {
		assertEquals(false, DAAuthentication.isAdmin(session));
		session.setAttribute("sesacceslevel", "Admin");
		assertEquals(true, DAAuthentication.isAdmin(session));
	}
}
