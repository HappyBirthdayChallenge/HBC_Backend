package inha.tnt.hbc.application.member.service;

import static inha.tnt.hbc.model.ResultCode.*;
import static inha.tnt.hbc.util.JwtUtils.*;

import org.springframework.stereotype.Service;

import inha.tnt.hbc.domain.member.service.RefreshTokenService;
import inha.tnt.hbc.model.ResultResponse;
import inha.tnt.hbc.security.jwt.dto.JwtDto;
import inha.tnt.hbc.util.JwtUtils;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TokenService {

	private final JwtUtils jwtUtils;
	private final RefreshTokenService refreshTokenService;

	public ResultResponse reissueToken(String refreshToken) {
		try {
			final String jwt = refreshToken.substring(TOKEN_PREFIX_LENGTH);
			final Long memberId = jwtUtils.getMemberId(jwt);
			final String token = refreshTokenService.getRefreshToken(memberId);

			if (token == null || !token.equals(jwt)) {
				return ResultResponse.of(JWT_REISSUE_FAILURE);
			}

			final JwtDto jwtDto = JwtDto.builder()
				.accessToken(jwtUtils.generateAccessToken(jwt))
				.refreshToken(jwtUtils.generateRefreshToken(jwt))
				.build();
			refreshTokenService.saveRefreshToken(memberId, jwtDto.getRefreshToken());
			return ResultResponse.of(JWT_REISSUE_SUCCESS, jwtDto);
		} catch (JwtException e) {
			return ResultResponse.of(JWT_REISSUE_FAILURE);
		}
	}
}
