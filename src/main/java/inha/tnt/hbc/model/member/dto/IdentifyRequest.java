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
	@Pattern(regexp = "^[가-힣A-Za-z\\d]{2,20}$")
	@ApiModelProperty(value = "이름(한글 || 영문자 || 숫자)[2, 20]", example = "손흥민", required = true)
	private String name;
	@NotNull
	@Email
	@ApiModelProperty(value = "이메일", required = true, example = "example@gmail.com")
	private String email;

}
