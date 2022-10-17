package inha.tnt.hbc.util;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import inha.tnt.hbc.domain.member.entity.Member;
import inha.tnt.hbc.domain.member.service.MemberService;
import inha.tnt.hbc.security.jwt.JwtAuthenticationToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class SecurityContextUtils {

	private final JwtUtils jwtUtils;
	private final MemberService memberService;

	public Member takeoutMember() {
		return memberService.findById(takeoutMemberId());
	}

	public Long takeoutMemberId() {
		final JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken)SecurityContextHolder.getContext()
			.getAuthentication();
		return jwtUtils.getMemberId(jwtAuthenticationToken);

	}

}
