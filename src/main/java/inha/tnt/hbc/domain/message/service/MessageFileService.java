package inha.tnt.hbc.domain.message.service;

import static inha.tnt.hbc.domain.message.entity.MessageFileStatus.*;
import static inha.tnt.hbc.util.Constants.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import inha.tnt.hbc.application.file.dto.LocalFile;
import inha.tnt.hbc.util.TimeUtils;

@Service
@RequiredArgsConstructor
public class MessageFileService {

	private static final String REDIS_PREFIX_KEY = "mf";
	private final RedisTemplate<String, Object> redisTemplate;

	@Async
	public void save(Long messageId, String fileId, LocalFile localFile) {
		final Map<String, Map<String, String>> hashKey = new HashMap<>();
		hashKey.put(fileId, convert(messageId, localFile));
		redisTemplate.opsForHash().putAll(generateRedisKey(messageId), hashKey);
	}

	private Map<String, String> convert(Long messageId, LocalFile localFile) {
		final Map<String, String> value = new HashMap<>();
		value.put("id", messageId.toString());
		value.put("timestamp", TimeUtils.convertToString(LocalDate.now()));
		value.put("name", localFile.getName());
		value.put("type", localFile.getType());
		value.put("uuid", localFile.getUuid());
		value.put("status", COMPLETED.name());
		return value;
	}

	private String generateRedisKey(Long messageId) {
		return REDIS_PREFIX_KEY + DELIMITER + messageId;
	}

}
