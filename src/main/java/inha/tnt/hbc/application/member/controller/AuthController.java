package inha.tnt.hbc.application.member.controller;

import static inha.tnt.hbc.domain.member.service.IdentityVerificationService.IdentityVerificationTypes.*;
import static inha.tnt.hbc.model.ResultCode.*;
import static inha.tnt.hbc.util.Constants.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import inha.tnt.hbc.application.member.service.AuthService;
import inha.tnt.hbc.domain.member.service.IdentityVerificationService;
import inha.tnt.hbc.infra.sms.SMSClient;
import inha.tnt.hbc.model.ResultResponse;
import inha.tnt.hbc.model.member.AuthApi;
import inha.tnt.hbc.model.member.dto.FindPasswordRequest;
import inha.tnt.hbc.model.member.dto.FindUsernameRequest;
import inha.tnt.hbc.model.member.dto.IdentifyRequest;
import inha.tnt.hbc.model.member.dto.PhoneRequest;
import inha.tnt.hbc.model.member.dto.SigninRequest;
import inha.tnt.hbc.model.member.dto.SignupRequest;
import inha.tnt.hbc.model.member.dto.UsernameRequest;
import inha.tnt.hbc.model.member.dto.VerifyCodeRequest;
import inha.tnt.hbc.model.member.dto.VerifyCodeResponse;
import inha.tnt.hbc.util.RandomUtils;
import inha.tnt.hbc.vo.Image;
import lombok.RequiredArgsConstructor;

// TODO: 비즈니스 로직 모두 service단에서 호출 후, 응답 모델 반환받아서 그대로 return하기
@RestController
@RequiredArgsConstructor
public class AuthController implements AuthApi {

	private final AuthService authService;
	private final IdentityVerificationService identityVerificationService;
	private final SMSClient smsClient;

	@Override
	public ResponseEntity<ResultResponse> signin(SigninRequest request) {
		return ResponseEntity.ok(authService.signin(request.getUsername(), request.getPassword()));
	}

	@Override
	public ResponseEntity<ResultResponse> reissue(String refreshToken) {
		return ResponseEntity.ok(authService.reissueToken(refreshToken));
	}

	@Override
	public ResponseEntity<ResultResponse> checkUsername(UsernameRequest request) {
		return ResponseEntity.ok(authService.checkUsername(request.getUsername()));
	}

	@Override
	public ResponseEntity<ResultResponse> checkPhone(PhoneRequest request) {
		return ResponseEntity.ok(authService.checkPhone(request.getPhone()));
	}

	@Override
	public ResponseEntity<ResultResponse> sendCodeBySms(PhoneRequest request) {
		final String code = RandomUtils.generateNumber(AUTH_CODE_LENGTH);
		identityVerificationService.saveAuthCode(code, request.getPhone(), request.getType());
		smsClient.sendSMS(request.getPhone(), String.format(AUTH_CODE_MESSAGE, code));
		return ResponseEntity.ok(ResultResponse.of(SEND_CODE_SUCCESS));
	}

	@Override
	public ResponseEntity<ResultResponse> verifyCode(VerifyCodeRequest request) {
		if (!identityVerificationService.isValid(request.getCode(), request.getPhone(), request.getType())) {
			return ResponseEntity.ok(ResultResponse.of(CODE_INVALID));
		}
		identityVerificationService.delete(request.getCode(), request.getType());
		final String key = RandomUtils.generateAuthKey();
		identityVerificationService.saveAuthKey(key, request.getPhone(), request.getType());
		final VerifyCodeResponse response = VerifyCodeResponse.builder()
			.key(key)
			.build();
		return ResponseEntity.ok(ResultResponse.of(CODE_VERIFIED, response));
	}

	@Override
	public ResponseEntity<ResultResponse> signup(SignupRequest request) {
		if (!identityVerificationService.isValid(request.getKey(), request.getPhone(), SIGNUP)) {
			return ResponseEntity.ok(ResultResponse.of(KEY_INVALID));
		}
		authService.signup(request.getUsername(), request.getPassword(), request.getName(),
			request.getPhone(), request.getBirthDate(), Image.getInitial());
		identityVerificationService.delete(request.getKey(), SIGNUP);
		return ResponseEntity.ok(ResultResponse.of(SIGNUP_SUCCESS));
	}

	@Override
	public ResponseEntity<ResultResponse> identify(IdentifyRequest request) {
		return ResponseEntity.ok(authService.identify(request.getName(), request.getPhone()));
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
