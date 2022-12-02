package inha.tnt.hbc.domain.member.dto;

import com.querydsl.core.annotations.QueryProjection;

import inha.tnt.hbc.domain.member.entity.Member;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FollowingDto {

	@ApiModelProperty(value = "팔로잉 회원 정보")
	private MemberDto following;

	@QueryProjection
	public FollowingDto(Member member) {
		this.following = MemberDto.of(member);
	}

}
