package inha.tnt.hbc.application.file.dto;

import static inha.tnt.hbc.util.Constants.*;

import java.io.File;

import org.apache.commons.io.FilenameUtils;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LocalFile {

	private File file;
	private String uuid;
	private String name;
	private String type;

	public String getFullName() {
		return this.uuid + DELIMITER + this.name + DOT + this.type;
	}

	public void deleteFile() {
		if (this.file.delete()) {
			log.debug("File delete success!");
		} else {
			log.debug("File delete failed!");
		}
	}

	public static LocalFile of(File file, String uuid, String originalFilename) {
		return LocalFile.builder()
			.file(file)
			.uuid(uuid)
			.name(FilenameUtils.getBaseName(originalFilename))
			.type(FilenameUtils.getExtension(originalFilename))
			.build();
	}

}
