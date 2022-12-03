package inha.tnt.hbc.domain.alarm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import inha.tnt.hbc.domain.alarm.entity.MessageLikeAlarm;

public interface MessageLikeAlarmRepository extends JpaRepository<MessageLikeAlarm, Long>, MessageLikeAlarmRepositoryQuerydsl {

}
