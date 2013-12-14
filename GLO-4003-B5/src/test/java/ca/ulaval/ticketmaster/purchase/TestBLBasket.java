package ca.ulaval.ticketmaster.purchase;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
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
import org.springframework.validation.BindingResult;

import ca.ulaval.ticketmaster.dao.util.DataManager;
import ca.ulaval.ticketmaster.dao.util.SearchEngine;
import ca.ulaval.ticketmaster.dao.util.TicketFactory;
import ca.ulaval.ticketmaster.events.model.Event;
import ca.ulaval.ticketmaster.events.model.SportType;
import ca.ulaval.ticketmaster.events.tickets.model.Ticket;
import ca.ulaval.ticketmaster.events.tickets.model.TicketType;
import ca.ulaval.ticketmaster.exceptions.InvalidFormExceptions;
import ca.ulaval.ticketmaster.exceptions.InvalidPurchaseException;
import ca.ulaval.ticketmaster.exceptions.UnauthenticatedException;
import ca.ulaval.ticketmaster.springproxy.ProxyHttpSession;
import ca.ulaval.ticketmaster.springproxy.ProxyModel;
import ca.ulaval.ticketmaster.users.model.User.AccessLevel;

@RunWith(MockitoJUnitRunner.class)
@SuppressWarnings("unchecked")
public class TestBLBasket {
    private ArrayList<Ticket> list;
    private ProxyHttpSession session;
    private BLBasket basket;

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
	basket = new BLBasket(dataManager, searchEngine);
	session = ProxyHttpSession.create(new MockHttpSession());
	model = ProxyModel.create(Mockito.mock(Model.class));
	list = new ArrayList<Ticket>();
    }

    @Test
    public void testEnumAccessLevel() {

	org.junit.Assert.assertThat(AccessLevel.valueOf("User"),
		org.hamcrest.CoreMatchers.is(org.hamcrest.CoreMatchers.notNullValue()));

	org.junit.Assert.assertThat(AccessLevel.valueOf("Admin"),
		org.hamcrest.CoreMatchers.is(org.hamcrest.CoreMatchers.notNullValue()));
    }

    private void setAdmin(ProxyHttpSession session) {
	session.setAttribute("sesacceslevel", "Admin");
    }

    private Ticket makeTicket(Event event) {
	return TicketFactory.CreateExistingTicket(UUID.randomUUID(), event, TicketType.GENERAL, "", "", "",
		2.0, 2.0);
    }

    private ArrayList<Ticket> makeMultipleTicket(Event event, int n) {
	ArrayList<Ticket> tList = new ArrayList<Ticket>();
	for (int i = 0; i < n; ++i) {

	    tList.add(makeTicket(event));
	}
	return tList;
    }

    private Event makeEvent() throws ParseException {
	Date date = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH).parse("30/09/2013");
	Date time = new SimpleDateFormat("H:mm").parse("13:30");
	return new Event(UUID.randomUUID(), true, SportType.FOOTBALL, "M", "Rouge et or", "Vert et or",
		"Québec", "Bell", date, time, 0, 0);
    }

    @Test
    public void testAddToBasket() throws ParseException {

	Event event = makeEvent();
	Ticket ticket = makeTicket(event);
	UUID eventId = event.getId();
	UUID ticketId = ticket.getId();

	// Si on est pas connecter
	basket.addToBasket(eventId.toString(), ticketId.toString(), session);
	assertNull(session.getAttribute("basket"));

	// Si le panier est vide et on ajoute 1 nouveau billet
	when(dataManager.findTicket(eventId, ticketId)).thenReturn(ticket);
	setAdmin(session);
	basket.addToBasket(eventId.toString(), ticketId.toString(), session);
	ArrayList<Ticket> nList = (ArrayList<Ticket>) session.getAttribute("basket");
	assertEquals(ticket.getId(), nList.get(0).getId());

	// Si le panier contient deja 1 ticket

	UUID ticketId2 = UUID.randomUUID();
	Ticket ticket2 = TicketFactory.CreateExistingTicket(ticketId2, event, TicketType.GENERAL, "", "", "",
		2.0, 2.0);
	when(dataManager.findTicket(eventId, ticketId2)).thenReturn(ticket2);
	basket.addToBasket(eventId.toString(), ticketId2.toString(), session);
	nList = (ArrayList<Ticket>) session.getAttribute("basket");
	assertEquals(ticket.getId(), nList.get(0).getId());
	assertEquals(ticket2.getId(), nList.get(1).getId());
    }

    @Test(expected = UnauthenticatedException.class)
    public void testAddMultipleTicketToBaskUnauthenticatedException() throws ParseException,
	    UnauthenticatedException {
	Event event = makeEvent();
	list = makeMultipleTicket(event, 3);
	UUID eventId = list.get(0).getId();
	when(dataManager.findTicket(eventId, list.get(0).getId())).thenReturn(list.get(0));
	when(searchEngine.filterListWithList(new ArrayList<Ticket>(), new ArrayList<Ticket>())).thenReturn(
		list);
	basket.addMultipleTicketsToBasket(eventId.toString(), list.get(0).getId().toString(), "3", model,
		session);
    }

    @Test
    public void testAddMultipleTicketToBask() throws ParseException, UnauthenticatedException {

	Event event = makeEvent();
	list = makeMultipleTicket(event, 3);

	UUID eventId = list.get(0).getId();
	setAdmin(session);
	when(dataManager.findTicket(eventId, list.get(0).getId())).thenReturn(list.get(0));
	when(searchEngine.filterListWithList(new ArrayList<Ticket>(), new ArrayList<Ticket>())).thenReturn(
		list);

	basket.addMultipleTicketsToBasket(eventId.toString(), list.get(0).getId().toString(), "3", model,
		session);
	ArrayList<Ticket> nList = (ArrayList<Ticket>) session.getAttribute("basket");
	assertEquals(list.get(0).getId(), nList.get(0).getId());
	assertEquals(list.get(1).getId(), nList.get(1).getId());
	assertEquals(list.get(2).getId(), nList.get(2).getId());

	when(searchEngine.filterListWithList(new ArrayList<Ticket>(), nList)).thenReturn(list);
	basket.addMultipleTicketsToBasket(eventId.toString(), list.get(0).getId().toString(), "3", model,
		session);
	nList = (ArrayList<Ticket>) session.getAttribute("basket");
	assertEquals(list.get(0).getId(), nList.get(3).getId());
	assertEquals(list.get(1).getId(), nList.get(4).getId());
	assertEquals(list.get(2).getId(), nList.get(5).getId());

    }

    @Test
    public void testRemoveFromBasket() throws ParseException {

	Event event = makeEvent();
	Ticket ticket = makeTicket(event);
	basket.removeFromBasket(event.getId().toString(), ticket.getId().toString(), session);
	assertNull(session.getAttribute("basket"));
	for (int i = 0; i < 3; ++i) {
	    list.add(makeTicket(event));
	}
	session.setAttribute("basket", list);
	basket.removeFromBasket(event.getId().toString(), ticket.getId().toString(), session);
	assertEquals(3, ((ArrayList<Ticket>) session.getAttribute("basket")).size());
	basket.removeFromBasket(event.getId().toString(), list.get(0).getId().toString(), session);
	assertEquals(2, ((ArrayList<Ticket>) session.getAttribute("basket")).size());
    }

    @Test(expected = UnauthenticatedException.class)
    public void testRemoveAllFrombasketUnauthenticatedException() throws UnauthenticatedException,
	    ParseException {
	basket.removeAllFromBasket(session);
    }

    @Test
    public void testRemoveAllFromBasket() throws UnauthenticatedException, ParseException {
	this.setAdmin(session);
	list.add(makeTicket(makeEvent()));
	session.setAttribute("basket", list);
	basket.removeAllFromBasket(session);
	assertEquals(0, ((ArrayList<Ticket>) session.getAttribute("basket")).size());
    }

    @Test
    public void testCopyToBasketAdd() throws UnauthenticatedException, ParseException {

	this.setAdmin(session);
	Event event = makeEvent();
	ArrayList<Ticket> temp = new ArrayList<Ticket>();
	ArrayList<Ticket> basket2 = new ArrayList<Ticket>();
	list = makeMultipleTicket(event, 8);

	ArrayList<ArrayList<Ticket>> displayList = new ArrayList<ArrayList<Ticket>>();

	basket2.add(makeTicket(makeEvent()));
	temp.add(basket2.get(0));
	displayList.add(temp);
	ArrayList<Ticket> temp2 = new ArrayList<Ticket>();

	Ticket ticket = makeTicket(event);
	temp2.add(ticket);
	basket2.add(ticket);
	displayList.add(temp2);
	session.setAttribute("basket", basket2);

	session.setAttribute("basketDisplay", displayList);
	temp = new ArrayList<Ticket>();
	when(dataManager.findTicket(event.getId(), ticket.getId())).thenReturn(ticket);
	when(searchEngine.filterListWithList(temp, displayList.get(1))).thenReturn(list);
	basket.copyToBasket(event.getId().toString(), ticket.getId().toString(), 3, model, session);
	assertEquals(4, ((ArrayList<Ticket>) session.getAttribute("basket")).size());
    }

    @Test
    public void testCopyToBasketRemove() throws UnauthenticatedException, ParseException {
	setAdmin(session);
	Event event = makeEvent();
	list = makeMultipleTicket(event, 8);
	ArrayList<ArrayList<Ticket>> displayList = new ArrayList<ArrayList<Ticket>>();
	displayList.add(list);
	session.setAttribute("basket", list);
	session.setAttribute("basketDisplay", displayList);
	when(dataManager.findTicket(event.getId(), list.get(0).getId())).thenReturn(list.get(0));
	basket.copyToBasket(event.getId().toString(), list.get(0).getId().toString(), 3, model, session);
	assertEquals(3, ((ArrayList<Ticket>) session.getAttribute("basket")).size());
    }

    @Test(expected = UnauthenticatedException.class)
    public void testCopyToBasketUnauthenticatedException() throws UnauthenticatedException {
	basket.copyToBasket("", "", 2, model, session);
    }

    @Test(expected = UnauthenticatedException.class)
    public void testPurchaseUnauthenticatedException() throws UnauthenticatedException,
	    InvalidFormExceptions, InvalidPurchaseException {

	BindingResult t = Mockito.mock(BindingResult.class);
	basket.purchase(session, model, t);
    }

    @Test(expected = InvalidFormExceptions.class)
    public void testPurchaseInvalidFormExceptions() throws UnauthenticatedException, InvalidFormExceptions,
	    InvalidPurchaseException {
	this.setAdmin(session);
	BindingResult t = Mockito.mock(BindingResult.class);
	when(t.hasErrors()).thenReturn(true);
	basket.purchase(session, model, t);
    }

    @Test(expected = InvalidPurchaseException.class)
    public void testPurchaseInvalidPurchaseException() throws ParseException, UnauthenticatedException,
	    InvalidFormExceptions, InvalidPurchaseException {
	this.setAdmin(session);
	BindingResult result = Mockito.mock(BindingResult.class);
	when(result.hasErrors()).thenReturn(false);
	list.add(makeTicket(makeEvent()));
	when(dataManager.buyTickets(list, null)).thenReturn(list);
	session.setAttribute("singleTicket", list);
	basket.purchase(session, model, result);

    }

    @Test
    public void testPurchase() throws ParseException, UnauthenticatedException, InvalidFormExceptions,
	    InvalidPurchaseException {
	this.setAdmin(session);
	BindingResult result = Mockito.mock(BindingResult.class);
	when(result.hasErrors()).thenReturn(false);
	list = makeMultipleTicket(makeEvent(), 3);
	when(dataManager.buyTickets(list, null)).thenReturn(null);
	session.setAttribute("basket", list);
	basket.purchase(session, model, result);
	Mockito.verify(dataManager, Mockito.atLeastOnce()).buyTickets(list, null);
    }

    @Test(expected = UnauthenticatedException.class)
    public void testBuySingleTicketUnauthenticatedException() throws UnauthenticatedException {
	basket.buySingleTicket("", "", session);
    }

    @Test
    public void testBuySingleTicket() throws UnauthenticatedException, ParseException {
	setAdmin(session);
	Event event = makeEvent();
	Ticket ticket = makeTicket(event);
	basket.buySingleTicket(event.getId().toString(), ticket.getId().toString(), session);
	assertEquals(1, ((ArrayList<Ticket>) session.getAttribute("singleTicket")).size());
    }

}
