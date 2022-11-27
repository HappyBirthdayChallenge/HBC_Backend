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

import inha.tnt.hbc.domain.member.entity.Friend;

@Entity
@Table(name = "friend_alarms")
@DiscriminatorValue("FRIEND")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FriendAlarm extends Alarm {

	private final static String ALARM_MESSAGE = "%s님이 회원님을 친구로 추가했어요!";

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "friend_id")
	private Friend friend;

	@Builder
	public FriendAlarm(Friend friend) {
		super(friend.getMember(), friend.getFriendMember(), String.format(ALARM_MESSAGE, friend.getMember().getName()));
		this.friend = friend;
	}

	@Override
	public String getMessage() {
		return String.format(ALARM_MESSAGE, super.getSender().getName());
	}

}
