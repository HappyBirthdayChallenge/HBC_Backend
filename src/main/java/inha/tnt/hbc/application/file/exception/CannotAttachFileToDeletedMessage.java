package inha.tnt.hbc.application.file.exception;

import static inha.tnt.hbc.model.ErrorCode.*;

import inha.tnt.hbc.exception.BusinessException;

public class CannotAttachFileToDeletedMessage extends BusinessException {

	public CannotAttachFileToDeletedMessage() {
		super(CANNOT_ATTACH_FILE_TO_DELETED_MESSAGE);
	}

}
