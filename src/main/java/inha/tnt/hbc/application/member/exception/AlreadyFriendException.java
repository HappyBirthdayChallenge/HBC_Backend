package inha.tnt.hbc.application.member.exception;

import static inha.tnt.hbc.model.ErrorCode.*;

import inha.tnt.hbc.exception.BusinessException;

public class AlreadyFriendException extends BusinessException {

	public AlreadyFriendException() {
		super(ALREADY_FRIEND);
	}

}
