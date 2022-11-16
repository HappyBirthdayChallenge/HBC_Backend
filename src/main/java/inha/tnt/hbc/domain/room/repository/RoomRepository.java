package inha.tnt.hbc.domain.room.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import inha.tnt.hbc.domain.room.entity.Room;

public interface RoomRepository extends JpaRepository<Room, Long> {

	List<Room> findAllByMemberId(Long memberId);

}
