package inha.tnt.hbc.domain.alarm.entity;

import static inha.tnt.hbc.util.Constants.*;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import inha.tnt.hbc.domain.member.entity.Member;
import inha.tnt.hbc.domain.room.entity.Room;

@Getter
@Entity
@Table(name = "room_alarms")
@DiscriminatorValue("ROOM")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoomAlarm extends Alarm {

	private final static String ALARM_MESSAGE = "%s님의 생일이 %d일 전이에요, 미리 생일 축하 메시지를 남겨보면 어떨까요?";

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "room_id")
	private Room room;

	@Builder
	public RoomAlarm(Member member, Room room) {
		super(room.getMember(), member,
			String.format(ALARM_MESSAGE, room.getMember().getName(), DAYS_BEFORE_ROOM_CREATION));
		this.room = room;
	}

}
