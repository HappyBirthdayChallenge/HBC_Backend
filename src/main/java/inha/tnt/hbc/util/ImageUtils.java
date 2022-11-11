package inha.tnt.hbc.util;

import static inha.tnt.hbc.util.Constants.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.UUID;

import javax.imageio.ImageIO;

import inha.tnt.hbc.domain.member.vo.ImageType;
import inha.tnt.hbc.domain.member.vo.ProfileImage;

public class ImageUtils {

	public static File convertToFile(String imageUrl) {
		try {
			final URL url = new URL(imageUrl);
			final String extension = imageUrl.substring(imageUrl.lastIndexOf(DOT) + 1);
			final String randomName = UUID.randomUUID().toString();
			final BufferedImage image = ImageIO.read(url);
			final String filename = randomName + DOT + extension;
			final String pathname = ROOT_DIRECTORY + BACK_SLASH + TEMPORAL_DIRECTORY + BACK_SLASH + filename;
			final File file = new File(pathname);
			ImageIO.write(image, extension, file);
			return file;
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	public static ProfileImage convertToProfileImage(String imageUrl) {
		final String extension = imageUrl.substring(imageUrl.lastIndexOf(DOT) + 1);
		return ProfileImage.builder()
			.name(UUID.randomUUID().toString())
			.type(ImageType.valueOf(extension.toUpperCase()))
			.uuid(UUID.randomUUID().toString())
			.build();
	}

}
