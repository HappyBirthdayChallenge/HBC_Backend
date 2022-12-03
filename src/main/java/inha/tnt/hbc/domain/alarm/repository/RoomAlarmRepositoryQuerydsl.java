package inha.tnt.hbc.domain.alarm.repository;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import inha.tnt.hbc.domain.alarm.entity.RoomAlarm;

@Transactional(readOnly = true)
public interface RoomAlarmRepositoryQuerydsl {

	List<RoomAlarm> findAllFetchRoomAndRoomMemberByAlarmIdIn(List<Long> alarmIds);

}
