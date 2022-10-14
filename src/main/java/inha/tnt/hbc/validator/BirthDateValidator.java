package inha.tnt.hbc.validator;

import java.time.LocalDate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.stereotype.Component;

import inha.tnt.hbc.annotation.BirthDay;
import inha.tnt.hbc.vo.BirthDate;

@Component
public class BirthDateValidator implements ConstraintValidator<BirthDay, BirthDate> {

	@Override
	public void initialize(BirthDay constraintAnnotation) {
	}

	@Override
	public boolean isValid(BirthDate value, ConstraintValidatorContext context) {
		context.disableDefaultConstraintViolation();
		final String nullMessage = "널이어서는 안됩니다";

		if (value == null) {
			final String errorMessage = nullMessage;
			context.buildConstraintViolationWithTemplate(errorMessage)
				.addConstraintViolation();
			return false;
		}

		boolean flag = true;
		if (value.getType() == null) {
			final String errorMessage = nullMessage;
			context.buildConstraintViolationWithTemplate(errorMessage)
				.addPropertyNode("type")
				.addConstraintViolation();
			flag = false;
		}
		else if (value.getType() != BirthDate.DateType.SOLAR) {
			final String errorMessage = "현재 양력 날짜만 지원합니다.";
			context.buildConstraintViolationWithTemplate(errorMessage)
				.addPropertyNode("type")
				.addConstraintViolation();
			flag = false;
		}

		if (value.getYear() == null) {
			final String errorMessage = nullMessage;
			context.buildConstraintViolationWithTemplate(errorMessage)
				.addPropertyNode("year")
				.addConstraintViolation();
			flag = false;
		}
		else if (value.getYear() < 1900 || value.getYear() >= LocalDate.now().getYear()) {
			final String errorMessage = "1900 ~ " + LocalDate.now().getYear() + " 사이로 입력해 주세요.";
			context.buildConstraintViolationWithTemplate(errorMessage)
				.addPropertyNode("year")
				.addConstraintViolation();
			flag = false;
		}

		if (value.getMonth() == null) {
			final String errorMessage = nullMessage;
			context.buildConstraintViolationWithTemplate(errorMessage)
				.addPropertyNode("month")
				.addConstraintViolation();
			flag = false;
		}
		else if (value.getMonth() < 1 || value.getMonth() > 12) {
			final String errorMessage = "1 ~ 12 사이로 입력해 주세요.";
			context.buildConstraintViolationWithTemplate(errorMessage)
				.addPropertyNode("month")
				.addConstraintViolation();
			flag = false;
		}
		if (value.getDate() == null) {
			final String errorMessage = nullMessage;
			context.buildConstraintViolationWithTemplate(errorMessage)
				.addPropertyNode("date")
				.addConstraintViolation();
			flag = false;
		}
		else {
			try {
				LocalDate.of(value.getYear(), value.getMonth(), value.getDate()).lengthOfMonth();
			} catch (Exception e) {
				final String errorMessage = "존재하는 일자만 입력해 주세요.";
				context.buildConstraintViolationWithTemplate(errorMessage)
					.addPropertyNode("date")
					.addConstraintViolation();
				flag = false;
			}
		}

		return flag;
	}

}
