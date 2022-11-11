package inha.tnt.hbc.model.member;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import inha.tnt.hbc.domain.member.entity.oauth2.OAuth2Providers;
import inha.tnt.hbc.model.ErrorResponse;
import inha.tnt.hbc.model.ResultResponse;
import inha.tnt.hbc.security.jwt.dto.JwtDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "OAuth2 인증 API")
@RequestMapping("/oauth2")
public interface OAuth2Api {

	@ApiOperation(value = "간편 로그인", notes = ""
		+ "1. 먼저 OAuth2 로그인을 통해 인가코드를 발급받아야 합니다.\n"
		+ "2. 다음으로 인가코드를 이용하여 OAuth2 인가 토큰을 발급받아야 합니다.")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "provider", value = "OAuth2 플랫폼명", example = "KAKAO", required = true),
		@ApiImplicitParam(name = "token", value = "OAuth2 인가 토큰", example = "7POZ3TGhuFkBtaEj8WwFLB7Qm8WZvbSaf-lU5_C4CinJXgAAAYPWM1g9", required = true)
	})
	@ApiResponses({
		@ApiResponse(code = 1, response = JwtDto.class, message = ""
			+ "status: 200 | code: R-M011 | message: 로그인에 성공하였습니다."),
		@ApiResponse(code = 500, response = ErrorResponse.class, message = ""
			+ "status: 400 | code: E-G002 | message: 입력 값이 유효하지 않습니다.\n"
			+ "status: 500 | code: E-G001 | message: 내부 서버 오류입니다."),
	})
	@PostMapping("/signin/{provider}")
	ResponseEntity<ResultResponse> signin(@PathVariable OAuth2Providers provider, @RequestParam String token);

}
