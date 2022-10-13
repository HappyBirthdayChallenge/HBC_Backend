package inha.tnt.hbc.security.jwt.exception;

import static inha.tnt.hbc.model.ErrorCode.*;

import java.util.List;

import inha.tnt.hbc.model.ErrorResponse.FieldError;
import inha.tnt.hbc.security.exception.CustomAuthenticationException;
import lombok.Getter;

@Getter
public class JwtAuthenticationException extends CustomAuthenticationException {

	public JwtAuthenticationException(List<FieldError> errors) {
		super(AUTHENTICATION_FAILURE, errors);
	}

}
