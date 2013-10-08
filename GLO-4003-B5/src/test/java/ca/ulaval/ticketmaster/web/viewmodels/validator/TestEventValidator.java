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

import ca.ulaval.ticketmaster.model.enums.SportType;
import ca.ulaval.ticketmaster.web.viewmodels.EventViewModel;

public class TestEventValidator {

    private static Validator validator;
    private EventViewModel event;

    @BeforeClass
    public static void setUp() {
	ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
	validator = factory.getValidator();
    }

    @Before
    public void setupTest() {
	event = new EventViewModel();
	event.date = "10/10/1010 10:10";
	event.gender = "M";
	event.homeTeam = "HOME";
	event.visitorsTeam = "VISITORTEAM";
	event.location = "LOCATION";
	event.sport = SportType.FOOTBALL;
	event.stadium = "STADIUM";
    }

    @Test
    public void testDateNotValid() {
	event.date = "1asd0/10/1010 10:10";
	Set<ConstraintViolation<EventViewModel>> constraintViolations = validator.validate(event);
	assertEquals(1, constraintViolations.size());
	
	event.date = "10/10asdasd/1010 10:10";
	constraintViolations = validator.validate(event);
	assertEquals(1, constraintViolations.size());
	
	event.date = "10/10/10s10 10:10123s";
	constraintViolations = validator.validate(event);
	assertEquals(1, constraintViolations.size());
    }
    
    @Test
    public void testGenderNotValid() {
	event.gender = "Q";
	Set<ConstraintViolation<EventViewModel>> constraintViolations = validator.validate(event);
	assertEquals(1, constraintViolations.size());
	
	event.gender = "2";
	constraintViolations = validator.validate(event);
	assertEquals(1, constraintViolations.size());
    }

    @Test
    public void testIsValid() {
	Set<ConstraintViolation<EventViewModel>> constraintViolations = validator.validate(event);

	assertEquals(0, constraintViolations.size());
    }
}