package inha.tnt.hbc.application.message.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import inha.tnt.hbc.domain.member.entity.Member;
import inha.tnt.hbc.domain.message.service.MessageService;
import inha.tnt.hbc.domain.room.entity.Room;
import inha.tnt.hbc.domain.room.service.RoomService;
import inha.tnt.hbc.model.message.dto.CreateMessageResponse;
import inha.tnt.hbc.util.SecurityContextUtils;

@Service
@RequiredArgsConstructor
public class MessageFacadeService {

	private final MessageService messageService;
	private final RoomService roomService;
	private final SecurityContextUtils securityContextUtils;

	@Transactional
	public CreateMessageResponse createMessage(Long roomId) {
		final Member member = securityContextUtils.takeoutMember();
		final Room room = roomService.findById(roomId);
		return CreateMessageResponse.of(messageService.create(member, room));
	}

}
