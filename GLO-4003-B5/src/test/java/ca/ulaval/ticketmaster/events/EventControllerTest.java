package ca.ulaval.ticketmaster.events;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.support.BindingAwareModelMap;

import ca.ulaval.ticketmaster.dao.util.DataManager;
import ca.ulaval.ticketmaster.dao.util.SearchEngine;
import ca.ulaval.ticketmaster.events.EventController;
import ca.ulaval.ticketmaster.events.model.Event;
import ca.ulaval.ticketmaster.events.model.EventViewModel;
import ca.ulaval.ticketmaster.events.model.SportType;
import ca.ulaval.ticketmaster.exceptions.UnauthorizedException;
import ca.ulaval.ticketmaster.home.Page;
import ca.ulaval.ticketmaster.purchase.BLBasket;
import ca.ulaval.ticketmaster.springproxy.ProxyHttpSession;
import ca.ulaval.ticketmaster.springproxy.ProxyModel;

@RunWith(MockitoJUnitRunner.class)
public class EventControllerTest {

	public MockHttpSession session;
	public EventController controller;
	 @Mock
	BLEvent event;
	


	@Before
	public void setUp() {
		event = mock(BLEvent.class);
		controller = new EventController(event);
		session = new MockHttpSession();
	}

	public void TestEventController(){
		controller = new EventController();
		assertEquals(true, controller instanceof EventController);
	}
	@Test
	public void TestlistEvent3Param() {
		String view = controller.list(null, null, null);
		assertEquals(view, Page.EventList.toString());
	}
	@Test
	public void TestlistEvent2Param() {
		String view = controller.list(null, null);
		assertEquals(view, Page.EventList.toString());
	}
	@Test
	public void TestEvent() {
		assertEquals(controller.Event(null, null,null), Page.TicketList.toString());
	}
	@Test
	public void TestDetail() {
		assertEquals(Page.Detail.toString(),controller.detail(null, null,null));
	}
}