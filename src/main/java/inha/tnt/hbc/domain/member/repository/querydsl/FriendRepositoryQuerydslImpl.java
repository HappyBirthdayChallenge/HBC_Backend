package inha.tnt.hbc.domain.member.repository.querydsl;

import static inha.tnt.hbc.domain.member.entity.QFriend.*;
import static inha.tnt.hbc.domain.member.entity.QMember.*;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.querydsl.jpa.impl.JPAQueryFactory;

import inha.tnt.hbc.domain.member.dto.FriendDto;
import inha.tnt.hbc.domain.member.dto.QFriendDto;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FriendRepositoryQuerydslImpl implements FriendRepositoryQuerydsl {

	private final JPAQueryFactory queryFactory;

	@Override
	public Page<FriendDto> findFriendDtoPage(Long memberId, Pageable pageable) {
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
			.innerJoin(friend.friendMember, member)
			.where(friend.member.id.eq(memberId))
			.fetch()
			.size();

		return new PageImpl<>(content, pageable, total);
	}

}
