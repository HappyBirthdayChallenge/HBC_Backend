package inha.tnt.hbc.domain.alarm.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import inha.tnt.hbc.domain.member.entity.Member;
import inha.tnt.hbc.domain.message.entity.Message;

@Entity
@Table(name = "message_like_alarms")
@DiscriminatorValue("MESSAGE_LIKE")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MessageLikeAlarm extends Alarm {

	private final static String ALARM_MESSAGE = "%s님이 회원님이 작성한 축하 메시지를 좋아합니다.";

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "message_id")
	private Message message;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;

	@Builder
	public MessageLikeAlarm(Message message, Member member) {
		super(message.getMember(), message.getRoom().getMember(), String.format(ALARM_MESSAGE, member.getName()));
		this.message = message;
		this.member = member;
	}

}
