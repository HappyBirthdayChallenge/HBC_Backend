package inha.tnt.hbc.domain.member.service;

import static inha.tnt.hbc.util.Constants.*;

import java.time.LocalDateTime;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import inha.tnt.hbc.util.TimeUtils;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FCMTokenService {

	private static final String REDIS_PREFIX_KEY = "fcm";
	private final RedisTemplate<String, String> redisTemplate;

	@Async
	public void saveFCMToken(Long memberId, String fcmToken) {
		final String timestamp = TimeUtils.convertToString(LocalDateTime.now());
		redisTemplate.opsForHash().put(generateRedisKey(memberId), fcmToken, timestamp);
	}

	@Async
	public void deleteFCMToken(Long memberId, String fcmToken) {
		redisTemplate.opsForHash().delete(generateRedisKey(memberId), fcmToken);
	}

	private String generateRedisKey(Long memberId) {
		return REDIS_PREFIX_KEY + DELIMITER + memberId;
	}

}
