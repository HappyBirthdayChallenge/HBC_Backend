package inha.tnt.hbc.application.member.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import inha.tnt.hbc.application.member.exception.AlreadyFriendException;
import inha.tnt.hbc.application.member.exception.CannotFriendMySelfException;
import inha.tnt.hbc.domain.member.dto.FriendDto;
import inha.tnt.hbc.domain.member.entity.Member;
import inha.tnt.hbc.domain.member.service.FriendService;
import inha.tnt.hbc.domain.member.service.MemberService;
import inha.tnt.hbc.infra.push.firebase.NotificationService;
import inha.tnt.hbc.model.member.dto.FriendListResponse;
import inha.tnt.hbc.util.SecurityContextUtils;

@Service
@RequiredArgsConstructor
public class FriendFacadeService {

	private final SecurityContextUtils securityContextUtils;
	private final MemberService memberService;
	private final FriendService friendService;
	private final NotificationService notificationService;

	@Transactional
	public void addFriend(Long memberId) {
		final Member member = securityContextUtils.takeoutMember();
		if (member.getId().equals(memberId)) {
			throw new CannotFriendMySelfException();
		}
		final Member friendMember = memberService.findById(memberId);
		if (friendService.checkFriendOrNot(member, friendMember)) {
			throw new AlreadyFriendException();
		}
		friendService.friend(member, friendMember);
		notificationService.sendFriendAlarm(member, friendMember);
	}

	@Transactional(readOnly = true)
	public FriendListResponse getFriends(int page, int size) {
		final Long memberId = securityContextUtils.takeoutMemberId();
		final Page<FriendDto> friendDtoPage = friendService.findFriendDtoPage(memberId, PageRequest.of(page - 1, size));
		return FriendListResponse.builder()
				.page(friendDtoPage)
				.build();
	}

}
