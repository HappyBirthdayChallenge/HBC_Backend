package inha.tnt.hbc.domain.member.service.oauth2;

import static inha.tnt.hbc.util.Constants.*;

import java.io.File;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import inha.tnt.hbc.application.member.service.AuthService;
import inha.tnt.hbc.domain.member.entity.Member;
import inha.tnt.hbc.domain.member.entity.oauth2.SnsAccount;
import inha.tnt.hbc.domain.member.entity.oauth2.SnsAccountPrimaryKey;
import inha.tnt.hbc.domain.member.entity.oauth2.SnsProvider;
import inha.tnt.hbc.domain.member.service.SnsAccountService;
import inha.tnt.hbc.infra.aws.S3Uploader;
import inha.tnt.hbc.security.oauth2.OAuth2Attributes;
import inha.tnt.hbc.util.ImageUtils;
import inha.tnt.hbc.vo.BirthDate;
import inha.tnt.hbc.vo.Image;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OAuth2Service {

	private final static String OAUTH2_USERNAME_PREFIX = "user";

	private final AuthService authService;
	private final SnsAccountService snsAccountService;
	private final S3Uploader s3Uploader;

	@Transactional
	public Member getMember(OAuth2Attributes oAuth2Attributes) {
		final SnsProvider provider = SnsProvider.valueOf(oAuth2Attributes.getProvider());
		final Long userId = oAuth2Attributes.getAttributeKey();
		final SnsAccountPrimaryKey primaryKey = new SnsAccountPrimaryKey(provider, userId);
		return snsAccountService.getWithMember(primaryKey)
			.map(SnsAccount::getMember)
			.orElseGet(() -> signup(primaryKey, oAuth2Attributes));
	}

	private Member signup(SnsAccountPrimaryKey primaryKey, OAuth2Attributes oAuth2Attributes) {
		final String username = OAUTH2_USERNAME_PREFIX + DELIMITER + System.currentTimeMillis();
		final String password = UUID.randomUUID().toString();
		final String email = oAuth2Attributes.getEmail();
		final BirthDate birthDate = oAuth2Attributes.getBirthDate();
		final String name = oAuth2Attributes.getName();
		final String imageUrl = oAuth2Attributes.getImageUrl();

		final File file = ImageUtils.convert(imageUrl);
		final Image image = s3Uploader.uploadImage(file, S3_DIRECTORY_MEMBER);
		final Member member = authService.signup(username, password, name, email, birthDate, image);
		snsAccountService.connect(member, primaryKey);

		return member;
	}

}
