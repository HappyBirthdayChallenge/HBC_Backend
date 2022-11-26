package inha.tnt.hbc.domain.message.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import inha.tnt.hbc.domain.message.entity.Decoration;
import inha.tnt.hbc.domain.message.entity.MessageDecorationTypes;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class MessageDecorationDto {

	@ApiModelProperty(value = "메시지 PK", example = "1")
	private Long messageId;
	@ApiModelProperty(value = "장식품 유형", example = "DOLL_TYPE1")
	private MessageDecorationTypes decorationType;

	public static MessageDecorationDto of(Decoration decoration) {
		return MessageDecorationDto.builder()
			.messageId(decoration.getMessage().getId())
			.decorationType(decoration.getType())
			.build();
	}

	public static List<MessageDecorationDto> of(List<Decoration> decorations) {
		return decorations.stream()
			.map(MessageDecorationDto::of)
			.collect(Collectors.toList());
	}

}
