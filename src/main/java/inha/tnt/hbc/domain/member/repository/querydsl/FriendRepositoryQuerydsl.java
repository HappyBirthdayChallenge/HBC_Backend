package inha.tnt.hbc.domain.member.repository.querydsl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import inha.tnt.hbc.domain.member.dto.FollowerDto;
import inha.tnt.hbc.domain.member.dto.FollowingDto;
import inha.tnt.hbc.domain.member.entity.Member;

@Transactional(readOnly = true)
public interface FriendRepositoryQuerydsl {

	Page<FollowingDto> findFollowingDtoPage(Long memberId, Pageable pageable);

	Page<FollowerDto> findFollowerDtoPage(Long memberId, PageRequest pageable);

	List<Member> findTop20FollowersByUsernameStartsWithOrNameStartsWith(Long memberId, String keyword);

	List<Member> findTop20FollowingsByUsernameStartsWithOrNameStartsWith(Long memberId, String keyword);

}
