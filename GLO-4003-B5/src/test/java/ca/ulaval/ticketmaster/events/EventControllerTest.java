package ca.ulaval.ticketmaster.events;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpSession;

import ca.ulaval.ticketmaster.home.Page;

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
		assertEquals(controller.event(null, null,null, null), Page.TicketList.toString());
	}
	@Test
	public void TestDetail() {
		assertEquals(Page.Detail.toString(),controller.detail(null, null,null));
	}
}