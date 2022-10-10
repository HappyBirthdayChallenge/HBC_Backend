package inha.tnt.hbc.exception;

import inha.tnt.hbc.model.ErrorCode;

public class EntityNotFoundException extends BusinessException {

	public EntityNotFoundException(ErrorCode errorCode) {
		super(errorCode);
	}

}
