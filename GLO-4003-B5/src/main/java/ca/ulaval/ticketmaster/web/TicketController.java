package ca.ulaval.ticketmaster.web;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ca.ulaval.ticketmaster.dao.util.DataManager;
import ca.ulaval.ticketmaster.model.Ticket;
import ca.ulaval.ticketmaster.web.converter.TicketConverter;
import ca.ulaval.ticketmaster.web.viewmodels.TicketViewModel;

/**
 * Handles requests for the application related to Ticket
 */
@Controller
@RequestMapping(value = "/ticket")
// TODO Move what is related to ticket in EventController here
public class TicketController {

	private static final Logger logger = LoggerFactory.getLogger(TicketController.class);
	private DataManager datamanager;
	
	public TicketController(){
		 datamanager = new DataManager();
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String create(Model model) {
		model.addAttribute("ticket", new TicketViewModel());
		model.addAttribute("currentPage", "TicketAdd.jsp");
		return "MainFrame";
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String create(@Valid TicketViewModel viewModel, BindingResult result, RedirectAttributes redirectAttributes, Model model) {
	    if (result.hasErrors()) {  	        
	        model.addAttribute("error", result.getAllErrors());
	        model.addAttribute("ticket", viewModel);
			model.addAttribute("currentPage", "TicketAdd.jsp");
			return "MainFrame";
	    }
		
		Ticket ticket = TicketConverter.convert(viewModel);
		// TODO Add it to XML
		return "redirect:/event/list";
	}
}
