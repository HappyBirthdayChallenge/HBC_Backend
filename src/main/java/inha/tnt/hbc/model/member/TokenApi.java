package inha.tnt.hbc.model.member;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import inha.tnt.hbc.annotation.Jwt;
import inha.tnt.hbc.model.ErrorResponse;
import inha.tnt.hbc.model.ResultResponse;
import inha.tnt.hbc.model.Void;
import inha.tnt.hbc.security.jwt.dto.JwtDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "토큰 API")
@RequestMapping("/token")
public interface TokenApi {

	@ApiOperation(value = "인증 토큰 유효성 검사", notes = "Authorization 헤더에 Access Token을 담아서 요청해주세요.")
	@ApiResponses({
		@ApiResponse(code = 1, response = Void.class, message = ""
			+ "status: 200 | code: R-J003 | message: 유효한 토큰입니다."),
		@ApiResponse(code = 500, response = ErrorResponse.class, message = ""
			+ "status: 401 | code: E-A003 | message: 인증에 실패하였습니다.\n"
			+ "status: 500 | code: E-G001 | message: 내부 서버 오류입니다.")
	})
	@PostMapping("/check")
	ResponseEntity<ResultResponse> check();

	@ApiOperation(value = "토큰 재발급")
	@ApiImplicitParam(name = "refreshToken", value = "RefreshToken", required = true, example = "Bearer ey...")
	@ApiResponses({
		@ApiResponse(code = 1, response = JwtDto.class, message = ""
			+ "status: 200 | code: R-J001 | message: 아이디 혹은 비밀번호가 올바르지 않습니다."),
		@ApiResponse(code = 2, response = Void.class, message = ""
			+ "status: 200 | code: R-J002 | message: 토큰 재발급에 실패하였습니다."),
		@ApiResponse(code = 500, response = ErrorResponse.class, message = ""
			+ "status: 400 | code: E-G002 | message: 입력 값이 유효하지 않습니다.\n"
			+ "status: 500 | code: E-G001 | message: 내부 서버 오류입니다.")
	})
	@PostMapping("/reissue")
	ResponseEntity<ResultResponse> reissue(@Jwt @RequestParam String refreshToken);


}
