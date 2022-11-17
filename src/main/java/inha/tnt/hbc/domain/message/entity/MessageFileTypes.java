package inha.tnt.hbc.domain.message.entity;

import static inha.tnt.hbc.util.Constants.*;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MessageFileTypes {
	JPG, JPEG, PNG, GIF,
	MP4,
	M4A,
	;

	public static boolean isImage(MultipartFile multipartFile) {
		validate(multipartFile);
		final String extension = extractExtension(multipartFile);
		return isEqual(extension, JPG) || isEqual(extension, JPEG) || isEqual(extension, PNG)
			|| isEqual(extension, GIF);
	}

	public static boolean isVideo(MultipartFile multipartFile) {
		validate(multipartFile);
		final String extension = extractExtension(multipartFile);
		return isEqual(extension, MP4);
	}

	public static boolean isAudio(MultipartFile multipartFile) {
		validate(multipartFile);
		final String extension = extractExtension(multipartFile);
		return isEqual(extension, M4A);
	}

	private static String extractExtension(MultipartFile multipartFile) {
		final String originalFilename = multipartFile.getOriginalFilename();
		return originalFilename.substring(originalFilename.lastIndexOf(DOT) + 1).toUpperCase();
	}

	private static boolean isEqual(String extension, MessageFileTypes type) {
		return extension.equals(type.name());
	}

	private static void validate(MultipartFile multipartFile) {
		if (multipartFile.isEmpty()) {
			throw new RuntimeException();
		}
	}

}
