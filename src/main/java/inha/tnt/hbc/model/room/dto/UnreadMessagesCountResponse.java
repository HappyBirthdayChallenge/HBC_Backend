package inha.tnt.hbc.model.room.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UnreadMessagesCountResponse {

	@ApiModelProperty(value = "읽지 않은 메시지 개수", example = "10")
	private int count;

	public static UnreadMessagesCountResponse of(int count) {
		return new UnreadMessagesCountResponse(count);
	}

}
