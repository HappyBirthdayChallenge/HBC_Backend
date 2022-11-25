package inha.tnt.hbc.domain.message.service;

import static inha.tnt.hbc.model.ErrorCode.*;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import inha.tnt.hbc.application.file.dto.LocalFile;
import inha.tnt.hbc.domain.message.entity.Message;
import inha.tnt.hbc.domain.message.entity.MessageFile;
import inha.tnt.hbc.domain.message.entity.MessageFileTypes;
import inha.tnt.hbc.domain.message.repository.MessageFileRepository;
import inha.tnt.hbc.exception.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class MessageFileService {

	private final MessageFileRepository messageFileRepository;

	public MessageFile save(Message message, LocalFile localFile) {
		final MessageFile messageFile = MessageFile.builder()
			.message(message)
			.name(localFile.getName())
			.type(MessageFileTypes.valueOf(localFile.getType().toUpperCase()))
			.uuid(localFile.getUuid())
			.build();
		return messageFileRepository.save(messageFile);
	}

	public void deleteById(Long fileId) {
		messageFileRepository.deleteById(fileId);
	}

	public MessageFile findById(Long fileId) {
		return messageFileRepository.findById(fileId)
			.orElseThrow(() -> new EntityNotFoundException(FILE_UNFOUNDED));
	}

	public void deleteByMessage(Message message) {
		messageFileRepository.deleteByMessage(message);
	}

}
