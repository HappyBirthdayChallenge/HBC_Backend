package inha.tnt.hbc.model.message.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import inha.tnt.hbc.domain.member.dto.MemberDto;
import inha.tnt.hbc.domain.member.entity.Member;
import inha.tnt.hbc.domain.message.entity.AnimationTypes;
import inha.tnt.hbc.domain.message.entity.Message;
import inha.tnt.hbc.domain.message.entity.MessageDecorationTypes;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonNaming(SnakeCaseStrategy.class)
public class InquiryMessageResponse {

	private Long messageId;
	private MemberDto member;
	private MessageDecorationTypes decorationType;
	private AnimationTypes animationType;
	private String content;
	private List<String> fileUris = new ArrayList<>();
	private String createAt;

	public static InquiryMessageResponse of(Member member, Message message) {
		return InquiryMessageResponse.builder()
			.messageId(message.getId())
			.member(MemberDto.of(member))
			.decorationType(message.getDecoration().getType())
			.animationType(message.getAnimation().getType())
			.content(message.getContent())
			.fileUris(message.getFileUris())
			.createAt(message.getCreateAt().toString())
			.build();
	}

}
