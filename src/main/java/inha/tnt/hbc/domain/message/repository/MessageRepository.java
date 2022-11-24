package inha.tnt.hbc.domain.message.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import inha.tnt.hbc.domain.member.entity.Member;
import inha.tnt.hbc.domain.message.entity.Message;
import inha.tnt.hbc.domain.message.entity.MessageStatus;
import inha.tnt.hbc.domain.message.repository.querydsl.MessageRepositoryQuerydsl;
import inha.tnt.hbc.domain.room.entity.Room;

public interface MessageRepository extends JpaRepository<Message, Long>, MessageRepositoryQuerydsl {

	@Transactional(readOnly = true)
	Optional<Message> findByMemberAndRoomAndStatus(Member member, Room room, MessageStatus status);

	@Transactional(readOnly = true)
	Optional<Message> findByIdAndMember(Long messageId, Member member);

}
