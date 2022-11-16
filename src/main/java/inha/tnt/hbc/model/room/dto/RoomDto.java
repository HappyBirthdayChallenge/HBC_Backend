package inha.tnt.hbc.model.room.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import inha.tnt.hbc.domain.room.entity.CakeDecorationTypes;
import inha.tnt.hbc.domain.room.entity.Room;
import inha.tnt.hbc.domain.room.entity.RoomDecorationTypes;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonNaming(SnakeCaseStrategy.class)
public class RoomDto {

	private Long roomId;
	private Integer year;
	private RoomDecorationTypes roomType;
	private CakeDecorationTypes cakeType;

	public static RoomDto of(Room room) {
		return RoomDto.builder()
			.roomId(room.getId())
			.year(room.getCreateAt().getYear())
			.roomType(room.getRoomType())
			.cakeType(room.getCakeType())
			.build();
	}

}
