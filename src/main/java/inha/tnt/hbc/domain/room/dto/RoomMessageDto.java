package inha.tnt.hbc.domain.room.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.querydsl.core.annotations.QueryProjection;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import inha.tnt.hbc.domain.member.dto.MemberDto;
import inha.tnt.hbc.domain.message.entity.Message;
import inha.tnt.hbc.domain.message.entity.MessageDecorationTypes;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RoomMessageDto {

	@ApiModelProperty(value = "메시지 PK", example = "1")
	private Long messageId;
	@ApiModelProperty(value = "메시지 장식품 유형", example = "DOLL_TYPE1")
	private MessageDecorationTypes decorationType;
	@ApiModelProperty(value = "메시지 작성자 정보")
	private MemberDto writer;
	@ApiModelProperty(value = "메시지 작성일시", example = "2022-11-22T13:00:21.6645316")
	private String createAt;

	@Builder
	@QueryProjection
	public RoomMessageDto(Message message) {
		this.messageId = message.getId();
		this.decorationType = message.getDecoration().getType();
		this.writer = MemberDto.of(message.getMember());
		this.createAt = message.getCreateAt().toString();
	}

}
