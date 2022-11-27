package inha.tnt.hbc.domain.alarm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import inha.tnt.hbc.domain.alarm.entity.Alarm;

public interface AlarmRepository extends JpaRepository<Alarm, Long> {

}
