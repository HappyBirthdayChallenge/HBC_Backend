package inha.tnt.hbc.annotation;

import static java.lang.annotation.ElementType.*;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import inha.tnt.hbc.validator.VideoValidator;

@Target({FIELD, TYPE, TYPE_PARAMETER, TYPE_USE, PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = VideoValidator.class)
public @interface Video {

	String message() default "";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
