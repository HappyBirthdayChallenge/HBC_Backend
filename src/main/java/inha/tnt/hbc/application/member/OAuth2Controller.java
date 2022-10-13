package inha.tnt.hbc.application.member;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import inha.tnt.hbc.model.ResultResponse;
import inha.tnt.hbc.model.member.OAuth2Api;

@RestController
public class OAuth2Controller implements OAuth2Api {

	@Override
	public ResponseEntity<ResultResponse> signin(String provider, String code) {
		return null;
	}

}
