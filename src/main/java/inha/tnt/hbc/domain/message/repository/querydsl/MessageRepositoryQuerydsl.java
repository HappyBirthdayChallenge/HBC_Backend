package inha.tnt.hbc.domain.message.repository.querydsl;

import java.util.Optional;

import inha.tnt.hbc.domain.message.entity.Message;

public interface MessageRepositoryQuerydsl {

	Optional<Message> findFetchRoomAndDecorationAndAnimationAndMessageFilesById(Long messageId);

}
