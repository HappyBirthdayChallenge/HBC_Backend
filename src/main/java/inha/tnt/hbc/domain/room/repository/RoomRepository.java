package inha.tnt.hbc.domain.room.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import inha.tnt.hbc.domain.member.entity.Member;
import inha.tnt.hbc.domain.room.entity.Room;
import inha.tnt.hbc.domain.room.repository.jdbc.RoomRepositoryJdbc;

@Transactional(readOnly = true)
public interface RoomRepository extends JpaRepository<Room, Long>, RoomRepositoryJdbc {

	List<Room> findAllByMemberId(Long memberId);

	Optional<Room> findTop1ByMemberOrderByIdDesc(Member member);

}
