package inha.tnt.hbc.exception;

import inha.tnt.hbc.model.ErrorCode;

public class EntityAlreadyExistException extends BusinessException {

	public EntityAlreadyExistException(ErrorCode errorCode) {
		super(errorCode);
	}

}
