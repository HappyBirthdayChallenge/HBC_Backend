package inha.tnt.hbc.model.admin;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import inha.tnt.hbc.model.ResultResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@Api(tags = "관리자 API")
@RequestMapping("/admins")
public interface AdminApi {

	@ApiOperation(value = "회원 목록 조회")
	@GetMapping("/members")
	ResponseEntity<ResultResponse> getMembers();

	@ApiOperation(value = "회원 탈퇴")
	@ApiImplicitParam(name = "member_id", value = "회원 PK", required = true, example = "1")
	@DeleteMapping("/members")
	ResponseEntity<ResultResponse> deleteMember(@RequestParam(name = "member_id") Long memberId);

}
