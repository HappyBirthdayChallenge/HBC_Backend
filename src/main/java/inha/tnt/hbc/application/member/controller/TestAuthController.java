package inha.tnt.hbc.application.member.controller;

import static inha.tnt.hbc.util.Constants.*;
import static java.util.concurrent.TimeUnit.*;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@Api(tags = "회원 인증(Test)")
@RequestMapping("/test/auth")
@RestController
@RequiredArgsConstructor
public class TestAuthController {

	private final RedisTemplate<String, String> redisTemplate;
	@Value("${auth.valid.key}")
	private long AUTH_KEY_VALIDITY;

	@ApiOperation(value = "인증 키 생성")
	@PostMapping("/key/generate")
	public String generateAuthKey() {
		final String authKey = UUID.randomUUID().toString();
		redisTemplate.opsForValue().set(authKey, EMPTY, AUTH_KEY_VALIDITY, MILLISECONDS);
		return authKey;
	}

}
