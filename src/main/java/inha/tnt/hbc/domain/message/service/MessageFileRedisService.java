package inha.tnt.hbc.domain.message.service;

import static inha.tnt.hbc.model.ErrorCode.*;
import static inha.tnt.hbc.util.Constants.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import inha.tnt.hbc.application.file.dto.LocalFile;
import inha.tnt.hbc.domain.message.dto.MessageFileDto;
import inha.tnt.hbc.exception.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class MessageFileRedisService {

	private static final String REDIS_PREFIX_KEY = "mf";
	private final RedisTemplate<String, Object> redisTemplate;

	public void save(Long messageId, String fileId, LocalFile localFile) {
		final Map<String, Map<String, String>> hashKey = new HashMap<>();
		hashKey.put(fileId, MessageFileDto.of(localFile).convertToMap());
		redisTemplate.opsForHash().putAll(generateRedisKey(messageId), hashKey);
	}

	public List<MessageFileDto> getAll(Long messageId, List<String> fileIds) {
		final String key = generateRedisKey(messageId);
		return fileIds.stream()
			.map(hashKey -> {
				final Object value = redisTemplate.opsForHash().get(key, hashKey);
				if (value == null) {
					throw new EntityNotFoundException(FILE_UNFOUNDED);
				}
				return value;
			})
			.map(value -> (Map<String, String>)value)
			.map(MessageFileDto::of)
			.collect(Collectors.toList());
	}

	public void delete(Long messageId) {
		redisTemplate.opsForHash().delete(generateRedisKey(messageId));
	}

	private String generateRedisKey(Long messageId) {
		return REDIS_PREFIX_KEY + DELIMITER + messageId;
	}

}
