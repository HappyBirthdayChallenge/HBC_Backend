package inha.tnt.hbc.domain.room.repository.jdbc;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import inha.tnt.hbc.domain.room.entity.Room;

public interface RoomRepositoryJdbc {

	@Transactional
	void saveAllInBatch(List<Room> rooms);

}
