package inha.tnt.hbc.model.member.dto;

import javax.validation.constraints.NotBlank;
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
public class VerifyCodeRequest {

	@NotBlank
	@Pattern(regexp = "^\\d{6}$")
	@ApiModelProperty(value = "인증 코드", required = true, example = "123456")
	private String code;

	@Phone
	@ApiModelProperty(value = "휴대폰 번호", required = true, example = "010-9128-5708")
	private String phone;

}
