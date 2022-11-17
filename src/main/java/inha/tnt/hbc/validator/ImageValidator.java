package inha.tnt.hbc.validator;

import static inha.tnt.hbc.model.ErrorCode.*;
import static inha.tnt.hbc.util.Constants.*;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import inha.tnt.hbc.annotation.Image;
import inha.tnt.hbc.domain.message.entity.MessageFileTypes;

@Component
public class ImageValidator implements ConstraintValidator<Image, MultipartFile> {

	private static final int MAX_FILE_BYTES = 10 * MEGA;

	@Override
	public void initialize(Image constraintAnnotation) {
	}

	@Override
	public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {
		context.disableDefaultConstraintViolation();
		if (value.isEmpty()) {
			context.buildConstraintViolationWithTemplate(NOT_NULL.getMessage())
				.addConstraintViolation();
			return false;
		}
		if (value.getSize() > MAX_FILE_BYTES) {
			context.buildConstraintViolationWithTemplate("업로드 가능한 최대 크기를 초과하였습니다.")
				.addConstraintViolation();
			return false;
		}
		if (!MessageFileTypes.isImage(value)) {
			context.buildConstraintViolationWithTemplate("지원하는 이미지 타입이 아닙니다.")
				.addConstraintViolation();
			return false;
		}
		return true;
	}

}
