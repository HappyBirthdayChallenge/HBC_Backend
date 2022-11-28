package inha.tnt.hbc.domain.message.service;

import static inha.tnt.hbc.domain.message.entity.MessageStatus.*;
import static inha.tnt.hbc.model.ErrorCode.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import inha.tnt.hbc.domain.member.entity.Member;
import inha.tnt.hbc.domain.message.dto.MessageWrittenByMeDto;
import inha.tnt.hbc.domain.message.entity.Message;
import inha.tnt.hbc.domain.message.exception.CannotCreateMessageInMyRoomException;
import inha.tnt.hbc.domain.message.exception.CannotCreateMessgaeMoreThanOnceException;
import inha.tnt.hbc.domain.message.repository.MessageRepository;
import inha.tnt.hbc.domain.room.dto.RoomMessageDto;
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

	public Message findFetchRoomMemberByIdAndMemberId(Long messageId, Long memberId) {
		return messageRepository.findFetchRoomMemberByIdAndMemberId(messageId, memberId)
			.orElseThrow(() -> new EntityNotFoundException(MESSAGE_UNFOUNDED));
	}

	public Message findFetchRoomAndDecorationAndAnimationAndMessageFilesById(Long messageId) {
		return messageRepository.findFetchRoomAndDecorationAndAnimationAndMessageFilesById(messageId)
			.orElseThrow(() -> new EntityNotFoundException(MESSAGE_UNFOUNDED));
	}

	public Message findByIdAndMember(Long messageId, Member member) {
		return messageRepository.findByIdAndMember(messageId, member)
			.orElseThrow(() -> new EntityNotFoundException(MESSAGE_UNFOUNDED));
	}

	public Message findFetchRoomAndDecorationAndAnimationByIdAndMemberId(Long messageId, Long memberId) {
		return messageRepository.findFetchRoomAndDecorationAndAnimationByIdAndMemberId(messageId, memberId)
			.orElseThrow(() -> new EntityNotFoundException(MESSAGE_UNFOUNDED));
	}

	public Page<RoomMessageDto> findRoomMessageDtoByRoom(Room room, Pageable pageable) {
		return messageRepository.findRoomMessageDtoByRoom(room, pageable);
	}

	public Page<MessageWrittenByMeDto> findMessageWrittenByMeDtoByMemberId(Long memberId, Pageable pageable) {
		return messageRepository.findMessageWrittenByMeDtoByMemberId(memberId, pageable);
	}

	public int findSequenceNumber(Long memberId, Long roomId, Long messageId, String decorationCategory) {
		return messageRepository.countByMemberIdAndRoomIdAndMessageIdAndDecorationCategory(memberId, roomId, messageId,
			decorationCategory);
	}

	public Message findByMemberIdAndRoomId(Long memberId, Long roomId) {
		return messageRepository.findByMemberIdAndRoomId(memberId, roomId)
			.orElseThrow(() -> new EntityNotFoundException(MESSAGE_UNFOUNDED));
	}

	public Message findById(Long messageId) {
		return messageRepository.findById(messageId)
			.orElseThrow(() -> new EntityNotFoundException(MESSAGE_UNFOUNDED));
	}

	public int countUnReadMessagesByRoom(Room room) {
		return messageRepository.countByRoomAndIsRead(room, false);
	}

	public Message findFetchMemberAndRoomMemberById(Long messageId) {
		return messageRepository.findFetchMemberAndRoomMemberById(messageId)
			.orElseThrow(() -> new EntityNotFoundException(MESSAGE_UNFOUNDED));
	}

	private boolean isMyRoom(Member member, Room room) {
		return room.getMember().getId().equals(member.getId());
	}

}
