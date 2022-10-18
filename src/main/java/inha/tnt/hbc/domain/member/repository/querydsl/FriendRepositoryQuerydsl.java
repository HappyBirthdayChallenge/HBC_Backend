package inha.tnt.hbc.domain.member.repository.querydsl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import inha.tnt.hbc.domain.member.dto.FriendDto;

public interface FriendRepositoryQuerydsl {

	Page<FriendDto> findFriendDtoPage(Long memberId, Pageable pageable);

}
