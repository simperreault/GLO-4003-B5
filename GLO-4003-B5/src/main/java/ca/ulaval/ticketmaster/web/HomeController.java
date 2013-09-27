package ca.ulaval.ticketmaster.web;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.junit.runner.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import ca.ulaval.ticketmaster.dao.util.DataManager;
import ca.ulaval.ticketmaster.dao.util.XmlReader;
import ca.ulaval.ticketmaster.model.Event;
import ca.ulaval.ticketmaster.model.Ticket;
import ca.ulaval.ticketmaster.model.User;
import ca.ulaval.ticketmaster.web.converter.UserConverter;
import ca.ulaval.ticketmaster.web.viewmodels.EventViewModel;
import ca.ulaval.ticketmaster.web.viewmodels.TicketViewModel;
import ca.ulaval.ticketmaster.web.viewmodels.UserViewModel;

/**
 * Handles requests for the application home page.
 */
@Controller
//@SessionAttributes
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	private DataManager datamanager;
	
	public HomeController(){
		 datamanager = new DataManager();
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
	
	@RequestMapping(value = "/CreateUser", method = RequestMethod.GET)
	public String CreateUser(Locale locale, Model model) {
		model.addAttribute("user", new UserViewModel());
		model.addAttribute("currentPage", "CreateUser.jsp");
		return "MainFrame";
	}
	
	@RequestMapping(value = "/CreateUser", method = RequestMethod.POST)
	public String CreateUser(@Valid UserViewModel viewmodel, BindingResult result, Model model) {
		
		//model.addAttribute("currentPage", "AddUser.jsp");
		model.addAttribute("username", viewmodel.username);
		
		User user = UserConverter.convert(viewmodel);

		if(datamanager.saveUser(user)){
			//blahblah user create sucessfull
		}
		else
		{
			
		}

		model.addAttribute("currentPage", "Home.jsp");
		return "MainFrame";
	}
	
	@RequestMapping(value = "/Login", method = RequestMethod.GET)
	public String Login(Locale locale, Model model) {
		model.addAttribute("currentPage", "Login.jsp");
		return "MainFrame";
	}
	
	@RequestMapping(value = {"/disconnect"}, method = RequestMethod.GET)
	public String Disconnect(Locale locale, Model model, HttpSession session) {
		session.setAttribute("sesacceslevel",null);
		session.setAttribute("sesusername", null);
		return "redirect:/";
	
	}
	
	//Msemble ﾃｧa va ﾃｪtre ﾃ�mettre ailleurs
	//TODO:trouve comment pas hardcoder tous les paths possibles
	@RequestMapping(value = {"/connect","/event/connect","/event/{id}/connect"}, method = RequestMethod.POST)
	public String Login(Locale locale, 
			@RequestParam("username")String username, 
			@RequestParam("password")String password,
			Model model, HttpSession session) {
		
		boolean userIsOk = false;
		
		User user = datamanager.getUser(username);
		if(user != null){
			if(user.getPassword().equals(password)){
				userIsOk = true;
				session.setAttribute("sesacceslevel",user.getAccessLevel().toString());
				session.setAttribute("sesusername", username);
			}
		}
		
		if (!userIsOk)
		{
			model.addAttribute("errorMsg", "La combinaison pseudo/mot de passe est invalide");
			model.addAttribute("currentPage", "Home.jsp");
			return "Mainframe";
		}
		else
		{
			return "redirect:/";
		}

	}
	
}
