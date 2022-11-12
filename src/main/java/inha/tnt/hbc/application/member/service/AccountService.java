package inha.tnt.hbc.application.member.service;

import static inha.tnt.hbc.model.ResultCode.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import inha.tnt.hbc.domain.member.entity.Member;
import inha.tnt.hbc.domain.member.service.FCMTokenService;
import inha.tnt.hbc.domain.member.service.RefreshTokenService;
import inha.tnt.hbc.model.ResultResponse;
import inha.tnt.hbc.model.member.dto.MyInfoResponse;
import inha.tnt.hbc.security.jwt.dto.JwtDto;
import inha.tnt.hbc.util.JwtUtils;
import inha.tnt.hbc.util.SecurityContextUtils;
import inha.tnt.hbc.domain.member.vo.BirthDate;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountService {

	private final SecurityContextUtils securityContextUtils;
	private final JwtUtils jwtUtils;
	private final RefreshTokenService refreshTokenService;
	private final FCMTokenService fcmTokenService;

	@Transactional
	public JwtDto setupMyBirthdayAndGenerateJwt(BirthDate birthDate) {
		final Member member = securityContextUtils.takeoutMember();
		member.setupBirthDate(birthDate);
		final String accessToken = jwtUtils.generateAccessToken(member);
		final String refreshToken = jwtUtils.generateRefreshToken(member);
		return JwtDto.builder()
			.accessToken(accessToken)
			.refreshToken(refreshToken)
			.build();
	}

	public ResultResponse signout(String fcmToken) {
		final Long memberId = securityContextUtils.takeoutMemberId();
		refreshTokenService.deleteRefreshToken(memberId);
		fcmTokenService.deleteFCMToken(memberId, fcmToken);
		return ResultResponse.of(SIGNOUT_SUCCESS);
	}

	public MyInfoResponse getMyInfo() {
		return MyInfoResponse.of(securityContextUtils.takeoutMember());
	}

}
