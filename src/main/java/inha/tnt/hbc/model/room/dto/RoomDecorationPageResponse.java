package inha.tnt.hbc.model.room.dto;

import java.util.List;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import inha.tnt.hbc.domain.message.dto.MessageDecorationDto;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RoomDecorationPageResponse {

	private List<MessageDecorationDto> foods;
	private List<MessageDecorationDto> drinks;
	private List<MessageDecorationDto> photos;
	private List<MessageDecorationDto> dolls;
	private List<MessageDecorationDto> gifts;
	private boolean isFirst;
	private boolean isLast;
	private boolean isEmpty;
	private int totalPages;

}
