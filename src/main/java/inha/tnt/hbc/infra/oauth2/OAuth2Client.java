package inha.tnt.hbc.infra.oauth2;

import org.springframework.stereotype.Component;

import inha.tnt.hbc.domain.member.entity.oauth2.OAuth2Providers;
import inha.tnt.hbc.infra.oauth2.kakao.KakaoOAuth2Client;
import inha.tnt.hbc.security.oauth2.OAuth2Attributes;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OAuth2Client {

	private final KakaoOAuth2Client kakaoOAuth2Client;

	public OAuth2Attributes getUserInfo(OAuth2Providers provider, String token) {
		if (provider.equals(OAuth2Providers.KAKAO)) {
			return kakaoOAuth2Client.getUserInfo(token);
		} else {
			throw new RuntimeException();
		}
	}

}
