package inha.tnt.hbc.domain.member.dto;

import com.querydsl.core.annotations.QueryProjection;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import inha.tnt.hbc.domain.member.entity.Member;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FollowerDto {

	@ApiModelProperty(value = "팔로워 회원 정보")
	private MemberDto follower;
	@ApiModelProperty(value = "팔로우 여부", example = "true")
	private boolean follow = false;

	@QueryProjection
	public FollowerDto(Member member) {
		this.follower = MemberDto.of(member);
	}

	public void raiseFollowFlag() {
		this.follow = true;
	}

}
