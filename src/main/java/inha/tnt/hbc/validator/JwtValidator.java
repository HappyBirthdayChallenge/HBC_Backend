package inha.tnt.hbc.validator;

import static inha.tnt.hbc.model.ErrorCode.*;
import static inha.tnt.hbc.util.Constants.*;
import static inha.tnt.hbc.util.JwtUtils.*;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import inha.tnt.hbc.annotation.Jwt;

public class JwtValidator implements ConstraintValidator<Jwt, String> {

	@Override
	public void initialize(Jwt constraintAnnotation) {
	}

	@Override
	public boolean isValid(String jwt, ConstraintValidatorContext context) {
		context.disableDefaultConstraintViolation();
		if (jwt == null) {
			context.buildConstraintViolationWithTemplate(NOT_NULL.getMessage())
				.addConstraintViolation();
			return false;
		}
		final String prefix = JWT_TYPE + SPACE;
		if (!jwt.startsWith(prefix)) {
			context.buildConstraintViolationWithTemplate(BEARER_PREFIX_MISSING.getMessage())
				.addConstraintViolation();
			return false;
		}
		return true;
	}

}
