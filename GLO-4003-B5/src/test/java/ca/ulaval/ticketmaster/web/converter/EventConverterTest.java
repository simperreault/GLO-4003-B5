package ca.ulaval.ticketmaster.web.converter;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Test;

import ca.ulaval.ticketmaster.dao.util.DataManager;
import ca.ulaval.ticketmaster.model.Event;
import ca.ulaval.ticketmaster.model.Event.Sport;
import ca.ulaval.ticketmaster.web.viewmodels.EventViewModel;

public class EventConverterTest {
	@Test
	public void convertEntryToviewmodel() {
		Event e = new Event();
		e.setSport(Sport.Football);
		e.setGender("gender");
		e.setHomeTeam("hometeam");
		e.setVisitorsTeam("visitorTeam");
		e.setLocation("location");
		e.setStadium("Statdim");
		e.setDate(new Date(0));
		e.setTime(new Date(0));
		
		EventViewModel viewmodel = EventConverter.convert(e);
		
		assertEquals(e.getSport().toString(), viewmodel.getSport());
		assertEquals(e.getGender(), viewmodel.getGender());
		assertEquals(e.getHomeTeam(), viewmodel.getHomeTeam());
		assertEquals(e.getVisitorsTeam(), viewmodel.getVisitorsTeam());
		assertEquals(e.getLocation(), viewmodel.getLocation());
		assertEquals(e.getStadium(), viewmodel.getStadium());
		assertEquals(e.getDate(), viewmodel.getDate());
		assertEquals(e.getTime(), viewmodel.getTime());
	}
	
	@Test
	public void convertviewmodelToEntry() {
		EventViewModel viewmodel = new EventViewModel();
		viewmodel.setSport("Football");
		viewmodel.setGender("gender");
		viewmodel.setHomeTeam("hometeam");
		viewmodel.setVisitorsTeam("visitorTeam");
		viewmodel.setLocation("location");
		viewmodel.setStadium("Statdim");
		viewmodel.setDate(new Date(0));
		viewmodel.setTime(new Date(0));
		
		Event e = EventConverter.convert(viewmodel, new DataManager());
		assertEquals(e.getSport().toString(), viewmodel.getSport());
		assertEquals(e.getGender(), viewmodel.getGender());
		assertEquals(e.getHomeTeam(), viewmodel.getHomeTeam());
		assertEquals(e.getVisitorsTeam(), viewmodel.getVisitorsTeam());
		assertEquals(e.getLocation(), viewmodel.getLocation());
		assertEquals(e.getStadium(), viewmodel.getStadium());
		assertEquals(e.getDate(), viewmodel.getDate());
		assertEquals(e.getTime(), viewmodel.getTime());
	}
}
