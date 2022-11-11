package inha.tnt.hbc.model.member;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import inha.tnt.hbc.annotation.BirthDay;
import inha.tnt.hbc.model.ErrorResponse;
import inha.tnt.hbc.model.ResultResponse;
import inha.tnt.hbc.model.Void;
import inha.tnt.hbc.model.member.dto.MyInfoResponse;
import inha.tnt.hbc.security.jwt.dto.JwtDto;
import inha.tnt.hbc.vo.BirthDate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "회원 계정 API")
@RequestMapping("/members/accounts")
public interface AccountManageApi {

	@ApiOperation(value = "생일 입력", notes = "생일은 최초 1회만 입력할 수 있습니다.")
	@ApiResponses({
		@ApiResponse(code = 1, response = JwtDto.class, message = ""
			+ "status: 200 | code: R-M012 | message: 생일 입력에 성공하였습니다."),
		@ApiResponse(code = 2, response = ErrorResponse.class, message = ""
			+ "status: 400 | code: E-G002 | message: 입력 값이 유효하지 않습니다.\n"
			+ "status: 400 | code: E-M005 | message: 이미 생일을 입력한 회원입니다..\n"
			+ "status: 401 | code: E-A003 | message: 인증에 실패하였습니다.\n"
			+ "status: 500 | code: E-G001 | message: 내부 서버 오류입니다.")
	})
	@PostMapping("/birthday")
	ResponseEntity<ResultResponse> setupBirthday(@Valid @BirthDay @RequestBody BirthDate birthDate);

	@ApiOperation(value = "로그아웃", notes = ""
		+ "1. 서버에서 Refresh token을 무효화합니다.\n"
		+ "2. 앞단에서 사용하던 인증 토큰을 모두 삭제해주세요.")
	@ApiResponses({
		@ApiResponse(code = 1, response = Void.class, message = ""
			+ "status: 200 | code: R-M018 | message: 로그아웃에 성공하였습니다."),
		@ApiResponse(code = 2, response = ErrorResponse.class, message = ""
			+ "status: 401 | code: E-A003 | message: 인증에 실패하였습니다.\n"
			+ "status: 500 | code: E-G001 | message: 내부 서버 오류입니다.")
	})
	@PostMapping("/signout")
	ResponseEntity<ResultResponse> signout();

	@ApiOperation(value = "본인 정보 조회")
	@ApiResponses({
		@ApiResponse(code = 1, response = MyInfoResponse.class, message = ""
			+ "status: 200 | code: R-M019 | message: 본인 정보 조회에 성공하였습니다."),
		@ApiResponse(code = 2, response = ErrorResponse.class, message = ""
			+ "status: 401 | code: E-A003 | message: 인증에 실패하였습니다.\n"
			+ "status: 500 | code: E-G001 | message: 내부 서버 오류입니다.")
	})
	@GetMapping("/me")
	ResponseEntity<ResultResponse> getMyInfo();

}
