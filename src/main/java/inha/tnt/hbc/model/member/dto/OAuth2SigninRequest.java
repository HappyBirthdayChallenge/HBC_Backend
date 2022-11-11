package inha.tnt.hbc.model.member.dto;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import inha.tnt.hbc.domain.member.entity.oauth2.OAuth2Providers;
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
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class OAuth2SigninRequest {

	@ApiModelProperty(value = "OAuth2 플랫폼명", example = "KAKAO", required = true)
	private OAuth2Providers provider;
	@Size(max = 2000)
	@ApiModelProperty(value = "OAuth2 인가 토큰", example = "7POZ3TGhuFkBtaEj8WwFLB7Qm8WZvbSaf-lU5_C4CinJXgAAAYPWM1g9", required = true)
	private String oauth2Token;
	@Size(max = 2000)
	@ApiModelProperty(value = "FCM 토큰", example = "c2aK9KHmw8E:APA91bF7...", required = true)
	private String fcmToken;

}
