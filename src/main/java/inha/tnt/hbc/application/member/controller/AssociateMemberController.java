package inha.tnt.hbc.application.member.controller;

import static inha.tnt.hbc.model.ResultCode.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import inha.tnt.hbc.application.member.service.AssociateMemberService;
import inha.tnt.hbc.model.ResultResponse;
import inha.tnt.hbc.model.member.AssociateMemberApi;
import inha.tnt.hbc.security.jwt.dto.JwtDto;
import inha.tnt.hbc.vo.BirthDate;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AssociateMemberController implements AssociateMemberApi {

	private final AssociateMemberService associateMemberService;

	@Override
	public ResponseEntity<ResultResponse> setupBirthday(BirthDate birthDate) {
		final JwtDto jwtDto = associateMemberService.setupMyBirthdayAndGenerateJwt(birthDate);
		return ResponseEntity.ok(ResultResponse.of(BIRTHDAY_SETUP_SUCCESS, jwtDto));
	}

}
