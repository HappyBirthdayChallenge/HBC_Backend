package inha.tnt.hbc.domain.member.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import inha.tnt.hbc.domain.member.dto.FriendDto;
import inha.tnt.hbc.domain.member.entity.Friend;
import inha.tnt.hbc.domain.member.entity.Member;
import inha.tnt.hbc.domain.member.repository.FriendRepository;

@Service
@RequiredArgsConstructor
public class FriendService {

	private final FriendRepository friendRepository;

	@Transactional(readOnly = true)
	public boolean checkFriendOrNot(Member member, Member friendMember) {
		return friendRepository.findByMemberAndFriendMember(member, friendMember).isPresent();
	}

	@Transactional
	public void friend(Member member, Member friendMember) {
		final Friend friend = Friend.builder()
				.member(member)
				.friendMember(friendMember)
				.build();
		friendRepository.save(friend);
	}

	@Transactional(readOnly = true)
	public Page<FriendDto> findFriendDtoPage(Long memberId, Pageable pageable) {
		return friendRepository.findFriendDtoPage(memberId, pageable);
	}

}
