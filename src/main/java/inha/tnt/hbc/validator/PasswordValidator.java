package inha.tnt.hbc.validator;

import static inha.tnt.hbc.model.ErrorCode.*;

import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import inha.tnt.hbc.annotation.Password;
import inha.tnt.hbc.util.MessageTemplates;

public class PasswordValidator implements ConstraintValidator<Password, String> {

	public static final String REGEXP = "^.*(?=^.{10,20}$)(?=.*\\d)(?=.*[a-zA-Z])(?=.*[`~!@#$%^&*()]).*$";

	@Override
	public void initialize(Password constraintAnnotation) {
	}

	@Override
	public boolean isValid(String password, ConstraintValidatorContext context) {
		context.disableDefaultConstraintViolation();
		if (password == null) {
			context.buildConstraintViolationWithTemplate(NOT_NULL.getMessage())
				.addConstraintViolation();
			return false;
		}
		if (!Pattern.matches(REGEXP, password)) {
			context.buildConstraintViolationWithTemplate(MessageTemplates.getPatternMessage(REGEXP))
				.addConstraintViolation();
			return false;
		}
		return true;
	}

}
