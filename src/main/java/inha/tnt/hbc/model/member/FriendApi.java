package inha.tnt.hbc.model.member;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import inha.tnt.hbc.model.member.dto.FollowerPageResponse;
import inha.tnt.hbc.model.member.dto.FollowingPageResponse;
import inha.tnt.hbc.model.ErrorResponse;
import inha.tnt.hbc.model.ResultResponse;
import inha.tnt.hbc.model.Void;
import inha.tnt.hbc.model.member.dto.MemberSearchResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "친구 API")
@Validated
@RequestMapping("/members/friends")
public interface FriendApi {

	@ApiOperation(value = "팔로잉 목록 조회")
	@ApiResponses({
		@ApiResponse(code = 1, response = FollowingPageResponse.class, message = ""
			+ "status: 200 | code: R-M014 | message: 팔로잉 목록 조회에 성공하였습니다."),
		@ApiResponse(code = 500, response = ErrorResponse.class, message = ""
			+ "status: 400 | code: E-G002 | message: 입력 값이 유효하지 않습니다.\n"
			+ "status: 401 | code: E-A003 | message: 인증에 실패하였습니다.\n"
			+ "status: 500 | code: E-G001 | message: 내부 서버 오류입니다.")
	})
	@ApiImplicitParams({
		@ApiImplicitParam(name = "page", value = "페이지(1 이상)", required = true, example = "1"),
		@ApiImplicitParam(name = "size", value = "페이지당 개수(1 이상)", required = true, example = "10")
	})
	@GetMapping("/followings")
	ResponseEntity<ResultResponse> getFollowings(@Min(1) @RequestParam int page, @Min(1) @RequestParam int size);

	@ApiOperation(value = "친구 추가", notes = ""
			+ "상대방을 친구 추가하면, 상대방에게 알림을 전송합니다.")
	@ApiResponses({
		@ApiResponse(code = 1, response = Void.class, message = ""
			+ "status: 200 | code: R-M013 | message: 친구 추가에 성공하였습니다."),
		@ApiResponse(code = 500, response = ErrorResponse.class, message = ""
			+ "status: 400 | code: E-G002 | message: 입력 값이 유효하지 않습니다.\n"
			+ "status: 400 | code: E-M001 | message: 존재하지 않는 회원입니다.\n"
			+ "status: 400 | code: E-M006 | message: 이미 친구관계입니다.\n"
			+ "status: 401 | code: E-A003 | message: 인증에 실패하였습니다.\n"
			+ "status: 500 | code: E-G001 | message: 내부 서버 오류입니다.")
	})
	@ApiImplicitParam(name = "member_id", value = "회원 PK", required = true, example = "1")
	@PostMapping
	ResponseEntity<ResultResponse> addFriend(@RequestParam(name = "member_id") Long memberId);

	@ApiOperation(value = "팔로워 목록 조회")
	@ApiResponses({
		@ApiResponse(code = 1, response = FollowerPageResponse.class, message = ""
			+ "status: 200 | code: R-M020 | message: 팔로워 목록 조회에 성공하였습니다."),
		@ApiResponse(code = 500, response = ErrorResponse.class, message = ""
			+ "status: 400 | code: E-G002 | message: 입력 값이 유효하지 않습니다.\n"
			+ "status: 401 | code: E-A003 | message: 인증에 실패하였습니다.\n"
			+ "status: 500 | code: E-G001 | message: 내부 서버 오류입니다.")
	})
	@ApiImplicitParams({
		@ApiImplicitParam(name = "page", value = "페이지(1 이상)", required = true, example = "1"),
		@ApiImplicitParam(name = "size", value = "페이지당 개수(1 이상)", required = true, example = "10")
	})
	@GetMapping("/followers")
	ResponseEntity<ResultResponse> getFollowers(@Min(1) @RequestParam int page, @Min(1) @RequestParam int size);

	@ApiOperation(value = "친구 삭제")
	@ApiResponses({
		@ApiResponse(code = 1, response = Void.class, message = ""
			+ "status: 200 | code: R-M021 | message: 친구 삭제에 성공하였습니다."),
		@ApiResponse(code = 500, response = ErrorResponse.class, message = ""
			+ "status: 400 | code: E-G002 | message: 입력 값이 유효하지 않습니다.\n"
			+ "status: 400 | code: E-M001 | message: 존재하지 않는 회원입니다.\n"
			+ "status: 400 | code: E-M008 | message: 친구 관계가 아닙니다.\n"
			+ "status: 401 | code: E-A003 | message: 인증에 실패하였습니다.\n"
			+ "status: 500 | code: E-G001 | message: 내부 서버 오류입니다.")
	})
	@ApiImplicitParam(name = "member_id", value = "회원 PK", required = true, example = "1")
	@DeleteMapping("/{member_id}")
	ResponseEntity<ResultResponse> deleteFriend(@PathVariable(name = "member_id") Long memberId);

	@ApiOperation(value = "팔로워 검색", notes = ""
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
	@GetMapping("/search/follower")
	ResponseEntity<ResultResponse> searchFollower(@Pattern(regexp = "^[가-힣A-Za-z\\d]{1,20}$") @RequestParam String keyword);



	@ApiOperation(value = "팔로잉 검색", notes = ""
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
	@GetMapping("/search/following")
	ResponseEntity<ResultResponse> searchFollowing(@Pattern(regexp = "^[가-힣A-Za-z\\d]{1,20}$") @RequestParam String keyword);


}
