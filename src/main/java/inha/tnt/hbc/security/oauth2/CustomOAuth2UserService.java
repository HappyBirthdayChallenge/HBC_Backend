package inha.tnt.hbc.security.oauth2;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import inha.tnt.hbc.domain.member.entity.Member;
import inha.tnt.hbc.application.member.service.OAuth2Service;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

	private final OAuth2Service oAuth2Service;

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) {
		final OAuth2User oAuth2User = super.loadUser(userRequest);
		final String provider = userRequest.getClientRegistration().getRegistrationId().toUpperCase();
		final OAuth2Attributes oAuth2Attributes = OAuth2Attributes.of(provider, oAuth2User.getAttributes());
		final Member member = oAuth2Service.getMember(oAuth2Attributes);
		return oAuth2Attributes.toOAuth2User(member);
	}

}
