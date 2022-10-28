package inha.tnt.hbc.domain.member.service;

import static inha.tnt.hbc.util.Constants.*;
import static java.util.concurrent.TimeUnit.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

// TODO: [Refactor] RedisAdapter
@Service
@RequiredArgsConstructor
public class IdentityVerificationService {

	private static final String REDIS_PREFIX_KEY = "iv";
	private final RedisTemplate<String, String> redisTemplate;
	@Value("${auth.valid.code}")
	private long AUTH_CODE_VALIDITY;
	@Value("${auth.valid.key}")
	private long AUTH_KEY_VALIDITY;

	public boolean isValid(String key, String value) {
		final String fullKey = generateRedisKey(key);
		return redisTemplate.opsForValue().get(fullKey) != null &&
			String.valueOf(redisTemplate.opsForValue().get(fullKey)).equals(value);
	}

	public void delete(String key) {
		redisTemplate.delete(generateRedisKey(key));
	}

	public void saveAuthCode(String code, String phone) {
		redisTemplate.opsForValue().set(generateRedisKey(code), phone, AUTH_CODE_VALIDITY, MILLISECONDS);
	}

	public void saveAuthKey(String key, String phone) {
		redisTemplate.opsForValue().set(generateRedisKey(key), phone, AUTH_KEY_VALIDITY, MILLISECONDS);
	}

	private String generateRedisKey(String key) {
		return REDIS_PREFIX_KEY + DELIMITER + key;
	}

}
