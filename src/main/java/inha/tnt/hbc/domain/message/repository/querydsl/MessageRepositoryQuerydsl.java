package inha.tnt.hbc.domain.message.repository.querydsl;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import inha.tnt.hbc.domain.message.dto.MessageWrittenByMeDto;
import inha.tnt.hbc.domain.message.entity.Message;
import inha.tnt.hbc.domain.room.dto.RoomMessageDto;
import inha.tnt.hbc.domain.room.entity.Room;

@Transactional(readOnly = true)
public interface MessageRepositoryQuerydsl {

	Optional<Message> findFetchRoomAndMemberAndDecorationAndAnimationAndMessageFilesById(Long messageId);

	Optional<Message> findFetchRoomMemberByIdAndMemberId(Long messageId, Long memberId);

	Optional<Message> findFetchRoomAndDecorationAndAnimationByIdAndMemberId(Long messageId, Long memberId);

	Page<RoomMessageDto> findRoomMessageDtoByRoom(Room room, Pageable pageable);

	Page<MessageWrittenByMeDto> findMessageWrittenByMeDtoByMemberId(Long memberId, Pageable pageable);

	int countByMemberIdAndRoomIdAndMessageIdAndDecorationCategory(Long memberId, Long roomId, Long messageId,
		String decorationCategory);

	Optional<Message> findFetchMemberAndRoomMemberById(Long messageId);

}
