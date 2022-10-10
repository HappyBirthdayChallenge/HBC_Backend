package inha.tnt.hbc.exception;

import static inha.tnt.hbc.model.ErrorCode.*;

import java.util.List;

import inha.tnt.hbc.model.ErrorResponse.FieldError;

public class InvalidArgumentException extends BusinessException {

	public InvalidArgumentException(List<FieldError> errors) {
		super(INPUT_VALUE_INVALID, errors);
	}

}
