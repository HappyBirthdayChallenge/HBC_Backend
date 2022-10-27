package inha.tnt.hbc.validator;

import static inha.tnt.hbc.model.ErrorCode.*;
import static inha.tnt.hbc.model.ResultCode.*;

import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.stereotype.Component;

import inha.tnt.hbc.annotation.Username;
import inha.tnt.hbc.domain.member.service.MemberService;
import inha.tnt.hbc.util.MessageTemplates;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UsernameValidator implements ConstraintValidator<Username, String> {

	public static final String REGEXP = "^[A-Za-z\\d]{5,20}$";
	private final MemberService memberService;
	private boolean check;

	@Override
	public void initialize(Username constraintAnnotation) {
		check = constraintAnnotation.check();
	}

	@Override
	public boolean isValid(String username, ConstraintValidatorContext context) {
		context.disableDefaultConstraintViolation();
		if (username == null) {
			context.buildConstraintViolationWithTemplate(NOT_NULL.getMessage())
				.addConstraintViolation();
			return false;
		}
		if (!Pattern.matches(REGEXP, username)) {
			context.buildConstraintViolationWithTemplate(MessageTemplates.getPatternMessage(REGEXP))
				.addConstraintViolation();
			return false;
		}
		if (check && memberService.isExistingUsername(username)) {
			context.buildConstraintViolationWithTemplate(USERNAME_ALREADY_USED.getMessage())
				.addConstraintViolation();
			return false;
		}
		return true;
	}

}
