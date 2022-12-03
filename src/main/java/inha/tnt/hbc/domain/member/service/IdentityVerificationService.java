package inha.tnt.hbc.domain.member.service;

import static inha.tnt.hbc.domain.member.service.IdentityVerificationService.AuthTypes.*;
import static inha.tnt.hbc.util.Constants.*;
import static java.util.concurrent.TimeUnit.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.Getter;
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

	public boolean isValidCode(String phone, String code, IdentityVerificationTypes type) {
		final String fullKey = generateRedisKey(AUTH_CODE, type, phone);
		return redisTemplate.opsForValue().get(fullKey) != null &&
			String.valueOf(redisTemplate.opsForValue().get(fullKey)).equals(code);
	}

	public boolean isValidKey(String phone, String key, IdentityVerificationTypes type) {
		final String fullKey = generateRedisKey(AUTH_KEY, type, phone);
		return redisTemplate.opsForValue().get(fullKey) != null &&
			String.valueOf(redisTemplate.opsForValue().get(fullKey)).equals(key);
	}

	public void deleteCode(String phone, IdentityVerificationTypes type) {
		redisTemplate.delete(generateRedisKey(AUTH_CODE, type, phone));
	}

	public void deleteKey(String phone, IdentityVerificationTypes type) {
		redisTemplate.delete(generateRedisKey(AUTH_KEY, type, phone));
	}

	public void saveAuthCode(String code, String phone, IdentityVerificationTypes type) {
		redisTemplate.opsForValue()
			.set(generateRedisKey(AUTH_CODE, type, phone), code, AUTH_CODE_VALIDITY, MILLISECONDS);
	}

	public void saveAuthKey(String key, String phone, IdentityVerificationTypes type) {
		redisTemplate.opsForValue()
			.set(generateRedisKey(AUTH_KEY, type, phone), key, AUTH_KEY_VALIDITY, MILLISECONDS);
	}

	private String generateRedisKey(AuthTypes authType, IdentityVerificationTypes identityVerificationType,
		String phone) {
		return REDIS_PREFIX_KEY + DELIMITER +
			identityVerificationType.getInfix() + DELIMITER +
			authType.getInfix() + DELIMITER +
			phone;
	}

	@Getter
	@AllArgsConstructor
	public enum IdentityVerificationTypes {
		SIGNUP("su"), FIND_ID("fi"), FIND_PW("fp"), CHANGE_PW("cp");

		private final String infix;
	}

	@Getter
	@AllArgsConstructor
	public enum AuthTypes {
		AUTH_CODE("ac"), AUTH_KEY("ac");

		private final String infix;
	}

}
