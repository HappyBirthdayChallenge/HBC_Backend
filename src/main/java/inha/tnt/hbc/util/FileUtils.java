package inha.tnt.hbc.util;

import static inha.tnt.hbc.util.Constants.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import inha.tnt.hbc.application.file.dto.LocalFile;

@Slf4j
public class FileUtils {

	private final static String PATHNAME = ROOT_DIRECTORY + BACK_SLASH + TEMPORAL_DIRECTORY;

	public static SimpleFile convertToSimpleFile(File file) {
		final String originalName = file.getName();
		return SimpleFile.builder()
			.name(FilenameUtils.getBaseName(originalName))
			.type(FilenameUtils.getExtension(originalName))
			.build();
	}

	public static void delete(File file) {
		if (!file.delete()) {
			log.error("File delete failed!");
		}
	}

	public static LocalFile convert(MultipartFile multipartFile) {
		final String originalFilename = multipartFile.getOriginalFilename();
		final LocalFile localFile = createLocalFile(originalFilename);
		try (FileOutputStream fos = new FileOutputStream(localFile.getFile())) {
			fos.write(multipartFile.getBytes());
			return localFile;
		} catch (IOException e) {
			log.error("File write failed!", e);
			throw new RuntimeException(e);
		}
	}

	public static LocalFile createLocalFile(String originalFilename) {
		try {
			File file;
			String uuid;
			do {
				uuid = UUID.randomUUID().toString();
				final String filename = uuid + DELIMITER + originalFilename;
				file = new File(PATHNAME + DELIMITER + filename);
			} while (!file.createNewFile());
			return LocalFile.builder()
				.file(file)
				.uuid(uuid)
				.name(FilenameUtils.getBaseName(originalFilename))
				.type(FilenameUtils.getExtension(originalFilename))
				.build();
		} catch (IOException e) {
			log.error("New file create failed!", e);
			throw new RuntimeException(e);
		}
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
