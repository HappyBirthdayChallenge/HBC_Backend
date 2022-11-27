package inha.tnt.hbc.application.member.service;

import static inha.tnt.hbc.util.Constants.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import inha.tnt.hbc.application.member.exception.AlreadyFriendException;
import inha.tnt.hbc.application.member.exception.CannotFriendMySelfException;
import inha.tnt.hbc.domain.alarm.service.AlarmService;
import inha.tnt.hbc.domain.member.dto.FriendDto;
import inha.tnt.hbc.domain.member.entity.Friend;
import inha.tnt.hbc.domain.member.entity.Member;
import inha.tnt.hbc.domain.member.service.FriendService;
import inha.tnt.hbc.domain.member.service.MemberService;
import inha.tnt.hbc.model.member.dto.FriendListResponse;
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

	public FriendListResponse getFriends(int page, int size) {
		final Long memberId = securityContextUtils.takeoutMemberId();
		final PageRequest pageable = PageRequest.of(page - PAGE_CORRECTION_VALUE, size);
		final Page<FriendDto> friendDtoPage = friendService.findFriendDtoPage(memberId, pageable);
		return FriendListResponse.builder()
			.page(friendDtoPage)
			.build();
	}

}
