package inha.tnt.hbc.application.message.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import inha.tnt.hbc.domain.member.entity.Member;
import inha.tnt.hbc.domain.message.dto.MessageFileDto;
import inha.tnt.hbc.domain.message.entity.Message;
import inha.tnt.hbc.domain.message.service.AnimationService;
import inha.tnt.hbc.domain.message.service.DecorationService;
import inha.tnt.hbc.domain.message.service.MessageFileRedisService;
import inha.tnt.hbc.domain.message.service.MessageFileService;
import inha.tnt.hbc.domain.message.service.MessageService;
import inha.tnt.hbc.domain.room.entity.Room;
import inha.tnt.hbc.domain.room.service.RoomService;
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
		final List<MessageFileDto> messageFileDtos = messageFileRedisService.getAll(message.getId(),
			request.getFileIds());
		messageFileService.saveAll(message, messageFileDtos);
		decorationService.save(message, request.getDecorationType());
		animationService.save(message, request.getAnimationType());
		messageFileRedisService.delete(message.getId());
	}

}
