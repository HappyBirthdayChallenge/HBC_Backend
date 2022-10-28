package inha.tnt.hbc.annotation;

import static java.lang.annotation.ElementType.*;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import inha.tnt.hbc.validator.PasswordValidator;

@Target({FIELD, TYPE, TYPE_PARAMETER, TYPE_USE, PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordValidator.class)
public @interface Password {

	String message() default "";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
