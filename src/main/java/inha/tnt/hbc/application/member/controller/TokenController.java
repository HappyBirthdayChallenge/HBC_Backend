package inha.tnt.hbc.application.member.controller;

import static inha.tnt.hbc.model.ResultCode.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import inha.tnt.hbc.application.member.service.TokenService;
import inha.tnt.hbc.model.ResultResponse;
import inha.tnt.hbc.model.member.TokenApi;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class TokenController implements TokenApi {

	private final TokenService tokenService;

	@Override
	public ResponseEntity<ResultResponse> check() {
		return ResponseEntity.ok(ResultResponse.of(JWT_VERIFIED));
	}

	@Override
	public ResponseEntity<ResultResponse> reissue(String refreshToken) {
		return ResponseEntity.ok(tokenService.reissueToken(refreshToken));
	}

}
