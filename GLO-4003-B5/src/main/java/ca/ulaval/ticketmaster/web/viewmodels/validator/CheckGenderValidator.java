package ca.ulaval.ticketmaster.web.viewmodels.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CheckGenderValidator implements ConstraintValidator<CheckGender, String> {

    @Override
    public void initialize(CheckGender arg0) {

    }

    @Override
    public boolean isValid(String object, ConstraintValidatorContext arg1) {
	return object.equals("M") || object.equals("F");
    }

}
