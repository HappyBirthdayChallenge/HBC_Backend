package inha.tnt.hbc.model.member;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import inha.tnt.hbc.model.ResultResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(tags = "OAuth2 회원 인증")
@RequestMapping("/oauth2")
public interface OAuth2Api {

	@ApiOperation(value = "간편 로그인")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "provider", value = "OAuth2 제공자", example = "kakao", required = true),
		@ApiImplicitParam(name = "code", value = "인가 코드", example = "CKWmhCwdgVeZwZ2YNQAccr5aW1S6xItOGKkiCEepCj1zmgAAAYPDeqSi", required = true)
	})
	@GetMapping("/signin/{provider}")
	ResponseEntity<ResultResponse> signin(@PathVariable String provider, @RequestParam String code);

}
