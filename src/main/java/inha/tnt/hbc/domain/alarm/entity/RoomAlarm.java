package inha.tnt.hbc.domain.alarm.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import inha.tnt.hbc.domain.member.entity.Member;
import inha.tnt.hbc.domain.room.entity.Room;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "room_alarms")
@DiscriminatorValue("ROOM")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoomAlarm extends Alarm {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "room_id")
	private Room room;
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "alarm_id")
	private Alarm alarm;

	@Builder
	public RoomAlarm(Member sender, Member receiver, AlarmStatus status, String content, Room room, Alarm alarm) {
		super(sender, receiver, status, content);
		this.room = room;
		this.alarm = alarm;
	}

}
