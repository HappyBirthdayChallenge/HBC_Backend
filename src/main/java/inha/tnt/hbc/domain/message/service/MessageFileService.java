package inha.tnt.hbc.domain.message.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import inha.tnt.hbc.domain.message.dto.MessageFileDto;
import inha.tnt.hbc.domain.message.entity.Message;
import inha.tnt.hbc.domain.message.entity.MessageFile;
import inha.tnt.hbc.domain.message.repository.MessageFileRepository;

@Service
@RequiredArgsConstructor
public class MessageFileService {

	private final MessageFileRepository messageFileRepository;

	@Transactional
	public void saveAll(Message message, List<MessageFileDto> messageFileDtos) {
		final List<MessageFile> messageFiles = messageFileDtos.stream()
			.map(dto -> dto.convertToEntity(message))
			.collect(Collectors.toList());
		messageFileRepository.saveAllInBatch(messageFiles);
	}

}
