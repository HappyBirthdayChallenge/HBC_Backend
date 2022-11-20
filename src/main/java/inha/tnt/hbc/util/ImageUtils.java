package inha.tnt.hbc.util;

import static inha.tnt.hbc.util.Constants.*;

import java.io.File;
import java.util.UUID;

import inha.tnt.hbc.domain.member.vo.ProfileImage;
import inha.tnt.hbc.domain.member.vo.ProfileImageType;

public class ImageUtils {

	public static File convertToFile(ProfileImage profileImage) {
		final String filename = profileImage.getFullName();
		final String pathname = ROOT_DIRECTORY + BACK_SLASH + TEMPORAL_DIRECTORY + BACK_SLASH + filename;
		return new File(pathname);
	}

	public static ProfileImage convertToProfileImage(String imageUrl) {
		final String extension = imageUrl.substring(imageUrl.lastIndexOf(DOT) + 1);
		return ProfileImage.builder()
			.name(UUID.randomUUID().toString())
			.type(ProfileImageType.valueOf(extension.toUpperCase()))
			.uuid(UUID.randomUUID().toString())
			.build();
	}

}
