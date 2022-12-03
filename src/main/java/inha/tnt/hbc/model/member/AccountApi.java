package inha.tnt.hbc.model.member;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import inha.tnt.hbc.annotation.BirthDay;
import inha.tnt.hbc.domain.member.dto.MemberProfileDto;
import inha.tnt.hbc.domain.member.vo.BirthDate;
import inha.tnt.hbc.model.ErrorResponse;
import inha.tnt.hbc.model.ResultResponse;
import inha.tnt.hbc.model.Void;
import inha.tnt.hbc.model.member.dto.MemberSearchResponse;
import inha.tnt.hbc.model.member.dto.MyInfoResponse;
import inha.tnt.hbc.security.jwt.dto.JwtDto;

@Api(tags = "회원 계정 API")
@Validated
@RequestMapping("/members/accounts")
public interface AccountApi {

	@ApiOperation(value = "생일 입력", notes = "생일은 최초 1회만 입력할 수 있습니다.")
	@ApiResponses({
		@ApiResponse(code = 1, response = JwtDto.class, message = ""
			+ "status: 200 | code: R-M012 | message: 생일 입력에 성공하였습니다."),
		@ApiResponse(code = 500, response = ErrorResponse.class, message = ""
			+ "status: 400 | code: E-G002 | message: 입력 값이 유효하지 않습니다.\n"
			+ "status: 400 | code: E-M005 | message: 이미 생일을 입력한 회원입니다..\n"
			+ "status: 401 | code: E-A003 | message: 인증에 실패하였습니다.\n"
			+ "status: 500 | code: E-G001 | message: 내부 서버 오류입니다.")
	})
	@PostMapping("/birthday")
	ResponseEntity<ResultResponse> setupBirthday(@Valid @BirthDay @RequestBody BirthDate birthDate);

	@ApiOperation(value = "로그아웃", notes = ""
		+ "1. 서버에서 Refresh token을 무효화합니다.\n"
		+ "2. 서버에서 FCM 토큰을 무효화합니다."
		+ "3. 앞단에서 사용하던 토큰을 모두 삭제해주세요.\n")
	@ApiImplicitParam(name = "fcm_token", value = "FCM token", example = "c2aK9KHmw8E:APA91bF7...", required = true)
	@ApiResponses({
		@ApiResponse(code = 1, response = Void.class, message = ""
			+ "status: 200 | code: R-M018 | message: 로그아웃에 성공하였습니다."),
		@ApiResponse(code = 500, response = ErrorResponse.class, message = ""
			+ "status: 400 | code: E-G002 | message: 입력 값이 유효하지 않습니다.\n"
			+ "status: 401 | code: E-A003 | message: 인증에 실패하였습니다.\n"
			+ "status: 500 | code: E-G001 | message: 내부 서버 오류입니다.")
	})
	@PostMapping("/signout")
	ResponseEntity<ResultResponse> signout(@RequestParam(name = "fcm_token") @Size(max = 2000) String fcmToken);

	@ApiOperation(value = "본인 정보 조회")
	@ApiResponses({
		@ApiResponse(code = 1, response = MyInfoResponse.class, message = ""
			+ "status: 200 | code: R-M019 | message: 본인 정보 조회에 성공하였습니다."),
		@ApiResponse(code = 500, response = ErrorResponse.class, message = ""
			+ "status: 401 | code: E-A003 | message: 인증에 실패하였습니다.\n"
			+ "status: 500 | code: E-G001 | message: 내부 서버 오류입니다.")
	})
	@GetMapping("/me")
	ResponseEntity<ResultResponse> getMyInfo();

	@ApiOperation(value = "회원 프로필 조회")
	@ApiResponses({
		@ApiResponse(code = 1, response = MemberProfileDto.class, message = ""
			+ "status: 200 | code: R-M022 | message: 회원 프로필 조회에 성공하였습니다."),
		@ApiResponse(code = 500, response = ErrorResponse.class, message = ""
			+ "status: 400 | code: E-G002 | message: 입력 값이 유효하지 않습니다.\n"
			+ "status: 400 | code: E-M001 | message: 존재하지 않는 회원입니다.\n"
			+ "status: 401 | code: E-A003 | message: 인증에 실패하였습니다.\n"
			+ "status: 500 | code: E-G001 | message: 내부 서버 오류입니다.")
	})
	@ApiImplicitParam(name = "member_id", value = "회원 PK", example = "1", required = true)
	@GetMapping("/profile/{member_id}")
	ResponseEntity<ResultResponse> getProfile(@PathVariable(name = "member_id") Long memberId);

	@ApiOperation(value = "회원 검색", notes = ""
		+ "1. 회원 아이디(username) or 이름(name) prefix 기준으로 검색합니다.\n"
		+ "2. 최대 20개까지만 조회합니다.")
	@ApiResponses({
		@ApiResponse(code = 1, response = MemberSearchResponse.class, message = ""
			+ "status: 200 | code: R-M023 | message: 회원 검색에 성공하였습니다."),
		@ApiResponse(code = 500, response = ErrorResponse.class, message = ""
			+ "status: 400 | code: E-G002 | message: 입력 값이 유효하지 않습니다.\n"
			+ "status: 401 | code: E-A003 | message: 인증에 실패하였습니다.\n"
			+ "status: 500 | code: E-G001 | message: 내부 서버 오류입니다.")
	})
	@ApiImplicitParam(name = "keyword", value = "검색 키워드(^[가-힣A-Za-z\\d]{1,20}$)", example = "dkdlel", required = true)
	@GetMapping("/search")
	ResponseEntity<ResultResponse> search(@Pattern(regexp = "^[가-힣A-Za-z\\d]{1,20}$") @RequestParam String keyword);

	@ApiOperation(value = "회원 이름 변경")
	@ApiResponses({
		@ApiResponse(code = 1, response = Void.class, message = ""
			+ "status: 200 | code: R-M024 | message: 회원 이름 변경에 성공하였습니다."),
		@ApiResponse(code = 500, response = ErrorResponse.class, message = ""
			+ "status: 400 | code: E-G002 | message: 입력 값이 유효하지 않습니다.\n"
			+ "status: 401 | code: E-A003 | message: 인증에 실패하였습니다.\n"
			+ "status: 500 | code: E-G001 | message: 내부 서버 오류입니다.")
	})
	@ApiImplicitParam(name = "name", value = "회원 이름(^[가-힣A-Za-z\\d]{2,10}$)", example = "손흥민1", required = true)
	@PatchMapping("/name")
	ResponseEntity<ResultResponse> changeName(@Pattern(regexp = "^[가-힣A-Za-z\\d]{2,10}$") @RequestParam String name);

}
