package inha.tnt.hbc.application.message.service;

import static inha.tnt.hbc.model.ErrorCode.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import inha.tnt.hbc.domain.member.entity.Member;
import inha.tnt.hbc.domain.message.entity.Message;
import inha.tnt.hbc.domain.message.service.AnimationService;
import inha.tnt.hbc.domain.message.service.DecorationService;
import inha.tnt.hbc.domain.message.service.MessageFileRedisService;
import inha.tnt.hbc.domain.message.service.MessageFileService;
import inha.tnt.hbc.domain.message.service.MessageService;
import inha.tnt.hbc.domain.room.entity.Room;
import inha.tnt.hbc.domain.room.service.RoomService;
import inha.tnt.hbc.exception.InvalidArgumentException;
import inha.tnt.hbc.model.message.dto.CreateMessageResponse;
import inha.tnt.hbc.model.message.dto.InquiryMessageResponse;
import inha.tnt.hbc.model.message.dto.MessageRequest;
import inha.tnt.hbc.util.SecurityContextUtils;

@Service
@RequiredArgsConstructor
public class MessageFacadeService {

	private final MessageService messageService;
	private final RoomService roomService;
	private final SecurityContextUtils securityContextUtils;
	private final MessageFileService messageFileService;
	private final MessageFileRedisService messageFileRedisService;
	private final DecorationService decorationService;
	private final AnimationService animationService;

	@Transactional
	public CreateMessageResponse createMessage(Long roomId) {
		final Member member = securityContextUtils.takeoutMember();
		final Room room = roomService.findById(roomId);
		return CreateMessageResponse.of(messageService.create(member, room));
	}

	@Transactional
	public void uploadMessage(MessageRequest request) {
		final Member member = securityContextUtils.takeoutMember();
		final Message message = messageService.findByIdAndMember(request.getMessageId(), member);
		decorationService.save(message, request.getDecorationType());
		animationService.save(message, request.getAnimationType());
		message.uploadMessage(request.getContent());
		messageFileRedisService.delete(message.getId());
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

	private boolean isRoomOwnerAndBeforeBirthday(Member member, Room room) {
		return room.isOwner(member) && room.isBeforeBirthDay();
	}

	private boolean isThirdParty(Member member, Message message) {
		return !message.isWriter(member) && !message.getRoom().isOwner(member);
	}

}
