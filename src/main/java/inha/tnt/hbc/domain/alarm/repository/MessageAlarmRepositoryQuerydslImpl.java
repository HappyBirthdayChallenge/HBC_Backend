package inha.tnt.hbc.domain.alarm.repository;

import static inha.tnt.hbc.domain.alarm.entity.QMessageAlarm.*;
import static inha.tnt.hbc.domain.message.entity.QAnimation.*;
import static inha.tnt.hbc.domain.message.entity.QDecoration.*;
import static inha.tnt.hbc.domain.message.entity.QMessage.*;

import java.util.List;

import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

import inha.tnt.hbc.domain.alarm.entity.MessageAlarm;

@RequiredArgsConstructor
public class MessageAlarmRepositoryQuerydslImpl implements MessageAlarmRepositoryQuerydsl {

	private final JPAQueryFactory queryFactory;

	@Override
	public List<MessageAlarm> findAllFetchMessageAndMessageDecorationMessageAnimationByAlarmIdIn(List<Long> alarmIds) {
		return queryFactory
			.selectFrom(messageAlarm)
			.innerJoin(messageAlarm.message, message).fetchJoin()
			.innerJoin(message.decoration, decoration).fetchJoin()
			.innerJoin(message.animation, animation).fetchJoin()
			.where(messageAlarm.id.in(alarmIds))
			.fetch();
	}

}
