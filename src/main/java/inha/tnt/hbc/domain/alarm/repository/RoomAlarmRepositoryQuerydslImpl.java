package inha.tnt.hbc.domain.alarm.repository;

import static inha.tnt.hbc.domain.alarm.entity.QRoomAlarm.*;
import static inha.tnt.hbc.domain.member.entity.QMember.*;
import static inha.tnt.hbc.domain.room.entity.QRoom.*;

import java.util.List;

import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

import inha.tnt.hbc.domain.alarm.entity.RoomAlarm;

@RequiredArgsConstructor
public class RoomAlarmRepositoryQuerydslImpl implements RoomAlarmRepositoryQuerydsl {

	private final JPAQueryFactory queryFactory;

	@Override
	public List<RoomAlarm> findAllFetchRoomAndRoomMemberByAlarmIdIn(List<Long> alarmIds) {
		return queryFactory
			.selectFrom(roomAlarm)
			.innerJoin(roomAlarm.room, room).fetchJoin()
			.innerJoin(room.member, member).fetchJoin()
			.where(roomAlarm.id.in(alarmIds))
			.fetch();
	}

}
