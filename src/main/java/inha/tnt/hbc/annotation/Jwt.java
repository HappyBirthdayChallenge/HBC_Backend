package inha.tnt.hbc.annotation;

import static java.lang.annotation.ElementType.*;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import inha.tnt.hbc.validator.JwtValidator;

@Target({FIELD, TYPE, TYPE_PARAMETER, TYPE_USE, PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = JwtValidator.class)
public @interface Jwt {

	String message() default "";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
