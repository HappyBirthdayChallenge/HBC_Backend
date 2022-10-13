package inha.tnt.hbc.security.oauth2.exception;

import static inha.tnt.hbc.model.ErrorCode.*;

import inha.tnt.hbc.security.exception.CustomAuthenticationException;

public class UnsupportedPlatformSignInException extends CustomAuthenticationException {

	public UnsupportedPlatformSignInException() {
		super(UNSUPPORTED_PLATFORM_SIGN_IN);
	}

}
