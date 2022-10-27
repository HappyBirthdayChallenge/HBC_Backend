package inha.tnt.hbc.domain.member.dto;

import com.querydsl.core.annotations.QueryProjection;

import inha.tnt.hbc.vo.BirthDate;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FriendDto {

	@ApiModelProperty(value = "회원 정보")
	private MemberDto member;

	@QueryProjection
	public FriendDto(Long memberId, String memberName, String memberUsername, String memberImageUrl,
		BirthDate memberBirthDate) {
		this.member = MemberDto.builder()
			.id(memberId)
			.name(memberName)
			.username(memberUsername)
			.imageUrl(memberImageUrl)
			.birthDate(memberBirthDate)
			.build();
	}

}