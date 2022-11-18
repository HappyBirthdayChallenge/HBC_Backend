package inha.tnt.hbc.domain.message.service;

import static inha.tnt.hbc.domain.message.entity.MessageStatus.*;
import static inha.tnt.hbc.model.ErrorCode.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import inha.tnt.hbc.domain.member.entity.Member;
import inha.tnt.hbc.domain.message.entity.Message;
import inha.tnt.hbc.domain.message.exception.CannotCreateMessageInMyRoomException;
import inha.tnt.hbc.domain.message.exception.CannotCreateMessgaeMoreThanOnceException;
import inha.tnt.hbc.domain.message.repository.MessageRepository;
import inha.tnt.hbc.domain.room.entity.Room;
import inha.tnt.hbc.exception.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class MessageService {

	private final MessageRepository messageRepository;

	@Transactional
	public Long create(Member member, Room room) {
		if (isMyRoom(member, room)) {
			throw new CannotCreateMessageInMyRoomException();
		}
		if (messageRepository.findByMemberAndRoomAndStatus(member, room, WRITTEN).isPresent()) {
			throw new CannotCreateMessgaeMoreThanOnceException();
		}
		final Message message = Message.builder()
			.member(member)
			.room(room)
			.status(HOLD)
			.build();
		return messageRepository.save(message).getId();
	}

	public Message findById(Long messageId) {
		return messageRepository.findById(messageId)
			.orElseThrow(() -> new EntityNotFoundException(MESSAGE_UNFOUNDED));
	}

	private boolean isMyRoom(Member member, Room room) {
		return room.getMember().getId().equals(member.getId());
	}

}