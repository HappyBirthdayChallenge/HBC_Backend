package inha.tnt.hbc.domain.room.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import inha.tnt.hbc.domain.room.entity.Room;
import inha.tnt.hbc.domain.room.repository.jdbc.RoomRepositoryJdbc;

public interface RoomRepository extends JpaRepository<Room, Long>, RoomRepositoryJdbc {

	List<Room> findAllByMemberId(Long memberId);

}
