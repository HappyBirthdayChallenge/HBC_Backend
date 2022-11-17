package inha.tnt.hbc.domain.message.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import inha.tnt.hbc.domain.member.entity.Member;
import inha.tnt.hbc.domain.message.entity.Message;
import inha.tnt.hbc.domain.message.entity.MessageStatus;
import inha.tnt.hbc.domain.room.entity.Room;

public interface MessageRepository extends JpaRepository<Message, Long> {

	Optional<Message> findByMemberAndRoomAndStatus(Member member, Room room, MessageStatus status);

}
