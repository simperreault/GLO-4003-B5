package ca.ulaval.ticketmaster.web.viewmodels.validator;

import static org.junit.Assert.assertEquals;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ca.ulaval.ticketmaster.model.enums.TicketType;
import ca.ulaval.ticketmaster.web.viewmodels.TicketViewModel;

public class TestTicketValidator {
    private static Validator validator;
    private TicketViewModel ticket;

    @BeforeClass
    public static void setUp() {
	ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
	validator = factory.getValidator();
    }

    @Before
    public void setupTest() {
	ticket = new TicketViewModel();
	ticket.owner = "OWNER";
	ticket.price = "23.0";
	ticket.resellprice = "24.0";
	ticket.seat = "SEAT";
	ticket.section = "SECTION";
	ticket.type = TicketType.GENERAL;
    }

    @Test
    public void testPriceNotValid() {
	ticket.price = "";
	Set<ConstraintViolation<TicketViewModel>> constraintViolations = validator.validate(ticket);
	assertEquals(1, constraintViolations.size());

	ticket.price = "-0.3";
	constraintViolations = validator.validate(ticket);
	assertEquals(1, constraintViolations.size());

	ticket.price = "0.0";
	constraintViolations = validator.validate(ticket);
	assertEquals(1, constraintViolations.size());

	ticket.price = "qwe";
	constraintViolations = validator.validate(ticket);
	assertEquals(1, constraintViolations.size());
    }

    @Test
    public void testIsValid() {
	Set<ConstraintViolation<TicketViewModel>> constraintViolations = validator.validate(ticket);

	assertEquals(0, constraintViolations.size());
    }
}
