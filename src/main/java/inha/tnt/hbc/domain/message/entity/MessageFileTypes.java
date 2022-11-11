package inha.tnt.hbc.domain.message.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MessageFileTypes {
	JPG(10), JPEG(10), PNG(10), GIF(10),
	MP4(500),
	M4A(50),
	;

	private final int maxMB;
}
