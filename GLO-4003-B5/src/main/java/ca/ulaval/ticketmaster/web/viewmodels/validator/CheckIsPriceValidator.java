package ca.ulaval.ticketmaster.web.viewmodels.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CheckIsPriceValidator implements ConstraintValidator<CheckIsPrice, String> {

    @Override
    public void initialize(CheckIsPrice arg0) {

    }

    @Override
    public boolean isValid(String object, ConstraintValidatorContext arg1) {

	if (object == "")
	    return false;

	Double db;
	try {
	    db = Double.parseDouble(object);
	} catch (NumberFormatException e) {
	    return false;
	}

	return db > 0.0;
    }
}
