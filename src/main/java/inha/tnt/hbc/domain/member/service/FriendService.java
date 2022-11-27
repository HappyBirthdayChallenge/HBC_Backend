package inha.tnt.hbc.domain.member.service;

import java.util.List;
import java.util.stream.Collectors;

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

	public boolean checkFriendOrNot(Member member, Member friendMember) {
		return friendRepository.findByMemberAndFriendMember(member, friendMember).isPresent();
	}

	@Transactional
	public Friend friend(Member member, Member friendMember) {
		final Friend friend = Friend.builder()
			.member(member)
			.friendMember(friendMember)
			.build();
		return friendRepository.save(friend);
	}

	@Transactional(readOnly = true)
	public Page<FriendDto> findFriendDtoPage(Long memberId, Pageable pageable) {
		return friendRepository.findFriendDtoPage(memberId, pageable);
	}

	public List<Member> findFollowers(Member member) {
		return friendRepository.findAllByFriendMember(member).stream()
			.map(Friend::getFriendMember)
			.collect(Collectors.toList());
	}

}
