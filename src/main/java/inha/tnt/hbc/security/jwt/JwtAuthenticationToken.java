package inha.tnt.hbc.security.jwt;

import java.util.Collection;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import lombok.Getter;

@Getter
public class JwtAuthenticationToken extends UsernamePasswordAuthenticationToken {

	private final String token;

	private JwtAuthenticationToken(String token, Object principal, Object credentials,
		Collection<? extends GrantedAuthority> authorities) {
		super(principal, credentials, authorities);
		this.token = token;
	}

	public static JwtAuthenticationToken of(String token, Object principal, Object credentials,
		Collection<? extends GrantedAuthority> authorities) {
		return new JwtAuthenticationToken(token, principal, credentials, authorities);
	}

	public static JwtAuthenticationToken of(String token) {
		return new JwtAuthenticationToken(token, null, null, null);
	}

}
