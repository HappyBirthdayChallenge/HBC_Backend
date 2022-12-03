package inha.tnt.hbc.domain.alarm.repository.querydsl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import inha.tnt.hbc.domain.alarm.entity.FriendAlarm;

@Transactional(readOnly = true)
public interface FriendAlarmRepositoryQuerydsl {

	List<FriendAlarm> findAllFetchFriendAndFriendMemberByAlarmIdIn(List<Long> alarmIds);

}
