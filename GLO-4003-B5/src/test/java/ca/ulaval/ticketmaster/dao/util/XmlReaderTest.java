package ca.ulaval.ticketmaster.dao.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import ca.ulaval.ticketmaster.model.Ticket;

@RunWith(MockitoJUnitRunner.class)
public class XmlReaderTest {
	
	@Test
	public void TestXmlReader(){
		XmlReader x = new XmlReader();
		assertNotNull(x);
	}

}
