package inha.tnt.hbc.domain.member.exception;

import static inha.tnt.hbc.model.ErrorCode.*;

import inha.tnt.hbc.exception.BusinessException;

public class AlreadySetupBirthDateException extends BusinessException {

	public AlreadySetupBirthDateException() {
		super(ALREADY_SETUP_BIRTHDAY);
	}

}
