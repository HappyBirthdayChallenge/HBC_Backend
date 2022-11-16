package inha.tnt.hbc.domain.room.repository.jdbc;

import java.util.List;

import inha.tnt.hbc.domain.room.entity.Room;

public interface RoomRepositoryJdbc {

	void saveAllInBatch(List<Room> rooms);

}
