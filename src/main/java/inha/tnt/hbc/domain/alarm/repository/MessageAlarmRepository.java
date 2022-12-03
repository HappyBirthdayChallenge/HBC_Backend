package inha.tnt.hbc.domain.alarm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import inha.tnt.hbc.domain.alarm.entity.MessageAlarm;

public interface MessageAlarmRepository extends JpaRepository<MessageAlarm, Long>, MessageAlarmRepositoryQuerydsl {

}
