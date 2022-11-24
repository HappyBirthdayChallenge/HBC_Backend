package inha.tnt.hbc.domain.message.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import inha.tnt.hbc.application.file.dto.LocalFile;
import inha.tnt.hbc.domain.message.entity.Message;
import inha.tnt.hbc.domain.message.entity.MessageFile;
import inha.tnt.hbc.domain.message.entity.MessageFileTypes;
import inha.tnt.hbc.domain.message.repository.MessageFileRepository;

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

}
