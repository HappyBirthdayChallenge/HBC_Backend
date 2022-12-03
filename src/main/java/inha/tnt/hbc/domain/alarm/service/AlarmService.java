package inha.tnt.hbc.domain.alarm.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import inha.tnt.hbc.domain.alarm.dto.AlarmDto;
import inha.tnt.hbc.domain.alarm.entity.Alarm;
import inha.tnt.hbc.domain.alarm.entity.AlarmStatus;
import inha.tnt.hbc.domain.alarm.entity.FriendAlarm;
import inha.tnt.hbc.domain.alarm.entity.MessageAlarm;
import inha.tnt.hbc.domain.alarm.entity.MessageLikeAlarm;
import inha.tnt.hbc.domain.alarm.entity.RoomAlarm;
import inha.tnt.hbc.domain.alarm.repository.AlarmRepository;
import inha.tnt.hbc.domain.alarm.repository.FriendAlarmRepository;
import inha.tnt.hbc.domain.alarm.repository.MessageAlarmRepository;
import inha.tnt.hbc.domain.alarm.repository.MessageLikeAlarmRepository;
import inha.tnt.hbc.domain.alarm.repository.RoomAlarmRepository;
import inha.tnt.hbc.domain.member.entity.Friend;
import inha.tnt.hbc.domain.member.entity.Member;
import inha.tnt.hbc.domain.message.entity.Message;
import inha.tnt.hbc.domain.room.entity.Room;
import inha.tnt.hbc.infra.push.firebase.NotificationService;

@Service
@RequiredArgsConstructor
public class AlarmService {

	private final AlarmRepository alarmRepository;
	private final RoomAlarmRepository roomAlarmRepository;
	private final MessageAlarmRepository messageAlarmRepository;
	private final MessageLikeAlarmRepository messageLikeAlarmRepository;
	private final FriendAlarmRepository friendAlarmRepository;
	private final NotificationService notificationService;

	public void alarmMessage(Message message) {
		final Alarm alarm = MessageAlarm.builder()
			.message(message)
			.build();
		alarmRepository.save(alarm);
		notificationService.sendNotification(message.getRoom().getMember().getId(), alarm.getContent());
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
		notificationService.sendNotifications(memberIds, alarms.get(0).getContent());
	}

	public void alarmFriend(Friend friend) {
		final Alarm alarm = FriendAlarm.builder()
			.friend(friend)
			.build();
		alarmRepository.save(alarm);
		notificationService.sendNotification(friend.getFriendMember().getId(), alarm.getContent());
	}

	public void alarmMessageLike(Message message) {
		final Alarm alarm = MessageLikeAlarm.builder()
			.message(message)
			.build();
		alarmRepository.save(alarm);
		notificationService.sendNotification(message.getMember().getId(), alarm.getContent());
	}

	public Page<Alarm> findAllByReceiver(Member receiver, Pageable pageable) {
		return alarmRepository.findAllByReceiverAndStatus(receiver, AlarmStatus.CREATED, pageable);
	}

	public List<AlarmDto> findAllAlarmDtoByAlarms(List<Alarm> alarms) {
		final Map<String, List<Alarm>> alarmMap = alarms.stream()
			.collect(Collectors.groupingBy(Alarm::getDtype));
		return alarmMap.entrySet()
			.stream()
			.map(entry -> {
				final List<Long> alarmIds = entry.getValue()
					.stream()
					.map(Alarm::getId)
					.collect(Collectors.toList());
				switch (entry.getKey()) {
					case RoomAlarm.DTYPE:
						return findRoomAlarmDtos(alarmIds);
					case MessageAlarm.DTYPE:
						return findMessageAlarmDtos(alarmIds);
					case MessageLikeAlarm.DTYPE:
						return findMessageLikeAlarmDtos(alarmIds);
					case FriendAlarm.DTYPE:
						return findFriendAlarmDtos(alarmIds);
					default:
						throw new RuntimeException("DTYPE 동기화가 필요합니다.");
				}
			})
			.flatMap(List::stream)
			.collect(Collectors.toList());
	}

	public void deleteFriendAlarm(Friend friend) {
		friendAlarmRepository.deleteByFriend(friend);
	}

	private List<AlarmDto> findFriendAlarmDtos(List<Long> alarmIds) {
		return friendAlarmRepository.findAllFetchFriendAndFriendMemberByAlarmIdIn(alarmIds)
			.stream()
			.map(AlarmDto::of)
			.collect(Collectors.toList());
	}

	private List<AlarmDto> findMessageLikeAlarmDtos(List<Long> alarmIds) {
		return messageLikeAlarmRepository
			.findAllFetchMessageAndMessageDecorationAndMessageAnimationAndMessageRoomMemberByAlarmIdIn(alarmIds)
			.stream()
			.map(AlarmDto::of)
			.collect(Collectors.toList());
	}

	private List<AlarmDto> findMessageAlarmDtos(List<Long> alarmIds) {
		return messageAlarmRepository.findAllFetchMessageAndMessageDecorationMessageAnimationByAlarmIdIn(alarmIds)
			.stream()
			.map(AlarmDto::of)
			.collect(Collectors.toList());
	}

	private List<AlarmDto> findRoomAlarmDtos(List<Long> alarmIds) {
		return roomAlarmRepository.findAllFetchRoomAndRoomMemberByAlarmIdIn(alarmIds)
			.stream()
			.map(AlarmDto::of)
			.collect(Collectors.toList());
	}

}
