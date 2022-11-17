package inha.tnt.hbc.domain.message.exception;

import static inha.tnt.hbc.model.ErrorCode.*;

import inha.tnt.hbc.exception.BusinessException;

public class CannotCreateMessgaeMoreThanOnceException extends BusinessException {

	public CannotCreateMessgaeMoreThanOnceException() {
		super(CANNOT_CREATE_MESSAGE_MORE_THAN_ONCE);
	}

}
