package inha.tnt.hbc.application.member.controller;

import static inha.tnt.hbc.model.ResultCode.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import inha.tnt.hbc.application.member.service.AccountManageService;
import inha.tnt.hbc.model.ResultResponse;
import inha.tnt.hbc.model.member.AccountManageApi;
import inha.tnt.hbc.model.member.dto.MyInfoResponse;
import inha.tnt.hbc.security.jwt.dto.JwtDto;
import inha.tnt.hbc.vo.BirthDate;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AccountController implements AccountManageApi {

	private final AccountManageService accountManageService;

	@Override
	public ResponseEntity<ResultResponse> setupBirthday(BirthDate birthDate) {
		final JwtDto jwtDto = accountManageService.setupMyBirthdayAndGenerateJwt(birthDate);
		return ResponseEntity.ok(ResultResponse.of(BIRTHDAY_SETUP_SUCCESS, jwtDto));
	}

	@Override
	public ResponseEntity<ResultResponse> signout() {
		return ResponseEntity.ok(accountManageService.signout());
	}

	@Override
	public ResponseEntity<ResultResponse> getMyInfo() {
		final MyInfoResponse response = accountManageService.getMyInfo();
		return ResponseEntity.ok(ResultResponse.of(GET_MY_INFO_SUCCESS, response));
	}

}
