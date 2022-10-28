package inha.tnt.hbc.application.member.controller;

import static java.util.concurrent.TimeUnit.*;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@Api(tags = "Test")
@RequestMapping("/test")
@RestController
@RequiredArgsConstructor
public class TestAuthController {

	private final RedisTemplate<String, String> redisTemplate;
	@Value("${auth.valid.key}")
	private long AUTH_KEY_VALIDITY;

	@ApiOperation(value = "인증 키 생성")
	@ApiImplicitParam(name = "phone", value = "휴대폰 번호", required = true, example = "010-9128-5708")
	@PostMapping("/key/generate")
	public String generateAuthKey(@RequestParam String phone) {
		final String authKey = UUID.randomUUID().toString();
		redisTemplate.opsForValue().set("iv_" + authKey, phone, AUTH_KEY_VALIDITY, MILLISECONDS);
		return authKey;
	}

	@ApiOperation(value = "Redis key 조회")
	@GetMapping("/key")
	public String getValue(@RequestParam String key) {
		return redisTemplate.opsForValue().get(key);
	}

}
