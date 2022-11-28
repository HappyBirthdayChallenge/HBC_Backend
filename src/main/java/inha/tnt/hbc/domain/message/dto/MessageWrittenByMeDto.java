package inha.tnt.hbc.domain.message.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.querydsl.core.annotations.QueryProjection;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import inha.tnt.hbc.domain.member.dto.MemberDto;
import inha.tnt.hbc.domain.message.entity.Message;
import inha.tnt.hbc.domain.message.entity.MessageDecorationTypes;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class MessageWrittenByMeDto {

	@ApiModelProperty(value = "메시지 PK", example = "1")
	private Long messageId;
	@ApiModelProperty(value = "메시지 장식품 유형", example = "DOLL_TYPE1")
	private MessageDecorationTypes decorationType;
	@ApiModelProperty(value = "파티룸 PK", example = "1")
	private Long roomId;
	@ApiModelProperty(value = "파티룸 주인 정보")
	private MemberDto roomOwner;

	@QueryProjection
	public MessageWrittenByMeDto(Message message) {
		this.messageId = message.getId();
		this.decorationType = message.getDecoration().getType();
		this.roomId = message.getRoom().getId();
		this.roomOwner = MemberDto.of(message.getRoom().getMember());
	}

}
