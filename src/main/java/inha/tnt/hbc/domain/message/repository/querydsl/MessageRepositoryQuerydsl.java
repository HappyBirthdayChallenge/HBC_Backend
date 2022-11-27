package inha.tnt.hbc.domain.message.repository.querydsl;

import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import inha.tnt.hbc.domain.message.entity.Message;

public interface MessageRepositoryQuerydsl {

	@Transactional(readOnly = true)
	Optional<Message> findFetchRoomAndDecorationAndAnimationAndMessageFilesById(Long messageId);

	@Transactional(readOnly = true)
	Optional<Message> findFetchRoomMemberByIdAndMemberId(Long messageId, Long memberId);

}
