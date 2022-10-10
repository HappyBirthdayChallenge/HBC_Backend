package inha.tnt.hbc.security.exception;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.AuthenticationException;

import inha.tnt.hbc.model.ErrorCode;
import inha.tnt.hbc.model.ErrorResponse.FieldError;
import lombok.Getter;

@Getter
public abstract class CustomAuthenticationException extends AuthenticationException {

	private final ErrorCode errorCode;
	private final List<FieldError> errors;

	public CustomAuthenticationException(ErrorCode errorCode, List<FieldError> errors) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
		this.errors = errors;
	}

	public CustomAuthenticationException(ErrorCode errorCode) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
		this.errors = new ArrayList<>();
	}

}
