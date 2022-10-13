package inha.tnt.hbc.util;

import static inha.tnt.hbc.util.Constants.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class FileUtils {

	public static SimpleFile convertToSimpleFile(File file) {
		final String originalName = file.getName();
		return SimpleFile.builder()
			.name(FilenameUtils.getBaseName(originalName))
			.type(FilenameUtils.getExtension(originalName))
			.build();
	}

	public static File convertToFile(MultipartFile multipartFile) {
		try {
			final File convertFile = new File(
				ROOT_DIRECTORY + BACK_SLASH + TEMPORAL_DIRECTORY + BACK_SLASH + multipartFile.getOriginalFilename());
			if (convertFile.createNewFile()) {
				try (FileOutputStream fos = new FileOutputStream(convertFile)) {
					fos.write(multipartFile.getBytes());
				}
				return convertFile;
			}
			throw new RuntimeException();
		} catch (IOException e) {
			throw new RuntimeException();
		}
	}

	public static boolean deleteFile(File file) {
		return file.delete();
	}

	@Getter
	@Builder
	@AllArgsConstructor
	public static class SimpleFile {

		private String name;
		private String type;

		public String getOriginalFilename() {
			return name + type;
		}

	}

}
