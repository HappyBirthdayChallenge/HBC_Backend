package inha.tnt.hbc.domain.member.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IdentityVerificationService {

	private final RedisTemplate<String, String> redisTemplate;

	@Transactional(readOnly = true)
	public boolean isValid(String key) {
		return redisTemplate.opsForValue().get(key) != null;
	}

	@Transactional
	public void delete(String key) {
		redisTemplate.delete(key);
	}

}
