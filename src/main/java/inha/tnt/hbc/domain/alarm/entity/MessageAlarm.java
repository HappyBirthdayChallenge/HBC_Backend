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
@Table(name = "message_alarms")
@DiscriminatorValue("MESSAGE")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MessageAlarm extends Alarm {

	public final static String DTYPE = "MESSAGE";
	private final static String ALARM_MESSAGE = "누군가 내 파티룸에 축하 메시지를 작성했어요!";

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "message_id")
	private Message message;

	@Builder
	public MessageAlarm(Message message) {
		super(message.getMember(), message.getRoom().getMember(), ALARM_MESSAGE);
		this.message = message;
	}

}
