package inha.tnt.hbc.application.file.exception;

import static inha.tnt.hbc.model.ErrorCode.*;

import inha.tnt.hbc.exception.BusinessException;

public class CannotAttachFileToWrittenMessage extends BusinessException {

	public CannotAttachFileToWrittenMessage() {
		super(CANNOT_ATTACH_FILE_TO_WRITTEN_MESSAGE);
	}

}
