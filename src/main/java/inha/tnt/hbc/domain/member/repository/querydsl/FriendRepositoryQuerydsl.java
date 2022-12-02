package inha.tnt.hbc.domain.member.repository.querydsl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import inha.tnt.hbc.domain.member.dto.FollowerDto;
import inha.tnt.hbc.domain.member.dto.FollowingDto;

@Transactional(readOnly = true)
public interface FriendRepositoryQuerydsl {

	Page<FollowingDto> findFollowingDtoPage(Long memberId, Pageable pageable);

	Page<FollowerDto> findFollowerDtoPage(Long memberId, PageRequest pageable);

}
