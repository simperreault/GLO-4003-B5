package ca.ulaval.ticketmaster.events;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.ui.Model;

import ca.ulaval.ticketmaster.dao.util.DataManager;
import ca.ulaval.ticketmaster.dao.util.SearchEngine;
import ca.ulaval.ticketmaster.dao.util.TicketFactory;
import ca.ulaval.ticketmaster.events.model.Event;
import ca.ulaval.ticketmaster.events.model.SportType;
import ca.ulaval.ticketmaster.events.tickets.model.Ticket;
import ca.ulaval.ticketmaster.events.tickets.model.TicketType;
import ca.ulaval.ticketmaster.purchase.BLBasket;
import ca.ulaval.ticketmaster.springproxy.ProxyHttpSession;
import ca.ulaval.ticketmaster.springproxy.ProxyModel;
@RunWith(MockitoJUnitRunner.class)

public class BLEventTest {
	
	@Mock
	private DataManager dataManager;
	private SearchEngine searchEngine;

	@Mock
	ProxyModel model;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		dataManager = Mockito.mock(DataManager.class);
		searchEngine = Mockito.mock(SearchEngine.class);
		//session = ProxyHttpSession.create(new MockHttpSession());
		model = ProxyModel.create(Mockito.mock(Model.class));
		//list = new ArrayList<Ticket>();

	}
	
	
	private Event makeEvent() throws ParseException {
		Date date = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH).parse("30/09/2013");
		Date time = new SimpleDateFormat("H:mm").parse("13:30");
		return new Event(UUID.randomUUID(), true, SportType.FOOTBALL, "M", "Rouge et or", "Vert et or", "Québec", "Bell", date, time, 0, 0);
	}
	
	@Test
	public void testAddEvent() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testPreLoadEvents() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testSearch() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testDeleteEvent() throws ParseException {
		Event event = makeEvent();
		when(dataManager.deleteEvent(event)).thenReturn(true);

	}

}
