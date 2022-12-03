package inha.tnt.hbc.application.alarm.service;

import static inha.tnt.hbc.util.Constants.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import inha.tnt.hbc.domain.alarm.dto.AlarmDto;
import inha.tnt.hbc.domain.alarm.dto.AlarmDto.FriendAlarmDto;
import inha.tnt.hbc.domain.alarm.entity.Alarm;
import inha.tnt.hbc.domain.alarm.entity.FriendAlarm;
import inha.tnt.hbc.domain.alarm.service.AlarmService;
import inha.tnt.hbc.domain.member.entity.Member;
import inha.tnt.hbc.domain.member.service.FriendService;
import inha.tnt.hbc.model.alarm.dto.AlarmPageResponse;
import inha.tnt.hbc.util.SecurityContextUtils;

@Service
@RequiredArgsConstructor
public class AlarmFacadeService {

	private final SecurityContextUtils securityContextUtils;
	private final AlarmService alarmService;
	private final FriendService friendService;

	public AlarmPageResponse getAlarms(int page, int size) {
		final Member receiver = securityContextUtils.takeoutMember();
		final Pageable pageable = PageRequest.of(page - PAGE_CORRECTION_VALUE, size);
		final Page<Alarm> alarmPage = alarmService.findAllByReceiver(receiver, pageable);
		final List<Alarm> alarms = alarmPage.getContent();
		final List<AlarmDto> alarmDtos = alarmService.findAllAlarmDtoByAlarms(alarms);
		if (hasFriendAlarm(alarmDtos)) {
			updateAllFriendAlarmDto(receiver, alarms, alarmDtos);
		}
		return AlarmPageResponse.of(new PageImpl<>(alarmDtos, pageable, alarmPage.getTotalElements()));
	}

	private void updateAllFriendAlarmDto(Member receiver, List<Alarm> alarms, List<AlarmDto> alarmDtos) {
		final List<Long> friendAlarmSenderIds = convertAllFriendAlarmSenderId(alarms);
		final Set<Long> followMemberIdBucket = getFollowingBucket(receiver, friendAlarmSenderIds);
		alarmDtos.stream()
			.filter(alarmDto -> alarmDto.getAlarmType().equals(FriendAlarm.DTYPE))
			.map(AlarmDto::getFriendAlarm)
			.forEach(alarmDto -> {
				if (followMemberIdBucket.contains(alarmDto.getMember().getId())) {
					alarmDto.raiseUpFollowFlag();
				}
			});
	}

	private Set<Long> getFollowingBucket(Member receiver, List<Long> friendAlarmSenderIds) {
		return friendService
			.findAllByMemberIdAndFriendMemberIdIn(receiver.getId(), friendAlarmSenderIds)
			.stream()
			.map(friend -> friend.getFriendMember().getId())
			.collect(Collectors.toSet());
	}

	private List<Long> convertAllFriendAlarmSenderId(List<Alarm> alarms) {
		return alarms.stream()
			.filter(alarm -> alarm.getDtype().equals(FriendAlarm.DTYPE))
			.map(alarm -> alarm.getSender().getId())
			.collect(Collectors.toList());
	}

	private boolean hasFriendAlarm(List<AlarmDto> alarmDtos) {
		return alarmDtos.stream().anyMatch(alarmDto -> alarmDto.getAlarmType().equals(FriendAlarm.DTYPE));
	}

}
