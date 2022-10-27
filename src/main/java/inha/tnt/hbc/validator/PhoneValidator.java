package inha.tnt.hbc.validator;

import static inha.tnt.hbc.model.ErrorCode.*;
import static inha.tnt.hbc.model.ResultCode.*;

import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.stereotype.Component;

import inha.tnt.hbc.annotation.Phone;
import inha.tnt.hbc.domain.member.service.MemberService;
import inha.tnt.hbc.util.MessageTemplates;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PhoneValidator implements ConstraintValidator<Phone, String> {

	public static final String REGEXP = "^\\d{3}-\\d{3,4}-\\d{4}$";
	private final MemberService memberService;
	private boolean check;

	@Override
	public void initialize(Phone constraintAnnotation) {
		check = constraintAnnotation.check();
	}

	@Override
	public boolean isValid(String phone, ConstraintValidatorContext context) {
		context.disableDefaultConstraintViolation();
		if (phone == null) {
			context.buildConstraintViolationWithTemplate(NOT_NULL.getMessage())
				.addConstraintViolation();
			return false;
		}
		if (!Pattern.matches(REGEXP, phone)) {
			context.buildConstraintViolationWithTemplate(MessageTemplates.getPatternMessage(REGEXP))
				.addConstraintViolation();
			return false;
		}
		if (check && memberService.isExistingPhone(phone)) {
			context.buildConstraintViolationWithTemplate(PHONE_ALREADY_USED.getMessage())
				.addConstraintViolation();
			return false;
		}
		return true;
	}

}
