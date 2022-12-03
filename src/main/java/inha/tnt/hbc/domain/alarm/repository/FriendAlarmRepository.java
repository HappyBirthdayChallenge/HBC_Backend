package inha.tnt.hbc.domain.alarm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import inha.tnt.hbc.domain.alarm.entity.FriendAlarm;
import inha.tnt.hbc.domain.alarm.repository.querydsl.FriendAlarmRepositoryQuerydsl;
import inha.tnt.hbc.domain.member.entity.Friend;

public interface FriendAlarmRepository extends JpaRepository<FriendAlarm, Long>, FriendAlarmRepositoryQuerydsl {

	void deleteByFriend(Friend friend);

}
