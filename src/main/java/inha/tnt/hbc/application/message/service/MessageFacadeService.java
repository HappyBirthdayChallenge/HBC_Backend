package inha.tnt.hbc.application.message.service;

import static inha.tnt.hbc.domain.message.entity.MessageStatus.*;
import static inha.tnt.hbc.model.ErrorCode.*;
import static inha.tnt.hbc.util.Constants.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import inha.tnt.hbc.domain.alarm.service.AlarmService;
import inha.tnt.hbc.domain.member.entity.Member;
import inha.tnt.hbc.domain.message.dto.MessageWrittenByMeDto;
import inha.tnt.hbc.domain.message.entity.Message;
import inha.tnt.hbc.domain.message.service.AnimationService;
import inha.tnt.hbc.domain.message.service.DecorationService;
import inha.tnt.hbc.domain.message.service.MessageFileRedisService;
import inha.tnt.hbc.domain.message.service.MessageFileService;
import inha.tnt.hbc.domain.message.service.MessageService;
import inha.tnt.hbc.domain.room.entity.Room;
import inha.tnt.hbc.domain.room.service.RoomService;
import inha.tnt.hbc.exception.InvalidArgumentException;
import inha.tnt.hbc.infra.aws.S3Uploader;
import inha.tnt.hbc.model.message.dto.CreateMessageResponse;
import inha.tnt.hbc.model.message.dto.InquiryMessageResponse;
import inha.tnt.hbc.model.message.dto.MessageRequest;
import inha.tnt.hbc.model.message.dto.MessageWrittenByMePageResponse;
import inha.tnt.hbc.util.SecurityContextUtils;

@Service
@RequiredArgsConstructor
public class MessageFacadeService {

	private final MessageService messageService;
	private final RoomService roomService;
	private final SecurityContextUtils securityContextUtils;
	private final MessageFileRedisService messageFileRedisService;
	private final MessageFileService messageFileService;
	private final DecorationService decorationService;
	private final AnimationService animationService;
	private final AlarmService alarmService;
	private final S3Uploader s3Uploader;

	@Transactional
	public CreateMessageResponse createMessage(Long roomId) {
		final Member member = securityContextUtils.takeoutMember();
		final Room room = roomService.findById(roomId);
		if (!room.isBeforeBirthDay()) {
			throw new InvalidArgumentException(CANNOT_CREATE_MESSAGE_AFTER_BIRTHDAY);
		}
		return CreateMessageResponse.of(messageService.create(member, room));
	}

	@Transactional
	public void uploadMessage(MessageRequest request) {
		final Long memberId = securityContextUtils.takeoutMemberId();
		final Message message = messageService.findFetchRoomMemberByIdAndMemberId(request.getMessageId(), memberId);
		if (!message.getRoom().isAfterBirthDay()) {
			throw new InvalidArgumentException(CANNOT_UPLOAD_AFTER_BIRTHDAY);
		}
		decorationService.save(message, request.getDecorationType());
		animationService.save(message, request.getAnimationType());
		message.uploadMessage(request.getContent());
		messageFileRedisService.delete(message.getId());
		alarmService.alarmMessage(message);
	}

	public InquiryMessageResponse inquiryMessage(Long messageId) {
		final Member member = securityContextUtils.takeoutMember();
		final Message message = messageService.findFetchRoomAndDecorationAndAnimationAndMessageFilesById(messageId);
		if (isThirdParty(member, message)) {
			throw new InvalidArgumentException(THIRD_PARTY_CANNOT_INQUIRY_MESSAGE);
		} else if (isRoomOwnerAndBeforeBirthday(member, message.getRoom())) {
			throw new InvalidArgumentException(ROOM_OWNER_CANNOT_INQUIRY_MESSAGE_BEFORE_BIRTHDAY);
		}
		return InquiryMessageResponse.of(member, message);
	}

	@Transactional
	public void cancelMessage(Long messageId) {
		final Member member = securityContextUtils.takeoutMember();
		final Message message = messageService.findByIdAndMember(messageId, member);
		if (message.getStatus().equals(DELETED)) {
			throw new InvalidArgumentException(CANNOT_CANCEL_DELETED_MESSAGE);
		} else if (message.getStatus().equals(WRITTEN)) {
			throw new InvalidArgumentException(CANNOT_CANCEL_WRITTEN_MESSAGE);
		}
		message.delete();
		messageFileService.deleteByMessage(message);
		messageFileRedisService.delete(messageId);
		s3Uploader.deleteDirectory(message.getS3Directory());
	}

	@Transactional
	public void deleteMessage(Long messageId) {
		final Long memberId = securityContextUtils.takeoutMemberId();
		final Message message = messageService.findFetchRoomMemberByIdAndMemberId(messageId, memberId);
		if (!message.getStatus().equals(WRITTEN)) {
			throw new InvalidArgumentException(CANNOT_DELETE_NOT_WRITTEN_MESSAGE);
		}
		if (!message.getRoom().isBeforeBirthDay()) {
			throw new InvalidArgumentException(CANNOT_DELETE_MESSAGE_AFTER_BIRTHDAY);
		}
		message.delete();
		decorationService.deleteByMessage(message);
		animationService.deleteByMessage(message);
		messageFileService.deleteByMessage(message);
		messageFileRedisService.delete(messageId);
		s3Uploader.deleteDirectory(message.getS3Directory());
	}

	@Transactional
	public void editMessage(MessageRequest request) {
		final Long memberId = securityContextUtils.takeoutMemberId();
		final Message message = messageService.findFetchRoomAndDecorationAndAnimationByIdAndMemberId(
			request.getMessageId(), memberId);
		if (!message.getStatus().equals(WRITTEN)) {
			throw new InvalidArgumentException(CANNOT_EDIT_NOT_WRITTEN_MESSAGE);
		}
		if (!message.getRoom().isAfterBirthDay()) {
			throw new InvalidArgumentException(CANNOT_EDIT_AFTER_BIRTHDAY);
		}
		message.getDecoration().change(request.getDecorationType());
		message.getAnimation().change(request.getAnimationType());
		message.uploadMessage(request.getContent());
		messageFileRedisService.delete(message.getId());
	}

	public MessageWrittenByMePageResponse getMessagesWrittenByMe(Integer page, Integer size) {
		final Long memberId = securityContextUtils.takeoutMemberId();
		final Pageable pageable = PageRequest.of(page - PAGE_CORRECTION_VALUE, size);
		final Page<MessageWrittenByMeDto> dto = messageService.findMessageWrittenByMeDtoByMemberId(memberId, pageable);
		return MessageWrittenByMePageResponse.of(dto);
	}

	private boolean isRoomOwnerAndBeforeBirthday(Member member, Room room) {
		return room.isOwner(member) && room.isBeforeBirthDay();
	}

	private boolean isThirdParty(Member member, Message message) {
		return !message.isWriter(member) && !message.getRoom().isOwner(member);
	}

}
