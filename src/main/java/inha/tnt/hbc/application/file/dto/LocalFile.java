package inha.tnt.hbc.application.file.dto;

import static inha.tnt.hbc.util.Constants.*;

import java.io.File;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

}
