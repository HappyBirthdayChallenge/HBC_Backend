package inha.tnt.hbc.model.room.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import inha.tnt.hbc.domain.member.vo.BirthDate;
import inha.tnt.hbc.domain.room.entity.CakeDecorationTypes;
import inha.tnt.hbc.domain.room.entity.Room;
import inha.tnt.hbc.domain.room.entity.RoomDecorationTypes;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonNaming(SnakeCaseStrategy.class)
public class RoomDto {

	@ApiModelProperty(value = "파티룸 PK", example = "1")
	private Long roomId;
	private BirthDate birthDate;
	@ApiModelProperty(value = "파티룸 유형", example = "ROOM_TYPE1")
	private RoomDecorationTypes roomType;
	@ApiModelProperty(value = "케이크 유형", example = "CAKE_TYPE1")
	private CakeDecorationTypes cakeType;

	public static RoomDto of(Room room) {
		return RoomDto.builder()
			.roomId(room.getId())
			.birthDate(room.getBirthDate())
			.roomType(room.getRoomType())
			.cakeType(room.getCakeType())
			.build();
	}

}
