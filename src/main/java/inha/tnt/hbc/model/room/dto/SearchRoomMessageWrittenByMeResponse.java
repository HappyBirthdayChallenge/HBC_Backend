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

	@ApiModelProperty(value = "메시지 찾음 여부", example = "true")
	private boolean found;
	@ApiModelProperty(value = "파티룸 페이지", example = "1")
	private Integer page;
	@ApiModelProperty(value = "파티룸 페이지 인덱스", example = "2")
	private Integer index;

	public static SearchRoomMessageWrittenByMeResponse of(Integer page, Integer index, boolean found) {
		return SearchRoomMessageWrittenByMeResponse
			.builder()
			.page(page)
			.index(index)
			.found(found)
			.build();
	}

}
