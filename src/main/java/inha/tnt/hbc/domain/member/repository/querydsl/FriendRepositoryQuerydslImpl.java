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

import inha.tnt.hbc.domain.member.dto.FriendDto;
import inha.tnt.hbc.domain.member.dto.QFriendDto;
import inha.tnt.hbc.domain.member.entity.Member;

@RequiredArgsConstructor
public class FriendRepositoryQuerydslImpl implements FriendRepositoryQuerydsl {

	private final JPAQueryFactory queryFactory;

	@Override
	public Page<FriendDto> findFollowingDtoPage(Long memberId, Pageable pageable) {
		final List<FriendDto> content = queryFactory
			.select(new QFriendDto(friend.friendMember))
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
	public Page<FriendDto> findFollowerDtoPage(Long memberId, PageRequest pageable) {
		final List<FriendDto> content = queryFactory
			.select(new QFriendDto(friend.member))
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

	@Override
	public List<Member> findTop20FollowersByUsernameStartsWithOrNameStartsWith(Long memberId, String keyword) {
		return queryFactory
			.select(friend.member)
			.from(friend)
			.innerJoin(friend.member, member)
			.where(
				friend.friendMember.id.eq(memberId).and(
					friend.member.username.startsWith(keyword).or(
						friend.member.name.startsWith(keyword)
					)
				)
			)
			.limit(20)
			.fetch();
	}

	@Override
	public List<Member> findTop20FollowingsByUsernameStartsWithOrNameStartsWith(Long memberId, String keyword) {
		return queryFactory
			.select(friend.friendMember)
			.from(friend)
			.innerJoin(friend.friendMember, member)
			.where(
				friend.member.id.eq(memberId).and(
					friend.friendMember.username.startsWith(keyword).or(
						friend.friendMember.name.startsWith(keyword)
					)
				)
			)
			.limit(20)
			.fetch();
	}

}
