package ca.ulaval.ticketmaster.web.viewmodels.validator;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ METHOD, FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = CheckDateValidator.class)
@Documented
public @interface CheckDate {

    String message() default "La date doit avoir le format dd/mm/yyyy HH:mm.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
