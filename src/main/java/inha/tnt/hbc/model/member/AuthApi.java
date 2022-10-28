package inha.tnt.hbc.model.member;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import inha.tnt.hbc.annotation.Jwt;
import inha.tnt.hbc.model.ErrorResponse;
import inha.tnt.hbc.model.ResultResponse;
import inha.tnt.hbc.model.Void;
import inha.tnt.hbc.model.member.dto.FindPasswordRequest;
import inha.tnt.hbc.model.member.dto.FindUsernameRequest;
import inha.tnt.hbc.model.member.dto.IdentifyRequest;
import inha.tnt.hbc.model.member.dto.PhoneRequest;
import inha.tnt.hbc.model.member.dto.SigninRequest;
import inha.tnt.hbc.model.member.dto.SignupRequest;
import inha.tnt.hbc.model.member.dto.UsernameRequest;
import inha.tnt.hbc.model.member.dto.VerifyCodeRequest;
import inha.tnt.hbc.model.member.dto.VerifyCodeResponse;
import inha.tnt.hbc.security.jwt.dto.JwtDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "회원 인증")
@Validated
@RequestMapping("/auth")
public interface AuthApi {

	@ApiOperation(value = "일반 로그인")
	@ApiResponses({
		@ApiResponse(code = 1, response = Void.class, message = ""
			+ "status: 200 | code: R-M010 | message: 아이디 혹은 비밀번호가 올바르지 않습니다."),
		@ApiResponse(code = 2, response = JwtDto.class, message = ""
			+ "status: 200 | code: R-M011 | message: 로그인에 성공하였습니다."),
		@ApiResponse(code = 500, response = ErrorResponse.class, message = ""
			+ "status: 400 | code: E-G002 | message: 입력 값이 유효하지 않습니다.\n"
			+ "status: 500 | code: E-G001 | message: 내부 서버 오류입니다.")
	})
	@PostMapping("/signin")
	ResponseEntity<ResultResponse> signin(@Valid @RequestBody SigninRequest request);

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

	@ApiOperation(value = "아이디 유효성 확인")
	@ApiResponses({
		@ApiResponse(code = 1, response = Void.class, message = ""
			+ "status: 200 | code: R-M001 | message: 사용 가능한 아이디입니다."
			+ "status: 200 | code: R-M004 | message: 이미 사용하고 있는 아이디입니다."),
		@ApiResponse(code = 500, response = ErrorResponse.class, message = ""
			+ "status: 400 | code: E-G002 | message: 입력 값이 유효하지 않습니다.\n"
			+ "status: 500 | code: E-G001 | message: 내부 서버 오류입니다.")
	})
	@PostMapping("/check/username")
	ResponseEntity<ResultResponse> checkUsername(@Valid @RequestBody UsernameRequest request);

	@ApiOperation(value = "휴대폰 번호 유효성 확인")
	@ApiResponses({
		@ApiResponse(code = 1, response = Void.class, message = ""
			+ "status: 200 | code: R-M015 | message: 사용 가능한 휴대폰 번호입니다."
			+ "status: 200 | code: R-M005 | message: 이미 사용하고 있는 휴대폰 번호입니다."),
		@ApiResponse(code = 500, response = ErrorResponse.class, message = ""
			+ "status: 400 | code: E-G002 | message: 입력 값이 유효하지 않습니다.\n"
			+ "status: 500 | code: E-G001 | message: 내부 서버 오류입니다.")
	})
	@PostMapping("/check/phone")
	ResponseEntity<ResultResponse> checkPhone(@Valid @RequestBody PhoneRequest request);

	@ApiOperation(value = "휴대폰 문자(SMS) 인증코드 전송", notes = "인증코드 유효시간은 3분입니다.")
	@ApiResponses({
		@ApiResponse(code = 1, response = Void.class, message = ""
			+ "status: 200 | code: R-IV004 | message: 인증 코드 전송에 성공하였습니다."),
		@ApiResponse(code = 500, response = ErrorResponse.class, message = ""
			+ "status: 400 | code: E-G002 | message: 입력 값이 유효하지 않습니다.\n"
			+ "status: 500 | code: E-G001 | message: 내부 서버 오류입니다.")
	})
	@PostMapping("/send/code")
	ResponseEntity<ResultResponse> sendCodeBySms(@Valid @RequestBody PhoneRequest request);

	@ApiOperation(value = "인증코드 검증", notes = "인증키 유효시간은 30분입니다.")
	@ApiResponses({
		@ApiResponse(code = 1, response = VerifyCodeResponse.class, message = ""
			+ "status: 200 | code: R-IV001 | message: 인증 코드 검증에 성공하였습니다."),
		@ApiResponse(code = 2, response = Void.class, message = ""
			+ "status: 200 | code: R-IV002 | message: 유효하지 않은 인증 코드입니다."),
		@ApiResponse(code = 500, response = ErrorResponse.class, message = ""
			+ "status: 400 | code: E-G002 | message: 입력 값이 유효하지 않습니다.\n"
			+ "status: 500 | code: E-G001 | message: 내부 서버 오류입니다.")
	})
	@PostMapping("/verify/code")
	ResponseEntity<ResultResponse> verifyCode(@Valid @RequestBody VerifyCodeRequest request);

	@ApiOperation(value = "일반 회원 가입", notes = ""
		+ "1. 아이디/휴대폰번호 유효성 확인을 필수로 진행해야 합니다.\n"
		+ "2. 휴대폰 인증과 인증코드 검증을 통해 인증키를 발급받아야 합니다.\n"
		+ "3. 가입 중간에 다른 회원이 먼저 동일한 아이디 or 휴대폰번호로 가입하는 예외 상황도 고려해 주세요. (status: 400)")
	@ApiResponses({
		@ApiResponse(code = 1, response = Void.class, message = ""
			+ "status: 200 | code: R-M003 | message: 회원가입에 성공하였습니다."
			+ "status: 200 | code: R-IV003 | message: 유효하지 않은 인증 키입니다."),
		@ApiResponse(code = 500, response = ErrorResponse.class, message = ""
			+ "status: 400 | code: E-G002 | message: 입력 값이 유효하지 않습니다.\n"
			+ "status: 500 | code: E-G001 | message: 내부 서버 오류입니다.")
	})
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
