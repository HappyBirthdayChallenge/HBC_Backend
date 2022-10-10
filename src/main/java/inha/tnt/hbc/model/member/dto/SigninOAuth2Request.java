package inha.tnt.hbc.model.member.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import inha.tnt.hbc.security.oauth2.OAuth2Provider;
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
public class SigninOAuth2Request {

	@NotBlank
	@Size(max = 100)
	@ApiModelProperty(value = "OAuth2 인가 코드", required = true, example = "CKWmhCwdgVeZwZ2YNQAccr5aW1S6xItOGKkiCEepCj1zmgAAAYPDeqSi")
	private String code;

	@NotNull
	@ApiModelProperty(value = "OAuth2 제공자", required = true, example = "KAKAO")
	private OAuth2Provider provider;

}
