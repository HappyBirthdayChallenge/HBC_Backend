package inha.tnt.hbc.security.oauth2;

import static inha.tnt.hbc.domain.member.entity.oauth2.SnsProvider.*;
import static inha.tnt.hbc.util.Constants.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import inha.tnt.hbc.domain.member.entity.Member;
import inha.tnt.hbc.security.oauth2.exception.UnsupportedPlatformSignInException;
import inha.tnt.hbc.vo.BirthDate;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class OAuth2Attributes {

	public final static String PRIMARY_KEY = "pk";
	public final static String NAME_ATTRIBUTE_KEY = "nameAttributeKey";
	public final static String PROVIDER_KEY = "provider";
	public final static String EMAIL_KEY = "email";
	public final static String NAME_KEY = "name";
	public final static String BIRTH_DATE_KEY = "birthDate";
	public final static String IMAGE_URL_KEY = "imageUrl";

	private final static int MM = 2;
	private final static int DD = 2;

	private Map<String, Object> attributes;
	private String provider;
	private Long attributeKey;
	private String email;
	private String name;
	private BirthDate birthDate;
	private String imageUrl;

	public static OAuth2Attributes of(String provider, Map<String, Object> attributes) {
		if (provider.equals(KAKAO.name())) {
			return kakao(attributes);
		} else {
			throw new UnsupportedPlatformSignInException();
		}
	}

	/**
	 * <a href="https://developers.kakao.com/docs/latest/ko/kakaologin/rest-api#req-user-info-response">Reference</a>
	 */
	private static OAuth2Attributes kakao(Map<String, Object> attributes) {
		final Long id = (Long)attributes.get("id");

		final Map<String, Object> kakaoAccount = (Map<String, Object>)attributes.get("kakao_account");
		final String name = (String)kakaoAccount.get("name");
		final String email = (String)kakaoAccount.get("email");
		final String birthYear = (String)kakaoAccount.get("birthyear");
		final String birthDay = (String)kakaoAccount.get("birthday");
		final String birthType = (String)kakaoAccount.get("birthday_type");
		final BirthDate birthDate = BirthDate.builder()
			.year(Integer.valueOf(birthYear))
			.month(Integer.valueOf(birthDay.substring(0, MM)))
			.date(Integer.valueOf(birthDay.substring(DD)))
			.type(BirthDate.DateType.valueOf(birthType))
			.build();

		final Map<String, Object> profile = (Map<String, Object>)kakaoAccount.get("profile");
		final String imageUrl = (String)profile.get("profile_image_url");

		return OAuth2Attributes.builder()
			.attributeKey(id)
			.attributes(attributes)
			.birthDate(birthDate)
			.email(email)
			.name(name)
			.imageUrl(imageUrl)
			.provider(KAKAO.name())
			.build();
	}

	public OAuth2User toOAuth2User(Member member) {
		final List<SimpleGrantedAuthority> authorities = Arrays.stream(member.getAuthorities().split(COMMA))
			.map(SimpleGrantedAuthority::new)
			.collect(Collectors.toList());

		final Map<String, Object> attributes = toMap();
		attributes.put(PRIMARY_KEY, member.getId());

		return new DefaultOAuth2User(authorities, attributes, NAME_ATTRIBUTE_KEY);
	}

	private Map<String, Object> toMap() {
		final Map<String, Object> map = new HashMap<>();
		map.put(NAME_ATTRIBUTE_KEY, this.attributeKey);
		map.put(PROVIDER_KEY, this.provider);
		map.put(IMAGE_URL_KEY, this.imageUrl);
		map.put(EMAIL_KEY, this.email);
		map.put(BIRTH_DATE_KEY, this.birthDate);
		map.put(NAME_KEY, this.name);
		return map;
	}

}
