package inha.tnt.hbc.util;

import static inha.tnt.hbc.model.ErrorCode.*;
import static inha.tnt.hbc.util.Constants.*;
import static inha.tnt.hbc.util.JwtUtils.JwtType.*;
import static java.util.stream.Collectors.*;

import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import inha.tnt.hbc.domain.member.entity.Member;
import inha.tnt.hbc.model.ErrorResponse.FieldError;
import inha.tnt.hbc.security.jwt.JwtAuthenticationToken;
import inha.tnt.hbc.security.jwt.exception.JwtAuthenticationException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtils {

	public static final String CLAIM_AUTHORITIES = "authorities";
	public static final String JWT_TYPE = "Bearer"; // RFC 6750: JWT, OAuth 2.0 token are bearer tokens
	public static final String CLAIM_ISSUER = "isr";
	public static final String CLAIM_PRIMARY_KEY = "id";
	public static final String CLAIM_USERNAME = "uname";
	public static final String CLAIM_NAME = "name";
	public static final String CLAIM_BIRTHDAY = "birth";
	public static final String CLAIM_IMAGE_URL = "img";
	public static final String TOKEN_TYPE = "typ";
	public static final String TOKEN_NAME = "JWT";
	public static final String TOKEN_ISSUER = "hbc";
	public static final int TOKEN_PREFIX_LENGTH = 7;

	@Value("${jwt.valid.access}")
	private long ACCESS_TOKEN_VALIDITY;
	@Value("${jwt.valid.refresh}")
	private long REFRESH_TOKEN_VALIDITY;
	@Value("${jwt.key}")
	private String SECRET_KEY;

	public String extractJwt(String authorizationHeader) {
		validateAuthorizationHeader(authorizationHeader);
		return authorizationHeader.substring(TOKEN_PREFIX_LENGTH);
	}

	public Long getMemberId(JwtAuthenticationToken authenticationToken) {
		final String token = authenticationToken.getToken();
		final Claims claims = getAllClaims(token);
		return claims.get(CLAIM_PRIMARY_KEY, Long.class);
	}

	public Authentication getAuthentication(String token) {
		final Claims claims = getAllClaims(token);
		final List<SimpleGrantedAuthority> authorities = getAuthorities(claims);
		final User principal = new User(claims.getSubject(), EMPTY, authorities);
		return JwtAuthenticationToken.of(token, principal, EMPTY, authorities);
	}

	public String generateAccessToken(OAuth2User oAuth2User) {
		return generateJwt(oAuth2User, ACCESS_TOKEN_VALIDITY, ACCESS_TOKEN.name());
	}

	public String generateRefreshToken(OAuth2User oAuth2User) {
		return generateJwt(oAuth2User, REFRESH_TOKEN_VALIDITY, REFRESH_TOKEN.name());
	}

	public String generateAccessToken(Member member) {
		return generateJwt(member, ACCESS_TOKEN_VALIDITY, ACCESS_TOKEN.name());
	}

	public String generateRefreshToken(Member member) {
		return generateJwt(member, REFRESH_TOKEN_VALIDITY, REFRESH_TOKEN.name());
	}

	@PostConstruct
	protected void init() {
		SECRET_KEY = Base64.getEncoder().encodeToString(SECRET_KEY.getBytes());
	}

	private String generateJwt(long validity, Map<String, Object> claims, String subject) {
		return generateJwt(validity, new HashMap<>(), claims, subject);
	}

	private String generateJwt(OAuth2User oAuth2User, long validity, String subject) {
		final Map<String, Object> attributes = oAuth2User.getAttributes();
		final Map<String, Object> claims = new HashMap<>();
		claims.put(CLAIM_AUTHORITIES, convertToList(oAuth2User.getAuthorities()));
		claims.put(CLAIM_PRIMARY_KEY, attributes.get(CLAIM_PRIMARY_KEY));
		claims.put(CLAIM_USERNAME, attributes.get(CLAIM_USERNAME));
		claims.put(CLAIM_NAME, attributes.get(CLAIM_NAME));
		claims.put(CLAIM_BIRTHDAY, attributes.get(CLAIM_BIRTHDAY));
		claims.put(CLAIM_IMAGE_URL, attributes.get(CLAIM_IMAGE_URL));
		return generateJwt(validity, claims, subject);
	}

	private String generateJwt(Member member, long validity, String subject) {
		final Map<String, Object> claims = new HashMap<>();
		claims.put(CLAIM_AUTHORITIES, member.combineAndGetAuthorities());
		claims.put(CLAIM_PRIMARY_KEY, member.getId());
		claims.put(CLAIM_USERNAME, member.getUsername());
		claims.put(CLAIM_NAME, member.getName());
		claims.put(CLAIM_BIRTHDAY, member.getBirthDate());
		claims.put(CLAIM_IMAGE_URL, member.getImage().getUrl());
		return generateJwt(validity, claims, subject);
	}

	private String generateJwt(long validity, Map<String, Object> headers, Map<String, Object> claims, String subject) {
		final Date now = new Date(System.currentTimeMillis());
		final Date expiration = new Date(now.getTime() + validity);
		headers.put(TOKEN_TYPE, TOKEN_NAME);
		claims.put(CLAIM_ISSUER, TOKEN_ISSUER);
		claims.put(TOKEN_TYPE, JWT_TYPE);
		return Jwts.builder()
			.setHeader(headers)
			.setClaims(claims)
			.setSubject(subject)
			.setIssuedAt(now)
			.setExpiration(expiration)
			.signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()), SignatureAlgorithm.HS512)
			.compact();
	}

	private List<String> convertToList(Collection<? extends GrantedAuthority> authorities) {
		return authorities.stream()
			.map(GrantedAuthority::getAuthority)
			.collect(toList());
	}

	private List<SimpleGrantedAuthority> getAuthorities(Claims claims) {
		final List<String> authorities = (List<String>)claims.get(CLAIM_AUTHORITIES);
		return authorities.stream()
			.map(SimpleGrantedAuthority::new)
			.collect(toList());
	}

	private void validateAuthorizationHeader(String authorizationHeader) {
		if (authorizationHeader == null) {
			throw new JwtAuthenticationException(
				FieldError.of(AUTHORIZATION_HEADER, EMPTY, AUTHORIZATION_HEADER_MISSING));
		} else if (!authorizationHeader.startsWith(JWT_TYPE + SPACE)) {
			throw new JwtAuthenticationException(
				FieldError.of(AUTHORIZATION_HEADER, authorizationHeader, BEARER_PREFIX_MISSING));
		}
	}

	private Claims getAllClaims(String token) {
		return Jwts.parserBuilder()
			.setSigningKey(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
			.build()
			.parseClaimsJws(token)
			.getBody();
	}

	public enum JwtType {

		ACCESS_TOKEN, REFRESH_TOKEN

	}

}
