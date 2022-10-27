package inha.tnt.hbc.model.admin;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import inha.tnt.hbc.model.ResultResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "관리자")
@RequestMapping("/admins")
public interface AdminApi {

	@ApiOperation(value = "회원 목록 조회")
	@GetMapping("/members")
	ResponseEntity<ResultResponse> getMembers();


}