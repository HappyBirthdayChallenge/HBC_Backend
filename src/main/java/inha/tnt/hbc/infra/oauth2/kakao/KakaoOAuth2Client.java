package inha.tnt.hbc.infra.oauth2.kakao;

import static inha.tnt.hbc.util.Constants.*;
import static org.springframework.http.HttpMethod.*;
import static org.springframework.http.MediaType.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import inha.tnt.hbc.infra.oauth2.kakao.dto.KakaoUserInfo;
import inha.tnt.hbc.security.oauth2.OAuth2Attributes;
import inha.tnt.hbc.util.JwtUtils;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class KakaoOAuth2Client {

	private final RestTemplate restTemplate;
	@Value("${spring.security.oauth2.client.provider.kakao.user-info-uri}")
	private String USER_INFO_URI;

	public OAuth2Attributes getUserInfo(String token) {
		final HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.set(CONTENT_TYPE_HEADER, APPLICATION_FORM_URLENCODED_VALUE);
		httpHeaders.set(AUTHORIZATION_HEADER, JwtUtils.JWT_TYPE + SPACE + token);
		final HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(null, httpHeaders);
		final ResponseEntity<KakaoUserInfo> responseEntity = restTemplate.exchange(USER_INFO_URI, GET, requestEntity,
			KakaoUserInfo.class);
		if (responseEntity.getBody() == null) {
			throw new RuntimeException();
		}
		final KakaoUserInfo kakaoUserInfo = responseEntity.getBody();
		return kakaoUserInfo.convert();
	}

}
