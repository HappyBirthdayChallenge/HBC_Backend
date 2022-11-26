package inha.tnt.hbc.model.room.dto;

import java.util.List;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import io.swagger.annotations.ApiModelProperty;
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

	@ApiModelProperty(value = "음식 장식품 목록")
	private List<MessageDecorationDto> foods;
	@ApiModelProperty(value = "음료 장식품 목록")
	private List<MessageDecorationDto> drinks;
	@ApiModelProperty(value = "사진 장식품 목록")
	private List<MessageDecorationDto> photos;
	@ApiModelProperty(value = "인형 장식품 목록")
	private List<MessageDecorationDto> dolls;
	@ApiModelProperty(value = "선물 장식품 목록")
	private List<MessageDecorationDto> gifts;
	@ApiModelProperty(value = "첫 번째 페이지 여부", example = "false")
	private boolean isFirst;
	@ApiModelProperty(value = "마지막 번째 페이지 여부", example = "true")
	private boolean isLast;
	@ApiModelProperty(value = "빈 페이지 여부", example = "true")
	private boolean isEmpty;
	@ApiModelProperty(value = "총 페이지 개수", example = "10")
	private int totalPages;
	@ApiModelProperty(value = "총 장식품 개수", example = "40")
	private int totalElements;

}
