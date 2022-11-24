package inha.tnt.hbc.model.message.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import inha.tnt.hbc.domain.message.entity.AnimationTypes;
import inha.tnt.hbc.domain.message.entity.MessageDecorationTypes;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonNaming(SnakeCaseStrategy.class)
public class MessageRequest {

	@NotNull
	@ApiModelProperty(value = "메시지 PK", example = "1", required = true)
	private Long messageId;
	@NotNull
	@ApiModelProperty(value = "메시지 장식품", example = "FOOD_TYPE1", required = true)
	private MessageDecorationTypes decorationType;
	@NotNull
	@ApiModelProperty(value = "메시지 애니메이션", example = "PARTY_POPPER_TYPE1", required = true)
	private AnimationTypes animationType;
	@Size(min = 2, max = 3000)
	@ApiModelProperty(value = "메시지 내용", example = "생일 축하해!", required = true)
	private String content;
	@Size(max = 10)
	@ApiModelProperty(value = "메시지 파일 ID 목록", example = "[1, 2]", required = true)
	private List<Long> fileIds = new ArrayList<>();

}
