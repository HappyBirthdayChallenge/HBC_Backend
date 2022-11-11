package inha.tnt.hbc.model.member;

import javax.validation.constraints.Min;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import inha.tnt.hbc.model.member.dto.FriendListResponse;
import inha.tnt.hbc.model.ErrorResponse;
import inha.tnt.hbc.model.ResultResponse;
import inha.tnt.hbc.model.Void;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "친구 API")
@Validated
@RequestMapping("/members/friends")
public interface FriendManageApi {

	@ApiOperation(value = "친구 목록 조회")
	@ApiResponses({
		@ApiResponse(code = 1, response = FriendListResponse.class, message = ""
			+ "status: 200 | code: R-M014 | message: 친구 목록 조회에 성공하였습니다."),
		@ApiResponse(code = 2, response = ErrorResponse.class, message = ""
			+ "status: 400 | code: E-G002 | message: 입력 값이 유효하지 않습니다.\n"
			+ "status: 401 | code: E-A003 | message: 인증에 실패하였습니다.\n"
			+ "status: 500 | code: E-G001 | message: 내부 서버 오류입니다.")
	})
	@ApiImplicitParams({
		@ApiImplicitParam(name = "page", value = "페이지(1 이상)", required = true, example = "1"),
		@ApiImplicitParam(name = "size", value = "페이지당 개수(1 이상)", required = true, example = "10")
	})
	@GetMapping
	ResponseEntity<ResultResponse> getFriends(@Min(1) @RequestParam int page, @Min(1) @RequestParam int size);

	@ApiOperation(value = "친구 추가")
	@ApiResponses({
		@ApiResponse(code = 1, response = Void.class, message = ""
			+ "status: 200 | code: R-M013 | message: 친구 추가에 성공하였습니다."),
		@ApiResponse(code = 2, response = ErrorResponse.class, message = ""
			+ "status: 400 | code: E-G002 | message: 입력 값이 유효하지 않습니다.\n"
			+ "status: 400 | code: E-M001 | message: 존재하지 않는 회원입니다.\n"
			+ "status: 400 | code: E-M006 | message: 이미 친구관계입니다.\n"
			+ "status: 401 | code: E-A003 | message: 인증에 실패하였습니다.\n"
			+ "status: 500 | code: E-G001 | message: 내부 서버 오류입니다.")
	})
	@ApiImplicitParam(name = "member_id", value = "회원 PK", required = true, example = "1")
	@PostMapping
	ResponseEntity<ResultResponse> addFriend(@RequestParam(name = "member_id") Long memberId);

}
