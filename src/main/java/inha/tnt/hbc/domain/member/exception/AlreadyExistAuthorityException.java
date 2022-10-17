package inha.tnt.hbc.domain.member.exception;

import inha.tnt.hbc.exception.BusinessException;
import inha.tnt.hbc.model.ErrorCode;

public class AlreadyExistAuthorityException extends BusinessException {

	public AlreadyExistAuthorityException() {
		super(ErrorCode.REDUNDANT_AUTHORITY);
	}

}
