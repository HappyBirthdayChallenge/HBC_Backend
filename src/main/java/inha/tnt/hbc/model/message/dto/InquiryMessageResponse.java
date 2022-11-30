package inha.tnt.hbc.model.message.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import io.swagger.annotations.ApiModelProperty;
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

	@ApiModelProperty(value = "메시지 PK", example = "1")
	private Long messageId;
	private MemberDto member;
	@ApiModelProperty(value = "장식품 유형", example = "DOLL_TYPE1")
	private MessageDecorationTypes decorationType;
	@ApiModelProperty(value = "애니메이션 유형", example = "PARTY_POPPER_TYPE1")
	private AnimationTypes animationType;
	@ApiModelProperty(value = "축하 메시지 내용", example = "생일 축하해!")
	private String content;
	@ApiModelProperty(value = "파일 uri 목록", example = "[url, url]")
	private List<String> fileUris = new ArrayList<>();
	@ApiModelProperty(value = "메시지 작성일시", example = "2022-11-22T13:00:21.6645316")
	private String createAt;
	@ApiModelProperty(value = "메시지 읽음 여부", example = "true")
	private boolean read;
	@ApiModelProperty(value = "메시지 좋아요 여부", example = "false")
	private boolean like;

	public static InquiryMessageResponse of(Member member, Message message) {
		return InquiryMessageResponse.builder()
			.messageId(message.getId())
			.member(MemberDto.of(member))
			.decorationType(message.getDecoration().getType())
			.animationType(message.getAnimation().getType())
			.content(message.getContent())
			.read(message.isRead())
			.like(message.isLike())
			.fileUris(message.getFileUris() == null ? new ArrayList<>() : message.getFileUris())
			.createAt(message.getCreateAt().toString())
			.build();
	}

}
