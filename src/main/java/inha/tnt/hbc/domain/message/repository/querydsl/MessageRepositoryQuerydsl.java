package inha.tnt.hbc.domain.message.repository.querydsl;

import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import inha.tnt.hbc.domain.message.entity.Message;

@Transactional(readOnly = true)
public interface MessageRepositoryQuerydsl {

	Optional<Message> findFetchRoomAndDecorationAndAnimationAndMessageFilesById(Long messageId);

	Optional<Message> findFetchRoomMemberByIdAndMemberId(Long messageId, Long memberId);

	Optional<Message> findFetchRoomAndDecorationAndAnimationByIdAndMemberId(Long messageId, Long memberId);

}
