package inha.tnt.hbc.domain.room.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import inha.tnt.hbc.domain.room.entity.Room;
import inha.tnt.hbc.domain.room.repository.jdbc.RoomRepositoryJdbc;

public interface RoomRepository extends JpaRepository<Room, Long>, RoomRepositoryJdbc {

	@Transactional(readOnly = true)
	List<Room> findAllByMemberId(Long memberId);

}
