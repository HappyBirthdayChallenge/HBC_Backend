package inha.tnt.hbc.model.room.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SearchRoomMessageWrittenByMeResponse {

	@ApiModelProperty(value = "파티룸 페이지", example = "1")
	private int page;
	@ApiModelProperty(value = "파티룸 페이지 인덱스", example = "2")
	private int index;

	public static SearchRoomMessageWrittenByMeResponse of(int page, int index) {
		return new SearchRoomMessageWrittenByMeResponse(page, index);
	}

}
