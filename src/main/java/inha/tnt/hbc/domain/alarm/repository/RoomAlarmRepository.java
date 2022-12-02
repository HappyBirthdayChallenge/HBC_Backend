package inha.tnt.hbc.domain.alarm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import inha.tnt.hbc.domain.alarm.entity.RoomAlarm;

public interface RoomAlarmRepository extends JpaRepository<RoomAlarm, Long> {

}
