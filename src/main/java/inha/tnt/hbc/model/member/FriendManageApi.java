package inha.tnt.hbc.model.member;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import inha.tnt.hbc.model.ErrorResponse;
import inha.tnt.hbc.model.PageDto;
import inha.tnt.hbc.model.ResultResponse;
import inha.tnt.hbc.security.jwt.dto.JwtDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "회원 친구 관리")
@RequestMapping("/members/friends")
public interface FriendManageApi {

	@ApiOperation(value = "친구 목록 조회")
	@ApiResponses({
		@ApiResponse(code = 1, response = JwtDto.class, message = ""
			+ "status: 200 | code: R-M014 | message: 친구 목록 조회에 성공하였습니다."),
		@ApiResponse(code = 2, response = ErrorResponse.class, message = ""
			+ "status: 400 | code: E-G002 | message: 입력 값이 유효하지 않습니다.\n"
			+ "status: 401 | code: E-A003 | message: 인증에 실패하였습니다.\n"
			+ "status: 500 | code: E-G001 | message: 내부 서버 오류입니다.")
	})
	@GetMapping
	ResponseEntity<ResultResponse> getFriends(@Valid @RequestBody PageDto pageDto);

	@ApiOperation(value = "친구 추가")
	@ApiResponses({
		@ApiResponse(code = 1, response = JwtDto.class, message = ""
			+ "status: 200 | code: R-M013 | message: 친구 추가에 성공하였습니다."),
		@ApiResponse(code = 2, response = ErrorResponse.class, message = ""
			+ "status: 400 | code: E-G002 | message: 입력 값이 유효하지 않습니다.\n"
			+ "status: 400 | code: E-M006 | message: 이미 친구관계입니다.\n"
			+ "status: 401 | code: E-A003 | message: 인증에 실패하였습니다.\n"
			+ "status: 500 | code: E-G001 | message: 내부 서버 오류입니다.")
	})
	@ApiImplicitParam(name = "memberId", value = "회원 PK", required = true, example = "1")
	@PostMapping
	ResponseEntity<ResultResponse> addFriend(@RequestParam Long memberId);

}
