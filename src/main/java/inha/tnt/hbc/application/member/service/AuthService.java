package inha.tnt.hbc.application.member.service;

import static inha.tnt.hbc.domain.member.entity.MemberRoles.*;
import static inha.tnt.hbc.domain.member.service.IdentityVerificationService.IdentityVerificationTypes.*;
import static inha.tnt.hbc.model.ResultCode.*;
import static inha.tnt.hbc.util.JwtUtils.*;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import inha.tnt.hbc.domain.member.entity.Member;
import inha.tnt.hbc.domain.member.service.IdentityVerificationService;
import inha.tnt.hbc.domain.member.service.MemberService;
import inha.tnt.hbc.domain.member.service.TokenService;
import inha.tnt.hbc.exception.EntityNotFoundException;
import inha.tnt.hbc.model.ResultResponse;
import inha.tnt.hbc.model.member.dto.FindPasswordRequest;
import inha.tnt.hbc.model.member.dto.FindUsernameResponse;
import inha.tnt.hbc.security.jwt.dto.JwtDto;
import inha.tnt.hbc.util.JwtUtils;
import inha.tnt.hbc.vo.BirthDate;
import inha.tnt.hbc.vo.Image;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final MemberService memberService;
	private final PasswordEncoder passwordEncoder;
	private final JwtUtils jwtUtils;
	private final TokenService tokenService;
	private final IdentityVerificationService identityVerificationService;

	@Transactional
	public Member signup(String username, String password, String name, String phone, BirthDate birthDate,
		Image image) {
		final Member member = Member.builder()
			.username(username)
			.password(passwordEncoder.encode(password))
			.name(name)
			.phone(phone)
			.birthDate(birthDate)
			.image(image)
			.authorities(ROLE_USER.name())
			.build();
		return memberService.save(member);
	}

	public ResultResponse signin(String username, String password) {
		try {
			final Member member = memberService.findByUsername(username);
			if (!passwordEncoder.matches(password, member.getPassword())) {
				return ResultResponse.of(USERNAME_PASSWORD_INCORRECT);
			}

			final JwtDto jwtDto = JwtDto.builder()
				.accessToken(jwtUtils.generateAccessToken(member))
				.refreshToken(jwtUtils.generateRefreshToken(member))
				.build();
			tokenService.saveRefreshToken(member.getId(), jwtDto.getRefreshToken());
			return ResultResponse.of(SIGNIN_SUCCESS, jwtDto);
		} catch (EntityNotFoundException e) {
			return ResultResponse.of(USERNAME_PASSWORD_INCORRECT);
		}
	}

	public ResultResponse checkUsername(String username) {
		if (!memberService.isExistingUsername(username)) {
			return ResultResponse.of(USERNAME_AVAILABLE);
		} else {
			return ResultResponse.of(USERNAME_ALREADY_USED);
		}
	}

	public ResultResponse checkPhone(String phone) {
		if (!memberService.isExistingPhone(phone)) {
			return ResultResponse.of(PHONE_AVAILABLE);
		} else {
			return ResultResponse.of(PHONE_ALREADY_USED);
		}
	}

	public ResultResponse reissueToken(String refreshToken) {
		try {
			final String jwt = refreshToken.substring(TOKEN_PREFIX_LENGTH);
			final Long memberId = jwtUtils.getMemberId(jwt);
			final String token = tokenService.getRefreshToken(memberId);

			if (token == null || !token.equals(jwt)) {
				return ResultResponse.of(JWT_REISSUE_FAILURE);
			}

			final JwtDto jwtDto = JwtDto.builder()
				.accessToken(jwtUtils.generateAccessToken(jwt))
				.refreshToken(jwtUtils.generateRefreshToken(jwt))
				.build();
			tokenService.saveRefreshToken(memberId, jwtDto.getRefreshToken());
			return ResultResponse.of(JWT_REISSUE_SUCCESS, jwtDto);
		} catch (JwtException e) {
			return ResultResponse.of(JWT_REISSUE_FAILURE);
		}
	}

	public ResultResponse identify(String name, String phone) {
		try {
			memberService.findByNameAndPhone(name, phone);
			return ResultResponse.of(IDENTIFY_SUCCESS);
		} catch (EntityNotFoundException e) {
			return ResultResponse.of(IDENTIFY_FAILURE);
		}
	}

	public ResultResponse findUsername(String name, String phone, String key) {
		if (!identityVerificationService.isValidKey(phone, key, FIND_ID)) {
			return ResultResponse.of(KEY_INVALID);
		}
		final Member member = memberService.findByNameAndPhone(name, phone);
		identityVerificationService.deleteKey(phone, FIND_ID);
		return ResultResponse.of(USERNAME_FIND_SUCCESS, new FindUsernameResponse(member.getUsername()));
	}

	@Transactional
	public ResultResponse findPassword(FindPasswordRequest request) {
		if (!identityVerificationService.isValidKey(request.getPhone(), request.getKey(), FIND_PW)) {
			return ResultResponse.of(KEY_INVALID);
		}
		final Member member = memberService.findByNameAndPhoneAndUsername(request.getName(), request.getPhone(),
			request.getUsername());
		member.changePassword(passwordEncoder.encode(request.getPassword()));
		identityVerificationService.deleteKey(request.getPhone(), FIND_PW);
		return ResultResponse.of(PASSWORD_FIND_SUCCESS);
	}

}
