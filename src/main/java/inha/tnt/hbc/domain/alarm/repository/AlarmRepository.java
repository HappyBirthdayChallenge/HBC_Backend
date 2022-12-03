package inha.tnt.hbc.domain.alarm.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import inha.tnt.hbc.domain.alarm.entity.Alarm;
import inha.tnt.hbc.domain.alarm.entity.AlarmStatus;
import inha.tnt.hbc.domain.member.entity.Member;

@Transactional(readOnly = true)
public interface AlarmRepository extends JpaRepository<Alarm, Long> {

	Page<Alarm> findAllByReceiverAndStatus(Member receiver, AlarmStatus status, Pageable pageable);

}
