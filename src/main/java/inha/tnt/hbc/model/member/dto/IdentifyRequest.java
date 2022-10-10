package inha.tnt.hbc.model.member.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

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
public class IdentifyRequest {

	@NotNull
	@Pattern(regexp = "^[A-Za-z\\d]{5,20}$")
	@ApiModelProperty(value = "아이디(영문자 || 숫자)[5, 20]", example = "dkdlel123", required = true)
	private String username;
	@NotNull
	@Email
	@ApiModelProperty(value = "이메일", required = true, example = "example@gmail.com")
	private String email;

}
