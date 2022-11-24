package inha.tnt.hbc.domain.member.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import inha.tnt.hbc.domain.member.entity.Member;
import inha.tnt.hbc.domain.member.vo.BirthDate;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class MemberDto {

	@ApiModelProperty(value = "회원 PK", example = "1")
	private Long id;
	@ApiModelProperty(value = "회원 이름", example = "손흥민")
	private String name;
	@ApiModelProperty(value = "회원 아이디", example = "dkdlel123")
	private String username;
	@ApiModelProperty(value = "회원 프로밀 이미지 url", example = "https://hbc-bucket.s3.ap-northeast-2.amazonaws.com/member/550e8400-e29b-41d4-a716-446655440000_account_circle.png")
	private String imageUrl;
	@ApiModelProperty(value = "회원 생일")
	private BirthDate birthDate;

	public static MemberDto of(Member member) {
		return MemberDto.builder()
			.id(member.getId())
			.name(member.getName())
			.username(member.getUsername())
			.imageUrl(member.getImageUri())
			.birthDate(member.getBirthDate())
			.build();
	}

}
