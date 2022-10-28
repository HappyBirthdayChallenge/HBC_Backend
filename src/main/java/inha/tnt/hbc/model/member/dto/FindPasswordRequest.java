package inha.tnt.hbc.model.member.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import inha.tnt.hbc.annotation.Phone;
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
public class FindPasswordRequest {

	@NotNull
	@Pattern(regexp = "^[A-Za-z\\d]{5,20}$")
	@ApiModelProperty(value = "아이디(영문자 || 숫자)[5, 20]", example = "dkdlel123", required = true)
	private String username;
	@NotNull
	@Pattern(regexp = "^[가-힣A-Za-z\\d]{2,20}$")
	@ApiModelProperty(value = "이름(한글 || 영문자 || 숫자)[2, 20]", example = "손흥민", required = true)
	private String name;
	@Phone
	@ApiModelProperty(value = "휴대폰 번호", required = true, example = "010-9128-5708")
	private String phone;
	@NotNull
	@Pattern(regexp = "^[A-Za-z\\d]{8}-[A-Za-z\\d]{4}-[A-Za-z\\d]{4}-[A-Za-z\\d]{4}-[A-Za-z\\d]{12}$")
	@ApiModelProperty(value = "인증 키", example = "550e8400-e29b-41d4-a716-446655440000", required = true)
	private String key;

}
