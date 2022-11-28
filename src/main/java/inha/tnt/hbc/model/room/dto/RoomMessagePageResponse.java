package inha.tnt.hbc.model.room.dto;

import org.springframework.data.domain.Page;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import inha.tnt.hbc.domain.room.dto.RoomMessageDto;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoomMessagePageResponse {

	private Page<RoomMessageDto> page;

	public static RoomMessagePageResponse of(Page<RoomMessageDto> page) {
		return new RoomMessagePageResponse(page);
	}

}
