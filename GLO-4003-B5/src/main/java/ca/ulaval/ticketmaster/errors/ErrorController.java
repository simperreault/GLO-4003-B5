package ca.ulaval.ticketmaster.errors;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ca.ulaval.ticketmaster.home.Page;

@RequestMapping(value = "/error")
public class ErrorController {

    @RequestMapping(value = "/404", method = RequestMethod.GET)
    public String notFound() {
	return Page.Error404.toString();
    }

    @RequestMapping(value = "/Unauthorized", method = RequestMethod.GET)
    public String unauthorized() {
	return Page.Error403.toString();
    }
}
