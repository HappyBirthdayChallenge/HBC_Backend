package inha.tnt.hbc.infra.oauth2.kakao.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import inha.tnt.hbc.domain.member.entity.oauth2.OAuth2Provider;
import inha.tnt.hbc.security.oauth2.OAuth2Attributes;
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
public class KakaoUserInfo {

	private Long id;
	private KakaoAccount kakaoAccount;

	public OAuth2Attributes convert() {
		return OAuth2Attributes.builder()
			.attributeKey(this.id)
			.email(this.kakaoAccount.email)
			.name(this.kakaoAccount.profile.nickname)
			.imageUrl(this.kakaoAccount.profile.profileImageUrl)
			.provider(OAuth2Provider.KAKAO.name())
			.build();
	}

	@Getter
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
	private static class KakaoAccount {

		private Profile profile;
		private String email;

	}

	@Getter
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
	private static class Profile {

		private String nickname;
		private String profileImageUrl;

	}

}
