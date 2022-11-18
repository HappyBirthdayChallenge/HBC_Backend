package inha.tnt.hbc.domain.member.service;

import static inha.tnt.hbc.util.Constants.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import inha.tnt.hbc.domain.member.entity.Member;
import inha.tnt.hbc.util.TimeUtils;

@Service
@RequiredArgsConstructor
public class FCMTokenService {

	private static final String REDIS_PREFIX_KEY = "fcm";
	private final RedisTemplate<String, String> redisTemplate;

	@Async
	public void saveFCMToken(Long memberId, String fcmToken) {
		final String timestamp = TimeUtils.convertToString(LocalDate.now());
		redisTemplate.opsForHash().put(generateRedisKey(memberId), fcmToken, timestamp);
	}

	@Async
	public void deleteFCMToken(Long memberId, String fcmToken) {
		redisTemplate.opsForHash().delete(generateRedisKey(memberId), fcmToken);
	}

	public List<String> getFcmTokens(Member member) {
		return redisTemplate.opsForHash()
				.keys(generateRedisKey(member.getId()))
				.stream()
				.map(Object::toString)
				.collect(Collectors.toList());
	}

	private String generateRedisKey(Long memberId) {
		return REDIS_PREFIX_KEY + DELIMITER + memberId;
	}

}
