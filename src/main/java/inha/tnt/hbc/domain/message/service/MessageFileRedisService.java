package inha.tnt.hbc.domain.message.service;

import static inha.tnt.hbc.util.Constants.*;

import java.util.HashMap;
import java.util.Map;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import inha.tnt.hbc.application.file.dto.LocalFile;
import inha.tnt.hbc.domain.message.dto.MessageFileDto;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageFileRedisService {

	private static final String REDIS_PREFIX_KEY = "mf";
	private final RedisTemplate<String, Object> redisTemplate;

	public void save(Long messageId, Long fileId, LocalFile localFile) {
		final Map<String, Object> hashKey = new HashMap<>();
		hashKey.put(fileId.toString(), MessageFileDto.of(localFile).convertToMap());
		redisTemplate.opsForHash().putAll(generateRedisKey(messageId), hashKey);
	}

	public void delete(Long messageId) {
		redisTemplate.delete(generateRedisKey(messageId));
	}

	public void delete(Long messageId, Long fileId) {
		redisTemplate.opsForHash().delete(generateRedisKey(messageId), fileId.toString());
	}

	private String generateRedisKey(Long messageId) {
		return REDIS_PREFIX_KEY + DELIMITER + messageId;
	}

}
