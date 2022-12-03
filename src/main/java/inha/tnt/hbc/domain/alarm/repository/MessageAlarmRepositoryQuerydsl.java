package inha.tnt.hbc.domain.alarm.repository;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import inha.tnt.hbc.domain.alarm.entity.MessageAlarm;

@Transactional(readOnly = true)
public interface MessageAlarmRepositoryQuerydsl {

	List<MessageAlarm> findAllFetchMessageAndMessageDecorationMessageAnimationByAlarmIdIn(List<Long> alarmIds);

}
