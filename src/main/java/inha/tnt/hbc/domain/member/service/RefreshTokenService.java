package inha.tnt.hbc.domain.member.service;

import static inha.tnt.hbc.util.Constants.*;
import static java.util.concurrent.TimeUnit.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

	private static final String REDIS_PREFIX_KEY = "rt";
	private final RedisTemplate<String, String> redisTemplate;
	@Value("${jwt.valid.refresh}")
	private long REFRESH_TOKEN_VALIDITY;

	public void saveRefreshToken(Long memberId, String refreshToken) {
		redisTemplate.opsForValue().set(generateRedisKey(memberId), refreshToken, REFRESH_TOKEN_VALIDITY, MILLISECONDS);
	}

	public String getRefreshToken(Long memberId) {
		return redisTemplate.opsForValue().get(generateRedisKey(memberId));
	}

	public void deleteRefreshToken(Long memberId) {
		redisTemplate.opsForValue().set(generateRedisKey(memberId), EMPTY, 1, MILLISECONDS);
	}

	private String generateRedisKey(Long memberId) {
		return REDIS_PREFIX_KEY + DELIMITER + memberId;
	}

}
