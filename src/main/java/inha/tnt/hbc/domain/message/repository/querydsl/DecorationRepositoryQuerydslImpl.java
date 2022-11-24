package inha.tnt.hbc.domain.message.repository.querydsl;

import static inha.tnt.hbc.domain.message.entity.MessageStatus.*;
import static inha.tnt.hbc.domain.message.entity.QDecoration.*;
import static inha.tnt.hbc.domain.message.entity.QMessage.*;
import static inha.tnt.hbc.domain.room.entity.QRoom.*;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

import inha.tnt.hbc.domain.message.entity.Decoration;

@RequiredArgsConstructor
public class DecorationRepositoryQuerydslImpl implements DecorationRepositoryQuerydsl {

	private final JPAQueryFactory queryFactory;

	@Override
	public Page<Decoration> findAllByRoomIdAndCategory(Long roomId, String category, Pageable pageable) {
		final List<Decoration> content = queryFactory
			.selectFrom(decoration)
			.innerJoin(decoration.message, message)
			.innerJoin(message.room, room)
			.where(decoration.category.eq(category)
				.and(decoration.message.room.id.eq(roomId))
				.and(decoration.message.status.eq(WRITTEN))
			)
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		final int total = queryFactory
			.selectFrom(decoration)
			.innerJoin(decoration.message, message)
			.innerJoin(message.room, room)
			.where(decoration.category.eq(category).and(decoration.message.room.id.eq(roomId)))
			.fetch()
			.size();

		return new PageImpl<>(content, pageable, total);
	}

}
