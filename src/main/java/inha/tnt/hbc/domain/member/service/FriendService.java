package inha.tnt.hbc.domain.member.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import inha.tnt.hbc.domain.member.entity.Friend;
import inha.tnt.hbc.domain.member.entity.Member;
import inha.tnt.hbc.domain.member.repository.FriendRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FriendService {

	private final FriendRepository friendRepository;

	@Transactional(readOnly = true)
	public boolean checkFriendOrNot(Member member, Member friendMember) {
		return friendRepository.findByMemberAndFriendMember(member, friendMember).isPresent();
	}

	@Transactional
	public void addFriend(Member member, Member friendMember) {
		final Friend friend = Friend.builder()
			.member(member)
			.friendMember(friendMember)
			.build();
		friendRepository.save(friend);
	}

}
