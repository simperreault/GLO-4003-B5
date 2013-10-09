package ca.ulaval.ticketmaster.web.viewmodels.validator;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CheckDateValidator implements ConstraintValidator<CheckDate, String> {

    @Override
    public void initialize(CheckDate constraintAnnotation) {
    }

    @Override
    public boolean isValid(String object, ConstraintValidatorContext constraintContext) {
	try {
	    new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(object);
	} catch (ParseException e) {
	    return false;
	}

	return object.matches("^\\d{2}/\\d{2}/\\d{4} \\d{2}:\\d{2}$");
    }

}
