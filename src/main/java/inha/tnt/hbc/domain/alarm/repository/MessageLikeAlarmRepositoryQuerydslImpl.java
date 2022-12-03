package inha.tnt.hbc.domain.alarm.repository;

import static inha.tnt.hbc.domain.alarm.entity.QMessageLikeAlarm.*;
import static inha.tnt.hbc.domain.member.entity.QMember.*;
import static inha.tnt.hbc.domain.message.entity.QAnimation.*;
import static inha.tnt.hbc.domain.message.entity.QDecoration.*;
import static inha.tnt.hbc.domain.message.entity.QMessage.*;
import static inha.tnt.hbc.domain.room.entity.QRoom.*;

import java.util.List;

import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

import inha.tnt.hbc.domain.alarm.entity.MessageLikeAlarm;

@RequiredArgsConstructor
public class MessageLikeAlarmRepositoryQuerydslImpl implements MessageLikeAlarmRepositoryQuerydsl {

	private final JPAQueryFactory queryFactory;

	@Override
	public List<MessageLikeAlarm> findAllFetchMessageAndMessageDecorationAndMessageAnimationAndMessageRoomMemberByAlarmIdIn(
		List<Long> alarmIds) {
		return queryFactory
			.selectFrom(messageLikeAlarm)
			.innerJoin(messageLikeAlarm.message, message).fetchJoin()
			.innerJoin(message.decoration, decoration).fetchJoin()
			.innerJoin(message.animation, animation).fetchJoin()
			.innerJoin(message.room, room).fetchJoin()
			.innerJoin(room.member, member).fetchJoin()
			.where(messageLikeAlarm.id.in(alarmIds))
			.fetch();
	}

}
