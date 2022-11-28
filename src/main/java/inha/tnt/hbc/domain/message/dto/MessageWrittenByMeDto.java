package inha.tnt.hbc.domain.message.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.querydsl.core.annotations.QueryProjection;

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

	private Long messageId;
	private MessageDecorationTypes decorationType;
	private Long roomId;
	private MemberDto roomOwner;

	@QueryProjection
	public MessageWrittenByMeDto(Message message) {
		this.messageId = message.getId();
		this.decorationType = message.getDecoration().getType();
		this.roomId = message.getRoom().getId();
		this.roomOwner = MemberDto.of(message.getRoom().getMember());
	}

}
