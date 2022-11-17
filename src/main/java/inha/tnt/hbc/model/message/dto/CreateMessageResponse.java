package inha.tnt.hbc.model.message.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

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
@JsonNaming(SnakeCaseStrategy.class)
public class CreateMessageResponse {

	@ApiModelProperty(value = "메시지 PK", example = "1")
	private Long messageId;

	public static CreateMessageResponse of(Long messageId) {
		return new CreateMessageResponse(messageId);
	}

}
