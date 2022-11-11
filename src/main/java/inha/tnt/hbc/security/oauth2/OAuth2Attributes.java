package inha.tnt.hbc.security.oauth2;

import static inha.tnt.hbc.domain.member.entity.oauth2.OAuth2Providers.*;
import static inha.tnt.hbc.util.JwtUtils.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import inha.tnt.hbc.domain.member.entity.Member;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class OAuth2Attributes {

	public final static String NAME_ATTRIBUTE_KEY = "nameAttributeKey";
	public final static String PROVIDER_KEY = "provider";
	public static final String USERNAME_KEY = "uname";
	public static final String NAME_KEY = "name";
	public static final String BIRTHDAY_KEY = "birth";
	public static final String IMAGE_URL_KEY = "img";

	private Map<String, Object> attributes;
	private String provider;
	private Long attributeKey;
	private String name;
	private String imageUrl;

	public static OAuth2Attributes of(String provider, Map<String, Object> attributes) {
		if (provider.equals(KAKAO.name())) {
			return kakao(attributes);
		} else {
			throw new RuntimeException();
		}
	}

	/**
	 * <a href="https://developers.kakao.com/docs/latest/ko/kakaologin/rest-api#req-user-info-response">Reference</a>
	 */
	private static OAuth2Attributes kakao(Map<String, Object> attributes) {
		final Long id = (Long)attributes.get("id");
		final Map<String, Object> kakaoAccount = (Map<String, Object>)attributes.get("kakao_account");
		final Map<String, Object> profile = (Map<String, Object>)kakaoAccount.get("profile");
		final String name = (String)kakaoAccount.get("nickname");
		final String imageUrl = (String)profile.get("profile_image_url");

		return OAuth2Attributes.builder()
			.attributes(attributes)
			.attributeKey(id)
			.name(name)
			.imageUrl(imageUrl)
			.provider(KAKAO.name())
			.build();
	}

	public OAuth2User toOAuth2User(Member member) {
		final List<SimpleGrantedAuthority> authorities = member.combineAndGetAuthorities().stream()
			.map(SimpleGrantedAuthority::new)
			.collect(Collectors.toList());

		final Map<String, Object> attributes = toMap();
		attributes.put(CLAIM_PRIMARY_KEY, member.getId());
		attributes.put(USERNAME_KEY, member.getUsername());
		attributes.put(BIRTHDAY_KEY, member.getBirthDate());
		return new DefaultOAuth2User(authorities, attributes, NAME_ATTRIBUTE_KEY);
	}

	private Map<String, Object> toMap() {
		final Map<String, Object> map = new HashMap<>();
		map.put(NAME_ATTRIBUTE_KEY, this.attributeKey);
		map.put(PROVIDER_KEY, this.provider);
		map.put(IMAGE_URL_KEY, this.imageUrl);
		map.put(NAME_KEY, this.name);
		return map;
	}

}
