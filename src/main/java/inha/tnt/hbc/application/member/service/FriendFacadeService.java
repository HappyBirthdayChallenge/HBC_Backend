package inha.tnt.hbc.application.member.service;

import static inha.tnt.hbc.util.Constants.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import inha.tnt.hbc.application.member.exception.AlreadyFriendException;
import inha.tnt.hbc.application.member.exception.CannotFriendMySelfException;
import inha.tnt.hbc.domain.alarm.service.AlarmService;
import inha.tnt.hbc.domain.member.dto.FriendDto;
import inha.tnt.hbc.domain.member.dto.MemberDto;
import inha.tnt.hbc.domain.member.dto.MemberSearchDto;
import inha.tnt.hbc.domain.member.entity.Friend;
import inha.tnt.hbc.domain.member.entity.Member;
import inha.tnt.hbc.domain.member.service.FriendService;
import inha.tnt.hbc.domain.member.service.MemberService;
import inha.tnt.hbc.model.member.dto.FollowerPageResponse;
import inha.tnt.hbc.model.member.dto.FollowingPageResponse;
import inha.tnt.hbc.model.member.dto.MemberSearchResponse;
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

	public FollowingPageResponse getFollowings(int page, int size, Long targetMemberId) {
		final Long memberId = securityContextUtils.takeoutMemberId();
		final PageRequest pageable = PageRequest.of(page - PAGE_CORRECTION_VALUE, size);
		final Page<FriendDto> friendDtoPage = friendService.findFollowingDtoPage(targetMemberId, pageable);
		setUpFollowFlag(memberId, friendDtoPage.getContent());
		return FollowingPageResponse.of(friendDtoPage);
	}

	public FollowerPageResponse getFollowers(int page, int size, Long targetMemberId) {
		final Long memberId = securityContextUtils.takeoutMemberId();
		final PageRequest pageable = PageRequest.of(page - PAGE_CORRECTION_VALUE, size);
		final Page<FriendDto> friendDtoPage = friendService.findFollowerDtoPage(targetMemberId, pageable);
		setUpFollowFlag(memberId, friendDtoPage.getContent());
		return FollowerPageResponse.of(friendDtoPage);
	}

	@Transactional
	public void deleteFriend(Long friendMemberId) {
		final Long memberId = securityContextUtils.takeoutMemberId();
		final Friend friend = friendService.findByMemberIdAndFriendMemberId(memberId, friendMemberId);
		alarmService.deleteFriendAlarm(friend);
		friendService.delete(friend);
	}

	public MemberSearchResponse searchFollower(String keyword) {
		final Long memberId = securityContextUtils.takeoutMemberId();
		final List<Member> searchedMembers = friendService.findTop20FollowersByUsernameStartsWithOrNameStartsWith(
			memberId, keyword);
		final List<Long> searchedMemberIds = searchedMembers
			.stream()
			.map(Member::getId)
			.collect(Collectors.toList());
		final Set<Long> followingMemberBucket = friendService.findAllByMemberIdAndFriendMemberIdIn(memberId,
				searchedMemberIds)
			.stream()
			.map(friend -> friend.getFriendMember().getId())
			.collect(Collectors.toSet());
		final List<MemberSearchDto> result = searchedMembers.stream()
			.map(member -> MemberSearchDto.of(member, followingMemberBucket.contains(member.getId())))
			.collect(Collectors.toList());
		return MemberSearchResponse.of(result);
	}

	public MemberSearchResponse searchFollowing(String keyword) {
		final Long memberId = securityContextUtils.takeoutMemberId();
		final List<Member> searchedMembers = friendService.findTop20FollowingsByUsernameStartsWithOrNameStartsWith(
			memberId, keyword);
		final List<Long> searchedMemberIds = searchedMembers
			.stream()
			.map(Member::getId)
			.collect(Collectors.toList());
		final Set<Long> followingMemberBucket = friendService.findAllByMemberIdAndFriendMemberIdIn(memberId,
				searchedMemberIds)
			.stream()
			.map(friend -> friend.getFriendMember().getId())
			.collect(Collectors.toSet());
		final List<MemberSearchDto> result = searchedMembers.stream()
			.map(member -> MemberSearchDto.of(member, followingMemberBucket.contains(member.getId())))
			.collect(Collectors.toList());
		return MemberSearchResponse.of(result);
	}

	private void setUpFollowFlag(Long memberId, List<FriendDto> friendDtos) {
		final List<Long> followerMemberIds = convertFollowerMemberIds(friendDtos);
		final List<Friend> friends = friendService.findAllByMemberIdAndFriendMemberIdIn(memberId, followerMemberIds);
		final Set<Long> followingMemberIds = convertFollowingMemberIds(friends);
		friendDtos.stream()
			.filter(followerDto -> followingMemberIds.contains(followerDto.getMember().getId()))
			.forEach(FriendDto::raiseFollowFlag);
	}

	private Set<Long> convertFollowingMemberIds(List<Friend> friends) {
		return friends.stream()
			.map(Friend::getFriendMember)
			.map(Member::getId)
			.collect(Collectors.toSet());
	}

	private List<Long> convertFollowerMemberIds(List<FriendDto> friendDtos) {
		return friendDtos.stream()
			.map(FriendDto::getMember)
			.map(MemberDto::getId)
			.collect(Collectors.toList());
	}

}
