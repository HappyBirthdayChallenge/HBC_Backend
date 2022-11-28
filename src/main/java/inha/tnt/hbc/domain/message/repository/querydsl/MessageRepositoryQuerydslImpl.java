package inha.tnt.hbc.domain.message.repository.querydsl;

import static inha.tnt.hbc.domain.member.entity.QMember.*;
import static inha.tnt.hbc.domain.message.entity.MessageStatus.*;
import static inha.tnt.hbc.domain.message.entity.QAnimation.*;
import static inha.tnt.hbc.domain.message.entity.QDecoration.*;
import static inha.tnt.hbc.domain.message.entity.QMessage.*;
import static inha.tnt.hbc.domain.message.entity.QMessageFile.*;
import static inha.tnt.hbc.domain.room.entity.QRoom.*;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

import inha.tnt.hbc.domain.message.entity.Message;
import inha.tnt.hbc.domain.message.entity.MessageStatus;
import inha.tnt.hbc.domain.room.dto.QRoomMessageDto;
import inha.tnt.hbc.domain.room.dto.RoomMessageDto;
import inha.tnt.hbc.domain.room.entity.Room;

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

	@Override
	public Optional<Message> findFetchRoomAndDecorationAndAnimationByIdAndMemberId(Long messageId, Long memberId) {
		return Optional.ofNullable(queryFactory
			.selectFrom(message)
			.where(message.id.eq(messageId).and(message.member.id.eq(memberId)))
			.innerJoin(message.room, room).fetchJoin()
			.innerJoin(message.decoration, decoration).fetchJoin()
			.innerJoin(message.animation, animation).fetchJoin()
			.fetchFirst()
		);
	}

	@Override
	public Page<RoomMessageDto> findRoomMessageDtoByRoom(Room room, Pageable pageable) {
		final List<RoomMessageDto> content = queryFactory
			.select(new QRoomMessageDto(message))
			.from(message)
			.where(message.room.id.eq(room.getId()).and(message.status.eq(WRITTEN)))
			.innerJoin(message.decoration, decoration)
			.innerJoin(message.member, member)
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.orderBy(message.id.desc())
			.fetch();

		final int total = queryFactory
			.selectFrom(message)
			.where(message.room.id.eq(room.getId()).and(message.status.eq(WRITTEN)))
			.fetch()
			.size();

		return new PageImpl<>(content, pageable, total);
	}

}
