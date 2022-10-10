package inha.tnt.hbc.exception;

import java.util.ArrayList;
import java.util.List;

import inha.tnt.hbc.model.ErrorCode;
import inha.tnt.hbc.model.ErrorResponse.FieldError;
import lombok.Getter;

@Getter
public abstract class BusinessException extends RuntimeException {

	private final ErrorCode errorCode;
	private final List<FieldError> errors;

	public BusinessException(String message, ErrorCode errorCode) {
		super(message);
		this.errorCode = errorCode;
		this.errors = new ArrayList<>();
	}

	public BusinessException(ErrorCode errorCode) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
		this.errors = new ArrayList<>();
	}

	public BusinessException(ErrorCode errorCode, List<FieldError> errors) {
		super(errorCode.getMessage());
		this.errors = errors;
		this.errorCode = errorCode;
	}

}
