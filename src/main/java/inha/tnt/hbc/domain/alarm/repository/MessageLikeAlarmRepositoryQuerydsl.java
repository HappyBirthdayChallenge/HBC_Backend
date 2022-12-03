package inha.tnt.hbc.domain.alarm.repository;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import inha.tnt.hbc.domain.alarm.entity.MessageLikeAlarm;

@Transactional(readOnly = true)
public interface MessageLikeAlarmRepositoryQuerydsl {

	List<MessageLikeAlarm> findAllFetchMessageAndMessageDecorationAndMessageAnimationAndMessageRoomMemberByAlarmIdIn(List<Long> alarmIds);

}
