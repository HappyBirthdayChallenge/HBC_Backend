package inha.tnt.hbc.domain.member.service;

import static java.util.concurrent.TimeUnit.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IdentityVerificationService {

	private final RedisTemplate<String, String> redisTemplate;
	@Value("${auth.valid.code}")
	private long AUTH_CODE_VALIDITY;
	@Value("${auth.valid.key}")
	private long AUTH_KEY_VALIDITY;

	@Transactional(readOnly = true)
	public boolean isValid(String key, String value) {
		return redisTemplate.opsForValue().get(key) != null && String.valueOf(redisTemplate.opsForValue().get(key)).equals(value);
	}

	@Transactional
	public void delete(String key) {
		redisTemplate.delete(key);
	}

	@Transactional
	public void saveAuthCode(String code, String phone) {
		redisTemplate.opsForValue().set(code, phone, AUTH_CODE_VALIDITY, MILLISECONDS);
	}

	@Transactional
	public void saveAuthKey(String key, String phone) {
		redisTemplate.opsForValue().set(key, phone, AUTH_KEY_VALIDITY, MILLISECONDS);
	}

}
