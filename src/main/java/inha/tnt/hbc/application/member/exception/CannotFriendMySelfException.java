package inha.tnt.hbc.application.member.exception;

import static inha.tnt.hbc.model.ErrorCode.*;

import inha.tnt.hbc.exception.BusinessException;

public class CannotFriendMySelfException extends BusinessException {

	public CannotFriendMySelfException() {
		super(FRIEND_MYSELF_IMPOSSIBLE);
	}

}
