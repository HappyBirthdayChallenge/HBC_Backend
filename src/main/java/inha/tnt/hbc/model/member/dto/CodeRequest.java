package inha.tnt.hbc.model.member.dto;

import javax.validation.constraints.NotBlank;
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
public class CodeRequest {

	@NotBlank
	@Pattern(regexp = "^\\d{6}$")
	@ApiModelProperty(value = "인증 코드", required = true, example = "123456")
	private String code;

}
