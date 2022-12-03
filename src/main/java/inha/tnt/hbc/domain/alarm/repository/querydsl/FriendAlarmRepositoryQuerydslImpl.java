package inha.tnt.hbc.domain.alarm.repository.querydsl;

import static inha.tnt.hbc.domain.alarm.entity.QFriendAlarm.*;
import static inha.tnt.hbc.domain.member.entity.QFriend.*;
import static inha.tnt.hbc.domain.member.entity.QMember.*;

import java.util.List;

import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

import inha.tnt.hbc.domain.alarm.entity.FriendAlarm;

@RequiredArgsConstructor
public class FriendAlarmRepositoryQuerydslImpl implements FriendAlarmRepositoryQuerydsl {

	private final JPAQueryFactory queryFactory;

	@Override
	public List<FriendAlarm> findAllFetchFriendAndFriendMemberByAlarmIdIn(List<Long> alarmIds) {
		return queryFactory
			.selectFrom(friendAlarm)
			.innerJoin(friendAlarm.friend, friend).fetchJoin()
			.innerJoin(friend.member, member).fetchJoin()
			.where(friendAlarm.id.in(alarmIds))
			.fetch();
	}

}
