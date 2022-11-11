package inha.tnt.hbc.application.member.service;

import static inha.tnt.hbc.util.Constants.*;

import java.io.File;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import inha.tnt.hbc.domain.member.entity.Member;
import inha.tnt.hbc.domain.member.entity.oauth2.OAuth2Account;
import inha.tnt.hbc.domain.member.entity.oauth2.OAuth2AccountPK;
import inha.tnt.hbc.domain.member.entity.oauth2.OAuth2Providers;
import inha.tnt.hbc.domain.member.service.MemberService;
import inha.tnt.hbc.domain.member.service.OAuth2AccountService;
import inha.tnt.hbc.infra.aws.S3Uploader;
import inha.tnt.hbc.infra.oauth2.OAuth2Client;
import inha.tnt.hbc.security.jwt.dto.JwtDto;
import inha.tnt.hbc.security.oauth2.OAuth2Attributes;
import inha.tnt.hbc.util.ImageUtils;
import inha.tnt.hbc.util.JwtUtils;
import inha.tnt.hbc.util.RandomUtils;
import inha.tnt.hbc.domain.member.vo.BirthDate;
import inha.tnt.hbc.domain.member.vo.ProfileImage;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OAuth2Service {

	private final static String OAUTH2_USERNAME_PREFIX = "user";

	private final AuthService authService;
	private final MemberService memberService;
	private final PasswordEncoder passwordEncoder;
	private final S3Uploader s3Uploader;
	private final OAuth2AccountService OAuth2AccountService;
	private final OAuth2Client oAuth2Client;
	private final JwtUtils jwtUtils;

	@Transactional
	public Member getMember(OAuth2Attributes oAuth2Attributes) {
		final OAuth2Providers provider = OAuth2Providers.valueOf(oAuth2Attributes.getProvider());
		final Long userId = oAuth2Attributes.getAttributeKey();
		final OAuth2AccountPK primaryKey = new OAuth2AccountPK(provider, userId);
		return OAuth2AccountService.getWithMember(primaryKey)
			.map(OAuth2Account::getMember)
			.orElseGet(() -> signup(primaryKey, oAuth2Attributes));
	}

	@Transactional
	public JwtDto signin(OAuth2Providers provider, String token) {
		final OAuth2Attributes oAuth2Attributes = oAuth2Client.getUserInfo(provider, token);
		final OAuth2User oAuth2User = oAuth2Attributes.toOAuth2User(getMember(oAuth2Attributes));
		final String accessToken = jwtUtils.generateAccessToken(oAuth2User);
		final String refreshToken = jwtUtils.generateRefreshToken(oAuth2User);
		return JwtDto.builder()
			.accessToken(accessToken)
			.refreshToken(refreshToken)
			.build();
	}

	private Member signup(OAuth2AccountPK primaryKey, OAuth2Attributes oAuth2Attributes) {
		final String seed = System.currentTimeMillis() + RandomUtils.generateNumber(5);
		final String username = OAUTH2_USERNAME_PREFIX + DELIMITER + seed;
		final String password = passwordEncoder.encode(UUID.randomUUID().toString());
		final String name = oAuth2Attributes.getName();
		final String imageUrl = oAuth2Attributes.getImageUrl();

		final ProfileImage image = ImageUtils.convertToProfileImage(imageUrl);
		final Member member = memberService.save(username, password, name, EMPTY, BirthDate.getInitial(), image);
		OAuth2AccountService.connect(member, primaryKey);
		final File file = ImageUtils.convertToFile(imageUrl);
		s3Uploader.uploadOAuth2ProfileImage(file, member.getId());
		return member;
	}

}
