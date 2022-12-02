package inha.tnt.hbc.domain.member.repository.querydsl;

import static inha.tnt.hbc.domain.member.entity.QFriend.*;
import static inha.tnt.hbc.domain.member.entity.QMember.*;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

import inha.tnt.hbc.domain.member.dto.FollowerDto;
import inha.tnt.hbc.domain.member.dto.FollowingDto;
import inha.tnt.hbc.domain.member.dto.QFollowerDto;
import inha.tnt.hbc.domain.member.dto.QFollowingDto;

@RequiredArgsConstructor
public class FriendRepositoryQuerydslImpl implements FriendRepositoryQuerydsl {

	private final JPAQueryFactory queryFactory;

	@Override
	public Page<FollowingDto> findFollowingDtoPage(Long memberId, Pageable pageable) {
		final List<FollowingDto> content = queryFactory
			.select(new QFollowingDto(friend.friendMember))
			.from(friend)
			.innerJoin(friend.friendMember, member)
			.where(friend.member.id.eq(memberId))
			.limit(pageable.getPageSize())
			.offset(pageable.getOffset())
			.orderBy(friend.friendMember.username.asc())
			.fetch();

		final int total = queryFactory
			.selectFrom(friend)
			.where(friend.member.id.eq(memberId))
			.fetch()
			.size();

		return new PageImpl<>(content, pageable, total);
	}

	@Override
	public Page<FollowerDto> findFollowerDtoPage(Long memberId, PageRequest pageable) {
		final List<FollowerDto> content = queryFactory
			.select(new QFollowerDto(friend.member))
			.from(friend)
			.innerJoin(friend.member, member)
			.where(friend.friendMember.id.eq(memberId))
			.limit(pageable.getPageSize())
			.offset(pageable.getOffset())
			.orderBy(friend.member.username.asc())
			.fetch();

		final int total = queryFactory
			.selectFrom(friend)
			.where(friend.friendMember.id.eq(memberId))
			.fetch()
			.size();

		return new PageImpl<>(content, pageable, total);
	}

}
