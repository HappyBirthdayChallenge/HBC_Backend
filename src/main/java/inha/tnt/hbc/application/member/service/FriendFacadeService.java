package inha.tnt.hbc.application.member.service;

import static inha.tnt.hbc.util.Constants.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import inha.tnt.hbc.application.member.exception.AlreadyFriendException;
import inha.tnt.hbc.application.member.exception.CannotFriendMySelfException;
import inha.tnt.hbc.domain.alarm.service.AlarmService;
import inha.tnt.hbc.domain.member.dto.FollowerDto;
import inha.tnt.hbc.domain.member.dto.FollowingDto;
import inha.tnt.hbc.domain.member.dto.MemberDto;
import inha.tnt.hbc.domain.member.entity.Friend;
import inha.tnt.hbc.domain.member.entity.Member;
import inha.tnt.hbc.domain.member.service.FriendService;
import inha.tnt.hbc.domain.member.service.MemberService;
import inha.tnt.hbc.model.member.dto.FollowerPageResponse;
import inha.tnt.hbc.model.member.dto.FollowingPageResponse;
import inha.tnt.hbc.util.SecurityContextUtils;

@Service
@RequiredArgsConstructor
public class FriendFacadeService {

	private final SecurityContextUtils securityContextUtils;
	private final MemberService memberService;
	private final FriendService friendService;
	private final AlarmService alarmService;

	public void addFriend(Long memberId) {
		final Member member = securityContextUtils.takeoutMember();
		if (member.getId().equals(memberId)) {
			throw new CannotFriendMySelfException();
		}
		final Member friendMember = memberService.findById(memberId);
		if (friendService.checkFriendOrNot(member, friendMember)) {
			throw new AlreadyFriendException();
		}
		final Friend friend = friendService.friend(member, friendMember);
		alarmService.alarmFriend(friend);
	}

	public FollowingPageResponse getFollowings(int page, int size) {
		final Long memberId = securityContextUtils.takeoutMemberId();
		final PageRequest pageable = PageRequest.of(page - PAGE_CORRECTION_VALUE, size);
		final Page<FollowingDto> friendDtoPage = friendService.findFollowingDtoPage(memberId, pageable);
		return FollowingPageResponse.of(friendDtoPage);
	}

	public FollowerPageResponse getFollowers(int page, int size) {
		final Long memberId = securityContextUtils.takeoutMemberId();
		final PageRequest pageable = PageRequest.of(page - PAGE_CORRECTION_VALUE, size);
		final Page<FollowerDto> followerDtoPage = friendService.findFollowerDtoPage(memberId, pageable);
		setUpFollowFlag(memberId, followerDtoPage.getContent());
		return FollowerPageResponse.of(followerDtoPage);
	}

	private void setUpFollowFlag(Long memberId, List<FollowerDto> followerDtos) {
		final List<Long> followerMemberIds = convertFollowerMemberIds(followerDtos);
		final List<Friend> friends = friendService.findAllByMemberIdAndFriendMemberIdIn(memberId, followerMemberIds);
		final Set<Long> followingMemberIds = convertFollowingMemberIds(friends);
		followerDtos.stream()
			.filter(followerDto -> followingMemberIds.contains(followerDto.getFollower().getId()))
			.forEach(FollowerDto::raiseFollowFlag);
	}

	private Set<Long> convertFollowingMemberIds(List<Friend> friends) {
		return friends.stream()
			.map(Friend::getFriendMember)
			.map(Member::getId)
			.collect(Collectors.toSet());
	}

	private List<Long> convertFollowerMemberIds(List<FollowerDto> followerDtos) {
		return followerDtos.stream()
			.map(FollowerDto::getFollower)
			.map(MemberDto::getId)
			.collect(Collectors.toList());
	}

}
