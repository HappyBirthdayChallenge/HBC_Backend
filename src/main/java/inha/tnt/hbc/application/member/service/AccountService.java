package inha.tnt.hbc.application.member.service;

import static inha.tnt.hbc.domain.member.service.IdentityVerificationService.IdentityVerificationTypes.*;
import static inha.tnt.hbc.model.ErrorCode.*;
import static inha.tnt.hbc.model.ResultCode.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

import inha.tnt.hbc.application.file.dto.LocalFile;
import inha.tnt.hbc.domain.member.dto.MemberProfileDto;
import inha.tnt.hbc.domain.member.dto.MemberSearchDto;
import inha.tnt.hbc.domain.member.entity.Member;
import inha.tnt.hbc.domain.member.service.FCMTokenService;
import inha.tnt.hbc.domain.member.service.FriendService;
import inha.tnt.hbc.domain.member.service.IdentityVerificationService;
import inha.tnt.hbc.domain.member.service.MemberService;
import inha.tnt.hbc.domain.member.service.RefreshTokenService;
import inha.tnt.hbc.domain.member.vo.BirthDate;
import inha.tnt.hbc.domain.member.vo.ProfileImage;
import inha.tnt.hbc.domain.room.entity.Room;
import inha.tnt.hbc.domain.room.service.RoomService;
import inha.tnt.hbc.exception.InvalidArgumentException;
import inha.tnt.hbc.infra.aws.S3Uploader;
import inha.tnt.hbc.model.ErrorResponse.FieldError;
import inha.tnt.hbc.model.ResultResponse;
import inha.tnt.hbc.model.member.dto.ChangePasswordRequest;
import inha.tnt.hbc.model.member.dto.MemberSearchResponse;
import inha.tnt.hbc.model.member.dto.MyInfoResponse;
import inha.tnt.hbc.security.jwt.dto.JwtDto;
import inha.tnt.hbc.util.FileUtils;
import inha.tnt.hbc.util.JwtUtils;
import inha.tnt.hbc.util.SecurityContextUtils;

@Service
@RequiredArgsConstructor
public class AccountService {

	private final SecurityContextUtils securityContextUtils;
	private final JwtUtils jwtUtils;
	private final RefreshTokenService refreshTokenService;
	private final FCMTokenService fcmTokenService;
	private final RoomService roomService;
	private final MemberService memberService;
	private final FriendService friendService;
	private final IdentityVerificationService identityVerificationService;
	private final PasswordEncoder passwordEncoder;
	private final S3Uploader s3Uploader;

	@Transactional
	public JwtDto setupMyBirthdayAndGenerateJwt(BirthDate birthDate) {
		final Member member = securityContextUtils.takeoutMember();
		member.setupBirthDate(birthDate);
		roomService.createRandomly(member);
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

	public MemberProfileDto getMemberProfile(Long targetMemberId) {
		final Long memberId = securityContextUtils.takeoutMemberId();
		final MemberProfileDto memberProfileDto = memberService.findMemberProfileDto(memberId, targetMemberId);
		final List<Room> rooms = roomService.findAllByMemberIdOrderByIdDesc(targetMemberId);
		memberProfileDto.setRooms(rooms);
		return memberProfileDto;
	}

	public MemberSearchResponse search(String keyword) {
		final Long memberId = securityContextUtils.takeoutMemberId();
		final List<Member> searchedMembers = memberService.findTop20ByUsernameStartsWithOrNameStartsWith(keyword);
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

	@Transactional
	public void changeName(String name) {
		final Member member = securityContextUtils.takeoutMember();
		member.changeName(name);
	}

	@Transactional
	public boolean changePassword(ChangePasswordRequest request) {
		final Member member = securityContextUtils.takeoutMember();
		if (!request.getPassword().equals(request.getPasswordCheck())) {
			throw new InvalidArgumentException(FieldError.of("password_check", request.getPasswordCheck(),
				PASSWORD_MISMATCHED));
		}
		if (!identityVerificationService.isValidKey(member.getPhone(), request.getKey(), CHANGE_PW)) {
			return false;
		}
		member.changePassword(passwordEncoder.encode(request.getPassword()));
		identityVerificationService.deleteKey(member.getPhone(), CHANGE_PW);
		return true;
	}

	@Transactional
	public void changeImage(MultipartFile image) {
		final Member member = securityContextUtils.takeoutMember();
		s3Uploader.deleteProfileImage(member.getId(), member.getImage());
		final LocalFile localFile = FileUtils.convert(image);
		final ProfileImage profileImage = ProfileImage.of(localFile);
		s3Uploader.uploadProfileImage(profileImage, localFile.getFile(), member.getId());
		member.changeImage(profileImage);
		localFile.deleteFile();
	}

	@Transactional
	public void changeDefaultImage() {
		final Member member = securityContextUtils.takeoutMember();
		s3Uploader.deleteProfileImage(member.getId(), member.getImage());
		s3Uploader.uploadInitialProfileImage(member.getId());
		member.changeImage(ProfileImage.initial());
	}

}
