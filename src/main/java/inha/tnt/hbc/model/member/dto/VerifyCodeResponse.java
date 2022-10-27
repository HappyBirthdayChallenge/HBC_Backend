package inha.tnt.hbc.model.member.dto;

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
public class VerifyCodeResponse {

	@ApiModelProperty(value = "인증 키", example = "550e8400-e29b-41d4-a716-446655440000", required = true)
	private String key;

}
