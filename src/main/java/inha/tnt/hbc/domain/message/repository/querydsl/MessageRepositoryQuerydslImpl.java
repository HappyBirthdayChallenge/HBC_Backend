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

import inha.tnt.hbc.domain.message.dto.MessageWrittenByMeDto;
import inha.tnt.hbc.domain.message.dto.QMessageWrittenByMeDto;
import inha.tnt.hbc.domain.message.entity.Message;
import inha.tnt.hbc.domain.room.dto.QRoomMessageDto;
import inha.tnt.hbc.domain.room.dto.RoomMessageDto;
import inha.tnt.hbc.domain.room.entity.Room;

@RequiredArgsConstructor
public class MessageRepositoryQuerydslImpl implements MessageRepositoryQuerydsl {

	private final JPAQueryFactory queryFactory;

	@Override
	public Optional<Message> findFetchRoomAndMemberAndDecorationAndAnimationAndMessageFilesById(Long messageId) {
		return Optional.ofNullable(queryFactory
			.selectFrom(message)
			.where(message.id.eq(messageId).and(message.status.eq(WRITTEN)))
			.innerJoin(message.room, room).fetchJoin()
			.innerJoin(message.member, member).fetchJoin()
			.innerJoin(message.animation, animation).fetchJoin()
			.innerJoin(message.decoration, decoration).fetchJoin()
			.leftJoin(message.messageFiles, messageFile).fetchJoin()
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
			.innerJoin(message.decoration, decoration).fetchJoin()
			.innerJoin(message.member, member).fetchJoin()
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

	@Override
	public Page<MessageWrittenByMeDto> findMessageWrittenByMeDtoByMemberId(Long memberId, Pageable pageable) {
		final List<MessageWrittenByMeDto> content = queryFactory
			.select(new QMessageWrittenByMeDto(message))
			.from(message)
			.where(message.member.id.eq(memberId).and(message.status.eq(WRITTEN)))
			.innerJoin(message.decoration, decoration).fetchJoin()
			.innerJoin(message.room, room).fetchJoin()
			.innerJoin(room.member, member).fetchJoin()
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.orderBy(message.id.desc())
			.fetch();

		final int total = queryFactory
			.selectFrom(message)
			.where(message.member.id.eq(memberId).and(message.status.eq(WRITTEN)))
			.fetch()
			.size();

		return new PageImpl<>(content, pageable, total);
	}

	@Override
	public int countByMemberIdAndRoomIdAndMessageIdAndDecorationCategory(Long memberId, Long roomId, Long messageId,
		String decorationCategory) {
		return queryFactory
			.selectFrom(message)
			.where(message.room.id.eq(roomId)
				.and(message.status.eq(WRITTEN))
				.and(message.decoration.category.eq(decorationCategory))
				.and(message.id.loe(messageId)))
			.innerJoin(message.decoration, decoration)
			.fetch()
			.size();
	}

	@Override
	public Optional<Message> findFetchMemberAndRoomMemberById(Long messageId) {
		return Optional.ofNullable(queryFactory
			.selectFrom(message)
			.where(message.id.eq(messageId))
			.innerJoin(message.member, member).fetchJoin()
			.innerJoin(message.room, room).fetchJoin()
			.innerJoin(room.member, member).fetchJoin()
			.fetchFirst()
		);
	}

}
