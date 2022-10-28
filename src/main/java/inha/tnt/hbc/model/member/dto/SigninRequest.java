package inha.tnt.hbc.model.member.dto;

import inha.tnt.hbc.annotation.Password;
import inha.tnt.hbc.annotation.Username;
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
public class SigninRequest {

	@Username
	@ApiModelProperty(value = "아이디(영문자 || 숫자)[5, 20]", example = "dkdlel123", required = true)
	private String username;
	@Password
	@ApiModelProperty(value = "비밀번호(영문자 && 숫자 && 특수문자)[10, 20]", example = "qlalfqjsgh1@", required = true)
	private String password;

}
