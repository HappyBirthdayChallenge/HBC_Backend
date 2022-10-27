package inha.tnt.hbc.application.member.controller;

import static inha.tnt.hbc.model.ResultCode.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import inha.tnt.hbc.application.member.service.AuthService;
import inha.tnt.hbc.domain.member.entity.Member;
import inha.tnt.hbc.exception.EntityNotFoundException;
import inha.tnt.hbc.model.ResultResponse;
import inha.tnt.hbc.model.member.AuthApi;
import inha.tnt.hbc.model.member.dto.CodeRequest;
import inha.tnt.hbc.model.member.dto.EmailRequest;
import inha.tnt.hbc.model.member.dto.FindPasswordRequest;
import inha.tnt.hbc.model.member.dto.FindUsernameRequest;
import inha.tnt.hbc.model.member.dto.IdentifyRequest;
import inha.tnt.hbc.model.member.dto.SigninRequest;
import inha.tnt.hbc.model.member.dto.SignupRequest;
import inha.tnt.hbc.model.member.dto.UsernameRequest;
import inha.tnt.hbc.security.jwt.dto.JwtDto;
import inha.tnt.hbc.vo.BirthDate;
import inha.tnt.hbc.vo.Image;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AuthController implements AuthApi {

	private final AuthService authService;

	@Override
	public ResponseEntity<ResultResponse> signin(SigninRequest request) {
		try {
			final JwtDto jwtDto = authService.signin(request.getUsername(), request.getPassword());
			return ResponseEntity.ok(ResultResponse.of(SIGNIN_SUCCESS, jwtDto));
		} catch (EntityNotFoundException e) {
			return ResponseEntity.ok(ResultResponse.of(USERNAME_PASSWORD_INCORRECT));
		}
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

	// TODO: 검증 로직 추가
	@Override
	public ResponseEntity<ResultResponse> signup(SignupRequest request) {
		final Member member = authService.signup(request.getUsername(), request.getPassword(), request.getName(),
			request.getPhone(), request.getBirthDate(), Image.getInitial());
		return ResponseEntity.ok(ResultResponse.of(SIGNUP_SUCCESS, member));
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
