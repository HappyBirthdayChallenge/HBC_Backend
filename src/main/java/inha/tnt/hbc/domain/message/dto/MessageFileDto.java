package inha.tnt.hbc.domain.message.dto;

import static inha.tnt.hbc.domain.message.entity.MessageFileStatus.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import inha.tnt.hbc.application.file.dto.LocalFile;
import inha.tnt.hbc.domain.message.entity.Message;
import inha.tnt.hbc.domain.message.entity.MessageFile;
import inha.tnt.hbc.domain.message.entity.MessageFileStatus;
import inha.tnt.hbc.domain.message.entity.MessageFileTypes;
import inha.tnt.hbc.util.TimeUtils;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MessageFileDto {

	private final static String KEY_TIMESTAMP = "timestamp";
	private final static String KEY_NAME = "name";
	private final static String KEY_TYPE = "type";
	private final static String KEY_UUID = "uuid";
	private final static String KEY_STATUS = "status";
	private String timestamp;
	private String name;
	private MessageFileTypes type;
	private String uuid;
	private MessageFileStatus status;

	public static MessageFileDto of(LocalFile localFile) {
		return MessageFileDto.builder()
			.timestamp(TimeUtils.convertToString(LocalDate.now()))
			.name(localFile.getName())
			.type(MessageFileTypes.valueOf(localFile.getType().toUpperCase()))
			.uuid(localFile.getUuid())
			.status(COMPLETED)
			.build();
	}

	public static MessageFileDto of(Map<String, String> value) {
		return MessageFileDto.builder()
			.timestamp(value.get(KEY_TIMESTAMP))
			.name(value.get(KEY_NAME))
			.type(MessageFileTypes.valueOf(value.get(KEY_TYPE).toUpperCase()))
			.uuid(value.get(KEY_UUID))
			.status(MessageFileStatus.valueOf(value.get(KEY_STATUS).toUpperCase()))
			.build();
	}

	public Map<String, String> convertToMap() {
		final Map<String, String> value = new HashMap<>();
		value.put(KEY_TIMESTAMP, this.timestamp);
		value.put(KEY_NAME, this.name);
		value.put(KEY_TYPE, this.type.name());
		value.put(KEY_UUID, this.uuid);
		value.put(KEY_STATUS, this.status.name());
		return value;
	}

	public MessageFile convertToEntity(Message message) {
		return MessageFile.builder()
			.message(message)
			.name(this.name)
			.type(this.type)
			.uuid(this.uuid)
			.build();
	}

}
