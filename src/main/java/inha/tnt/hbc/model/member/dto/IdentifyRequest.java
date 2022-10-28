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
public class IdentifyRequest {

	@NotNull
	@Pattern(regexp = "^[가-힣A-Za-z\\d]{2,20}$")
	@ApiModelProperty(value = "이름(한글 || 영문자 || 숫자)[2, 20]", example = "손흥민", required = true)
	private String name;
	@Phone
	@ApiModelProperty(value = "휴대폰 번호", required = true, example = "010-9128-5708")
	private String phone;

}
