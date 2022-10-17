package inha.tnt.hbc.application.member.controller;

import static inha.tnt.hbc.model.ResultCode.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import inha.tnt.hbc.application.member.service.OAuth2Service;
import inha.tnt.hbc.model.ResultResponse;
import inha.tnt.hbc.model.member.OAuth2Api;
import inha.tnt.hbc.security.jwt.dto.JwtDto;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class OAuth2Controller implements OAuth2Api {

	private final OAuth2Service oAuth2Service;

	@Override
	public ResponseEntity<ResultResponse> signin(String provider, String token) {
		final JwtDto jwtDto = oAuth2Service.signin(provider, token);
		return ResponseEntity.ok(ResultResponse.of(SIGNIN_SUCCESS, jwtDto));
	}

}
