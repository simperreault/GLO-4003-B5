package ca.ulaval.ticketmaster.web.viewmodels.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CheckEmailValidator implements ConstraintValidator<CheckEmail, String> {

	@Override
	public boolean isValid(String object, ConstraintValidatorContext constraintContext) {
		return object.matches("\b[A-Z0-9._%+-]+@[A-Z0-9.-]+.[A-Z]{2,4}\b");
	}

	@Override
	public void initialize(CheckEmail arg0) {
	}

}
