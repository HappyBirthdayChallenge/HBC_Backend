package inha.tnt.hbc.domain.message.exception;

import static inha.tnt.hbc.model.ErrorCode.*;

import inha.tnt.hbc.exception.BusinessException;

public class CannotCreateMessageInMyRoomException extends BusinessException {

	public CannotCreateMessageInMyRoomException() {
		super(CANNOT_CREATE_MESSAGE_MY_ROOM);
	}

}
