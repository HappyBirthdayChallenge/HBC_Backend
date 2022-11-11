package inha.tnt.hbc.domain.member.dto;

import com.querydsl.core.annotations.QueryProjection;

import inha.tnt.hbc.domain.member.entity.Member;
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
	public FriendDto(Member member) {
		this.member = MemberDto.builder()
			.id(member.getId())
			.name(member.getName())
			.username(member.getUsername())
			.imageUrl(member.getImageUri())
			.birthDate(member.getBirthDate())
			.build();
	}

}
