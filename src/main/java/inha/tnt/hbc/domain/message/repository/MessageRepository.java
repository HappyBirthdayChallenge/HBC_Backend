package inha.tnt.hbc.domain.message.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import inha.tnt.hbc.domain.member.entity.Member;
import inha.tnt.hbc.domain.message.entity.Message;
import inha.tnt.hbc.domain.message.entity.MessageStatus;
import inha.tnt.hbc.domain.message.repository.querydsl.MessageRepositoryQuerydsl;
import inha.tnt.hbc.domain.room.entity.Room;

@Transactional(readOnly = true)
public interface MessageRepository extends JpaRepository<Message, Long>, MessageRepositoryQuerydsl {

	Optional<Message> findByMemberAndRoomAndStatus(Member member, Room room, MessageStatus status);

	Optional<Message> findByIdAndMember(Long messageId, Member member);

	Optional<Message> findByMemberIdAndRoomIdAndStatus(Long memberId, Long roomId, MessageStatus status);

	int countByRoomAndIsReadAndStatus(Room room, boolean isRead, MessageStatus status);

}
