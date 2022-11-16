package inha.tnt.hbc.domain.member.repository.querydsl;

import static inha.tnt.hbc.domain.member.entity.QMember.*;
import static inha.tnt.hbc.domain.room.entity.QRoom.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

import inha.tnt.hbc.domain.member.entity.Member;

@RequiredArgsConstructor
public class MemberRepositoryQuerydslImpl implements MemberRepositoryQuerydsl {

	private final JPAQueryFactory queryFactory;

	@Override
	public List<Member> findAllByRoomCreateDate(LocalDate roomCreateDate) {
		return queryFactory
			.selectFrom(member)
			.where(
				eqBirthDateMonthAndDate(roomCreateDate)
				.and(member.id.notIn(findMemberIdsByCreateAtWithinCurrentYear(roomCreateDate)))
			)
			.fetch();
	}

	private BooleanExpression eqBirthDateMonthAndDate(LocalDate roomCreateDate) {
		return member.birthDate.month.eq(roomCreateDate.getMonthValue())
			.and(member.birthDate.date.eq(roomCreateDate.getDayOfMonth()));
	}

	private JPQLQuery<Long> findMemberIdsByCreateAtWithinCurrentYear(LocalDate roomCreateDate) {
		final LocalDateTime start = LocalDateTime.of(roomCreateDate.getYear(), 1, 1, 0, 0, 0);
		final LocalDateTime end = start.plusYears(1);
		return JPAExpressions
			.select(room.member.id)
			.from(room)
			.where(room.createAt.goe(start)
				.and(room.createAt.lt(end))
			);
	}

}
