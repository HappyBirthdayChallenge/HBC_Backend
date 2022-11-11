package inha.tnt.hbc.validator;


import static inha.tnt.hbc.model.ErrorCode.*;

import java.time.LocalDate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.stereotype.Component;

import inha.tnt.hbc.annotation.BirthDay;
import inha.tnt.hbc.domain.member.vo.BirthDate;

// TODO: [Refactor] 에러 메시지 상수화
@Component
public class BirthDateValidator implements ConstraintValidator<BirthDay, BirthDate> {

	@Override
	public void initialize(BirthDay constraintAnnotation) {
	}

	@Override
	public boolean isValid(BirthDate value, ConstraintValidatorContext context) {
		context.disableDefaultConstraintViolation();
		if (value == null) {
			context.buildConstraintViolationWithTemplate(NOT_NULL.getMessage())
				.addConstraintViolation();
			return false;
		}

		boolean flag = true;
		if (value.getType() == null) {
			context.buildConstraintViolationWithTemplate(NOT_NULL.getMessage())
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
			context.buildConstraintViolationWithTemplate(NOT_NULL.getMessage())
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
			context.buildConstraintViolationWithTemplate(NOT_NULL.getMessage())
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
			context.buildConstraintViolationWithTemplate(NOT_NULL.getMessage())
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
