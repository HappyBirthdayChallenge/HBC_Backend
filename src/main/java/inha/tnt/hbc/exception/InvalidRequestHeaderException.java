package inha.tnt.hbc.exception;

import static inha.tnt.hbc.model.ErrorCode.*;

import java.util.List;

import inha.tnt.hbc.model.ErrorResponse.FieldError;

public class InvalidRequestHeaderException extends BusinessException {

	public InvalidRequestHeaderException(List<FieldError> errors) {
		super(REQUEST_HEADER_MISSING, errors);
	}

}
