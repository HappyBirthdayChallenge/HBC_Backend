package inha.tnt.hbc.security.jwt;

import static inha.tnt.hbc.model.ErrorCode.*;
import static inha.tnt.hbc.util.Constants.*;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import inha.tnt.hbc.model.ErrorResponse.FieldError;
import inha.tnt.hbc.security.jwt.exception.JwtAuthenticationException;
import inha.tnt.hbc.util.JwtUtils;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {

	private final JwtUtils jwtUtils;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		final String token = ((JwtAuthenticationToken)authentication).getToken();
		try {
			return jwtUtils.getAuthentication(token);
		} catch (MalformedJwtException e) {
			throw new JwtAuthenticationException(FieldError.of(AUTHORIZATION_HEADER, token, JWT_MALFORMED));
		} catch (UnsupportedJwtException e) {
			throw new JwtAuthenticationException(FieldError.of(AUTHORIZATION_HEADER, token, JWT_UNSUPPORTED));
		} catch (SignatureException e) {
			throw new JwtAuthenticationException(FieldError.of(AUTHORIZATION_HEADER, token, JWT_SIGNATURE_INVALID));
		} catch (ExpiredJwtException e) {
			throw new JwtAuthenticationException(FieldError.of(AUTHORIZATION_HEADER, token, JWT_EXPIRED));
		} catch (JwtException e) {
			throw new JwtAuthenticationException(FieldError.of(AUTHORIZATION_HEADER, token, JWT_INVALID));
		}
	}

	@Override
	public boolean supports(Class<?> aClass) {
		return JwtAuthenticationToken.class.isAssignableFrom(aClass);
	}

}
