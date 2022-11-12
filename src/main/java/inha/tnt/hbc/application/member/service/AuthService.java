package inha.tnt.hbc.application.member.service;

import static inha.tnt.hbc.domain.member.service.IdentityVerificationService.IdentityVerificationTypes.*;
import static inha.tnt.hbc.model.ResultCode.*;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import inha.tnt.hbc.domain.member.entity.Member;
import inha.tnt.hbc.domain.member.service.FCMTokenService;
import inha.tnt.hbc.domain.member.service.IdentityVerificationService;
import inha.tnt.hbc.domain.member.service.MemberService;
import inha.tnt.hbc.domain.member.service.RefreshTokenService;
import inha.tnt.hbc.exception.EntityNotFoundException;
import inha.tnt.hbc.infra.aws.S3Uploader;
import inha.tnt.hbc.model.ResultResponse;
import inha.tnt.hbc.model.member.dto.FindPasswordRequest;
import inha.tnt.hbc.model.member.dto.FindUsernameResponse;
import inha.tnt.hbc.model.member.dto.SigninRequest;
import inha.tnt.hbc.model.member.dto.SignupRequest;
import inha.tnt.hbc.security.jwt.dto.JwtDto;
import inha.tnt.hbc.util.JwtUtils;
import inha.tnt.hbc.domain.member.vo.ProfileImage;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final MemberService memberService;
	private final PasswordEncoder passwordEncoder;
	private final JwtUtils jwtUtils;
	private final RefreshTokenService refreshTokenService;
	private final IdentityVerificationService identityVerificationService;
	private final S3Uploader s3Uploader;
	private final FCMTokenService fcmTokenService;

	@Transactional
	public void signup(SignupRequest request) {
		final Member member = memberService.save(request.getUsername(), passwordEncoder.encode(request.getPassword()),
			request.getName(), request.getPhone(), request.getBirthDate(), ProfileImage.initial());
		s3Uploader.uploadInitialProfileImage(member.getId());
	}

	public ResultResponse signin(SigninRequest request) {
		try {
			final Member member = memberService.findByUsername(request.getUsername());
			if (!passwordEncoder.matches(request.getPassword(), member.getPassword())) {
				return ResultResponse.of(USERNAME_PASSWORD_INCORRECT);
			}

			final JwtDto jwtDto = JwtDto.builder()
				.accessToken(jwtUtils.generateAccessToken(member))
				.refreshToken(jwtUtils.generateRefreshToken(member))
				.build();
			refreshTokenService.saveRefreshToken(member.getId(), jwtDto.getRefreshToken());
			fcmTokenService.saveFCMToken(member.getId(), request.getFcmToken());
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
