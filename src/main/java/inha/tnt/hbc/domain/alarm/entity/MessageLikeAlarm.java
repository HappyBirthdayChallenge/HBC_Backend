package inha.tnt.hbc.domain.alarm.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import inha.tnt.hbc.domain.message.entity.Message;

@Getter
@Entity
@Table(name = "message_like_alarms")
@DiscriminatorValue("MESSAGE_LIKE")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MessageLikeAlarm extends Alarm {

	public final static String DTYPE = "MESSAGE_LIKE";
	private final static String ALARM_MESSAGE = "%s님이 회원님이 작성한 축하 메시지를 좋아합니다.";

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "message_id")
	private Message message;

	@Builder
	public MessageLikeAlarm(Message message) {
		super(message.getRoom().getMember(), message.getMember(),
			String.format(ALARM_MESSAGE, message.getMember().getName()));
		this.message = message;
	}

}
