package inha.tnt.hbc.application.member.service;

import static inha.tnt.hbc.model.ErrorCode.*;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import inha.tnt.hbc.application.member.dto.FriendListResponse;
import inha.tnt.hbc.application.member.exception.AlreadyFriendException;
import inha.tnt.hbc.domain.member.entity.Member;
import inha.tnt.hbc.domain.member.service.FriendService;
import inha.tnt.hbc.domain.member.service.MemberService;
import inha.tnt.hbc.exception.InvalidArgumentException;
import inha.tnt.hbc.model.ErrorResponse.FieldError;
import inha.tnt.hbc.util.SecurityContextUtils;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FriendManageService {

	private final SecurityContextUtils securityContextUtils;
	private final MemberService memberService;
	private final FriendService friendService;

	@Transactional
	public void addFriend(Long memberId) {
		final Member member = securityContextUtils.takeoutMember();
		if (member.getId().equals(memberId)) {
			final List<FieldError> errors = FieldError.of("memberId", String.valueOf(memberId),
				FRIEND_MYSELF_IMPOSSIBLE.getMessage());
			throw new InvalidArgumentException(errors);
		}
		final Member friendMember = memberService.findById(memberId);
		if (friendService.checkFriendOrNot(member, friendMember)) {
			throw new AlreadyFriendException();
		}
		friendService.addFriend(member, friendMember);
	}

	@Transactional(readOnly = true)
	public FriendListResponse getFriends(int page, int size) {
		return null;
	}

}
