package inha.tnt.hbc.application.member;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import inha.tnt.hbc.model.ResultResponse;
import inha.tnt.hbc.model.member.AuthApi;
import inha.tnt.hbc.model.member.dto.EmailRequest;
import inha.tnt.hbc.model.member.dto.CodeRequest;
import inha.tnt.hbc.model.member.dto.FindPasswordRequest;
import inha.tnt.hbc.model.member.dto.FindUsernameRequest;
import inha.tnt.hbc.model.member.dto.IdentifyRequest;
import inha.tnt.hbc.model.member.dto.SigninOAuth2Request;
import inha.tnt.hbc.model.member.dto.SignupRequest;
import inha.tnt.hbc.model.member.dto.UsernameRequest;
import inha.tnt.hbc.model.member.dto.SigninRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AuthController implements AuthApi {

	@Override
	public ResponseEntity<ResultResponse> signin(SigninRequest request) {
		return null;
	}

	@Override
	public ResponseEntity<ResultResponse> signinOAuth2(SigninOAuth2Request request) {
		return null;
	}

	@Override
	public ResponseEntity<ResultResponse> reissue(String refreshToken) {
		return null;
	}

	@Override
	public ResponseEntity<ResultResponse> checkUsername(UsernameRequest request) {
		return null;
	}

	@Override
	public ResponseEntity<ResultResponse> checkEmail(EmailRequest request) {
		return null;
	}

	@Override
	public ResponseEntity<ResultResponse> sendCodeToEmail(EmailRequest request) {
		return null;
	}

	@Override
	public ResponseEntity<ResultResponse> verifyCode(CodeRequest request) {
		return null;
	}

	@Override
	public ResponseEntity<ResultResponse> signup(SignupRequest request) {
		return null;
	}

	@Override
	public ResponseEntity<ResultResponse> identify(IdentifyRequest request) {
		return null;
	}

	@Override
	public ResponseEntity<ResultResponse> findUsername(FindUsernameRequest request) {
		return null;
	}

	@Override
	public ResponseEntity<ResultResponse> findPassword(FindPasswordRequest request) {
		return null;
	}

}
