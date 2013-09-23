package ca.ulaval.billet;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ca.ulaval.billet.model.Event;
import ca.ulaval.billet.model.Ticket;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/List", method = RequestMethod.GET)
	public String list(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		Event e1 = new Event(1);
		e1.setSport("Ballon-balais");
		e1.setGender("Masculin");
		e1.setDate(new Date(2013,9,22));
		e1.setVisitorsTeam("BISHOP");
		e1.setHomeTeam("ULAVAL");
		e1.setLocation("ULAVAL");
		Ticket t1 = new Ticket(1,e1);
		t1.setSection("A");
		t1.setSeat(22);
		t1.setPrice(14.99);
		
		Event e2 = new Event(2);
		e2.setSport("Basket");
		e2.setGender("Feminin");
		e2.setDate(new Date(2013,9,22));
		e2.setVisitorsTeam("BISHOP2");
		e2.setHomeTeam("ULAVAL2");
		e2.setLocation("ULAVAL2");
		Ticket t2 = new Ticket(2,e2);
		t2.setSection("Z");
		t2.setSeat(5);
		t2.setPrice(1);
		
		
		
		ArrayList<Ticket> list = new ArrayList<Ticket>();
		list.add(t1);
		list.add(t2);
		
		model.addAttribute("Ticketlist", list );
		model.addAttribute("currentPage", "list.jsp");
		return "MainFrame";
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String detail(@PathVariable int id, Model model) {
		Event e1 = new Event(1);
		e1.setSport("Ballon-balais");
		e1.setGender("Masculin");
		e1.setDate(new Date(2013,9,22));
		e1.setVisitorsTeam("BISHOP");
		e1.setHomeTeam("ULAVAL");
		e1.setLocation("ULAVAL");
		Ticket t1 = new Ticket(1,e1);
		t1.setSection("A");
		t1.setSeat(22);
		t1.setPrice(14.99);
		
		model.addAttribute("ticket", t1);
		model.addAttribute("currentPage", "detail.jsp");
		return "MainFrame";
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String MainFrame(Locale locale, Model model) {
		model.addAttribute("currentPage", "Home.jsp");
		return "MainFrame";
	}
	
	@RequestMapping(value = "/Home", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		model.addAttribute("currentPage", "Home.jsp");
		return "MainFrame";
	}
	
}
