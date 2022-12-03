package inha.tnt.hbc.domain.member.service;

import static inha.tnt.hbc.model.ErrorCode.*;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import inha.tnt.hbc.domain.member.dto.FollowerDto;
import inha.tnt.hbc.domain.member.dto.FollowingDto;
import inha.tnt.hbc.domain.member.entity.Friend;
import inha.tnt.hbc.domain.member.entity.Member;
import inha.tnt.hbc.domain.member.repository.FriendRepository;
import inha.tnt.hbc.exception.EntityNotFoundException;

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

	public Page<FollowingDto> findFollowingDtoPage(Long memberId, Pageable pageable) {
		return friendRepository.findFollowingDtoPage(memberId, pageable);
	}

	public List<Member> findFollowers(Member member) {
		return friendRepository.findAllByFriendMember(member).stream()
			.map(Friend::getFriendMember)
			.collect(Collectors.toList());
	}

	public Page<FollowerDto> findFollowerDtoPage(Long memberId, PageRequest pageable) {
		return friendRepository.findFollowerDtoPage(memberId, pageable);
	}

	public List<Friend> findAllByMemberIdAndFriendMemberIdIn(Long memberId, List<Long> friendMemberIds) {
		return friendRepository.findAllByMemberIdAndFriendMemberIdIn(memberId, friendMemberIds);
	}

	public Friend findByMemberIdAndFriendMemberId(Long memberId, Long friendMemberId) {
		return friendRepository.findByMemberIdAndFriendMemberId(memberId, friendMemberId)
			.orElseThrow(() -> new EntityNotFoundException(NOT_FRIENDSHIP));
	}

	public void delete(Friend friend) {
		friendRepository.delete(friend);
	}

}
