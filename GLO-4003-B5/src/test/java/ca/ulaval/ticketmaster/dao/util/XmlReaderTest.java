package ca.ulaval.ticketmaster.dao.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.ticketmaster.model.Event;
import ca.ulaval.ticketmaster.model.Ticket;
import ca.ulaval.ticketmaster.model.User;
import ca.ulaval.ticketmaster.model.User.AccessLevel;

@RunWith(MockitoJUnitRunner.class)
public class XmlReaderTest {

    public static String DATA_FILE = "src/test/resources/testDataXmlReader.xml";

    @Test
    public void TestXmlReader() {
	XmlReader x = new XmlReader(DATA_FILE);
	assertNotNull(x);
    }

    @Test
    public void TestConnectError() {
	XmlReader x = new XmlReader(DATA_FILE);
	assertEquals(x.connect("notrealfile"), false);
    }

    @Test
    public void TestConnect() {
	XmlReader x = new XmlReader(DATA_FILE);

	assertEquals(x.connect(DATA_FILE), true);

    }

    @Test
    public void TestStartup() {
	XmlReader x = new XmlReader(DATA_FILE);
	assertEquals(x.connect("src/test/resources/testDataXmlReader.xml"), true);

	int[] result = x.readStartupInformation();
	assertEquals(result[0], 1);
	assertEquals(result[1], 2);
    }

    @Test
    public void TestLoadEvent() {
	XmlReader x = new XmlReader(DATA_FILE);

	assertEquals(x.connect("src/test/resources/testDataXmlReader.xml"), true);
	Event e = x.loadEvent(UUID.fromString("d05696d9-8cae-4aca-a088-1501576e8187"), false);
	assertNotNull(e);
	assertEquals(e.getId(), UUID.fromString("d05696d9-8cae-4aca-a088-1501576e8187"));
	assertEquals(e.getStadium(), "Telus");
	assertEquals(e.getTicketList().size(), 0);
	e = x.loadEvent(UUID.fromString("d05696d9-8cae-4aca-a088-1501576e8187"));
	assertEquals(e.getTicketList().get(2).getSeat(), "qweqweqw");
	assertEquals(e.getSectionList().size(), 1);
	assertEquals(e.getSectionList().get(0), "B");
	assertEquals(e.getTicketList().size(), 3);
	System.out.println(e.getTicketList());
	e = x.loadEvent(UUID.randomUUID());
	assertNull(e);
    }

    @Test
    public void TestLoadTicket() {
	XmlReader x = new XmlReader(DATA_FILE);

	assertEquals(x.connect("src/test/resources/testDataXmlReader.xml"), true);
	Ticket t = x.loadTicket(UUID.fromString("d05696d9-8cae-4aca-a088-1501576e8187"),
		UUID.fromString("108417d5-d0b4-4005-a357-0cf82ebd066d"));
	assertNotNull(t);
	assertEquals(t.getId(), UUID.fromString("108417d5-d0b4-4005-a357-0cf82ebd066d"));
	assertEquals(t.getEvent().getSectionList().get(0), "B");
	t = x.loadTicket(UUID.randomUUID(), UUID.randomUUID());
	assertNull(t);
    }

    @Test
    public void TestLoadEvents() {
	XmlReader x = new XmlReader(DATA_FILE);

	assertEquals(x.connect("src/test/resources/testDataXmlReader.xml"), true);
	List<Event> l = x.loadEvents();
	assertEquals(l.size(), 1);
    }

    @Test
    public void TestLoadUser() {
	XmlReader x = new XmlReader(DATA_FILE);

	assertEquals(x.connect("src/test/resources/testDataXmlReader.xml"), true);
	User u = x.userAuthenticate("CarloBoutet");
	assertEquals(u.getPassword(), "123");
	assertEquals(u.getAccessLevel(), AccessLevel.Admin);
	assertNull(x.userAuthenticate("bobthenonexistinguser"));

    }

    @Test
    public void TestLoadUsers() {
	XmlReader x = new XmlReader(DATA_FILE);

	assertEquals(x.connect("src/test/resources/testDataXmlReader.xml"), true);
	List<User> l = x.loadUsers();
	assertEquals(l.size(), 2);
	assertEquals(l.get(0).getAccessLevel(), AccessLevel.Admin);

    }

}
