package inha.tnt.hbc.domain.alarm.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import inha.tnt.hbc.domain.member.entity.Member;
import inha.tnt.hbc.domain.message.entity.Message;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "message_alarms")
@DiscriminatorValue("MESSAGE")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MessageAlarm extends Alarm {

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "message_id")
	private Message message;
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "alarm_id")
	private Alarm alarm;

	@Builder
	public MessageAlarm(Member sender, Member receiver,
		AlarmStatus status, String content, Message message, Alarm alarm) {
		super(sender, receiver, status, content);
		this.message = message;
		this.alarm = alarm;
	}

}
