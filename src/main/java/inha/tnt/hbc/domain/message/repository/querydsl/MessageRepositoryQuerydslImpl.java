package inha.tnt.hbc.domain.message.repository.querydsl;

import static inha.tnt.hbc.domain.member.entity.QMember.*;
import static inha.tnt.hbc.domain.message.entity.MessageStatus.*;
import static inha.tnt.hbc.domain.message.entity.QAnimation.*;
import static inha.tnt.hbc.domain.message.entity.QDecoration.*;
import static inha.tnt.hbc.domain.message.entity.QMessage.*;
import static inha.tnt.hbc.domain.message.entity.QMessageFile.*;
import static inha.tnt.hbc.domain.room.entity.QRoom.*;

import java.util.Optional;

import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

import inha.tnt.hbc.domain.message.entity.Message;

@RequiredArgsConstructor
public class MessageRepositoryQuerydslImpl implements MessageRepositoryQuerydsl {

	private final JPAQueryFactory queryFactory;

	@Override
	public Optional<Message> findFetchRoomAndDecorationAndAnimationAndMessageFilesById(Long messageId) {
		return Optional.ofNullable(queryFactory
			.selectFrom(message)
			.where(message.id.eq(messageId).and(message.status.eq(WRITTEN)))
			.innerJoin(message.room, room).fetchJoin()
			.innerJoin(message.animation, animation).fetchJoin()
			.innerJoin(message.decoration, decoration).fetchJoin()
			.innerJoin(message.messageFiles, messageFile).fetchJoin()
			.fetchFirst()
		);
	}

	@Override
	public Optional<Message> findFetchRoomMemberByIdAndMemberId(Long messageId, Long memberId) {
		return Optional.ofNullable(queryFactory
			.selectFrom(message)
			.where(message.id.eq(messageId).and(message.member.id.eq(memberId)))
			.innerJoin(message.room, room).fetchJoin()
			.innerJoin(room.member, member).fetchJoin()
			.fetchFirst()
		);
	}

}
