package inha.tnt.hbc.model.member.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

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
public class EmailRequest {

	@NotNull
	@Email
	@ApiModelProperty(value = "이메일", required = true, example = "example@gmail.com")
	private String email;

}
