package inha.tnt.hbc.model.member;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import inha.tnt.hbc.model.Void;
import inha.tnt.hbc.model.ErrorResponse;
import inha.tnt.hbc.model.ResultResponse;
import inha.tnt.hbc.model.member.dto.CodeRequest;
import inha.tnt.hbc.model.member.dto.EmailRequest;
import inha.tnt.hbc.model.member.dto.FindPasswordRequest;
import inha.tnt.hbc.model.member.dto.FindUsernameRequest;
import inha.tnt.hbc.model.member.dto.IdentifyRequest;
import inha.tnt.hbc.model.member.dto.SigninRequest;
import inha.tnt.hbc.model.member.dto.SignupRequest;
import inha.tnt.hbc.model.member.dto.UsernameRequest;
import inha.tnt.hbc.security.jwt.dto.JwtDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "회원 인증")
@RequestMapping("/auth")
public interface AuthApi {

	@ApiOperation(value = "일반 로그인")
	@ApiResponses({
		@ApiResponse(code = 1, response = Void.class, message = ""
			+ "status: 200 | code: R-M010 | message: 아이디 혹은 비밀번호가 올바르지 않습니다."),
		@ApiResponse(code = 2, response = JwtDto.class, message = ""
			+ "status: 200 | code: R-M011 | message: 로그인에 성공하였습니다.."),
		@ApiResponse(code = 500, response = ErrorResponse.class, message = ""
			+ "status: 500 | code: E-G001 | message: 내부 서버 오류입니다.")
	})
	@PostMapping("/signin")
	ResponseEntity<ResultResponse> signin(@Valid @RequestBody SigninRequest request);

	@ApiOperation(value = "토큰 재발급")
	@ApiImplicitParam(name = "refreshToken", value = "RefreshToken", required = true, example = "Bearer ey...", paramType = "cookie")
	@PostMapping("/reissue")
	ResponseEntity<ResultResponse> reissue(@CookieValue String refreshToken);

	@ApiOperation(value = "아이디 유효성 확인")
	@PostMapping("/check/username")
	ResponseEntity<ResultResponse> checkUsername(@Valid @RequestBody UsernameRequest request);

	@ApiOperation(value = "이메일 유효성 확인")
	@PostMapping("/check/email")
	ResponseEntity<ResultResponse> checkEmail(@Valid @RequestBody EmailRequest request);

	@ApiOperation(value = "이메일 인증코드 전송")
	@PostMapping("/send/code")
	ResponseEntity<ResultResponse> sendCodeToEmail(@Valid @RequestBody EmailRequest request);

	@ApiOperation(value = "인증코드 검증")
	@PostMapping("/verify/code")
	ResponseEntity<ResultResponse> verifyCode(@Valid @RequestBody CodeRequest request);

	@ApiOperation(value = "일반 회원 가입")
	@PostMapping("/signup")
	ResponseEntity<ResultResponse> signup(@Valid @RequestBody SignupRequest request);

	@ApiOperation(value = "본인 여부 확인")
	@PostMapping("/identify")
	ResponseEntity<ResultResponse> identify(@Valid @RequestBody IdentifyRequest request);

	@ApiOperation(value = "아이디 찾기")
	@PostMapping("/find/username")
	ResponseEntity<ResultResponse> findUsername(@Valid @RequestBody FindUsernameRequest request);

	@ApiOperation(value = "비밀번호 찾기")
	@PostMapping("/find/password")
	ResponseEntity<ResultResponse> findPassword(@Valid @RequestBody FindPasswordRequest request);

}
