package inha.tnt.hbc.model.member.dto;

import java.util.List;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
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
@JsonNaming(SnakeCaseStrategy.class)
public class MyInfoResponse {

	@ApiModelProperty(value = "회원 PK", example = "1", position = 0)
	private Long id;
	@ApiModelProperty(value = "아이디", example = "dkdlel123", position = 1)
	private String username;
	@ApiModelProperty(value = "이름", example = "손흥민", position = 2)
	private String name;
	@ApiModelProperty(value = "휴대폰", example = "010-1234-1234", position = 3)
	private String phone;
	@ApiModelProperty(value = "프로필 이미지 url", example = "https://~~.png", position = 4)
	private String imageUrl;
	@ApiModelProperty(position = 5)
	private BirthDate birthDate;
	@ApiModelProperty(value = "권한", example = "[ROLE_USER, ROLE_ADMIN]", position = 6)
	private List<String> authorities;

	public static MyInfoResponse of(Member member) {
		return MyInfoResponse.builder()
			.id(member.getId())
			.birthDate(member.getBirthDate())
			.name(member.getName())
			.username(member.getUsername())
			.phone(member.getPhone())
			.imageUrl(member.getImageUri())
			.authorities(member.combineAndGetAuthorities())
			.build();
	}

}
