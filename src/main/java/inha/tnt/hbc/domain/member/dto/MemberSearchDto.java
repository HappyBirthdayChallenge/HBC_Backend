package inha.tnt.hbc.domain.member.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import inha.tnt.hbc.domain.member.entity.Member;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberSearchDto {

	private MemberDto member;
	@ApiModelProperty(value = "팔로우 여부", example = "true")
	private boolean follow = false;

	public static MemberSearchDto of(Member member, boolean isFollowing) {
		return new MemberSearchDto(MemberDto.of(member), isFollowing);
	}

}
