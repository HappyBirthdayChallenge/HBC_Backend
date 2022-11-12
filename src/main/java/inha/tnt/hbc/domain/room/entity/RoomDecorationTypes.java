package inha.tnt.hbc.domain.room.entity;

import java.util.Random;

public enum RoomDecorationTypes {
	ROOM_TYPE1,
	;

	public static RoomDecorationTypes random() {
		return RoomDecorationTypes.values()[new Random().nextInt(
			RoomDecorationTypes.values().length)];
	}
}
