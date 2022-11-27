package inha.tnt.hbc.domain.alarm.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import inha.tnt.hbc.domain.alarm.entity.Alarm;
import inha.tnt.hbc.domain.alarm.entity.FriendAlarm;
import inha.tnt.hbc.domain.alarm.entity.MessageAlarm;
import inha.tnt.hbc.domain.alarm.entity.RoomAlarm;
import inha.tnt.hbc.domain.alarm.repository.AlarmRepository;
import inha.tnt.hbc.domain.member.entity.Friend;
import inha.tnt.hbc.domain.member.entity.Member;
import inha.tnt.hbc.domain.message.entity.Message;
import inha.tnt.hbc.domain.room.entity.Room;
import inha.tnt.hbc.infra.push.firebase.NotificationService;

@Service
@RequiredArgsConstructor
public class AlarmService {

	private final AlarmRepository alarmRepository;
	private final NotificationService notificationService;

	public void alarmMessage(Message message) {
		final Alarm alarm = MessageAlarm.builder()
			.message(message)
			.build();
		alarmRepository.save(alarm);
		notificationService.sendNotification(message.getRoom().getMember().getId(), alarm.getMessage());
	}

	public void alarmRoom(Room room, List<Member> members) {
		// TODO: jdbc batch insert
		final List<Alarm> alarms = members.stream()
			.map(member -> RoomAlarm.builder()
				.room(room)
				.member(member)
				.build())
			.collect(Collectors.toList());
		alarmRepository.saveAll(alarms);
		final List<Long> memberIds = members.stream()
			.map(Member::getId)
			.collect(Collectors.toList());
		notificationService.sendNotifications(memberIds, alarms.get(0).getMessage());
	}

	public void alarmFriend(Friend friend) {
		final Alarm alarm = FriendAlarm.builder()
			.friend(friend)
			.build();
		alarmRepository.save(alarm);
		notificationService.sendNotification(friend.getFriendMember().getId(), alarm.getMessage());
	}

}
