package inha.tnt.hbc.domain.member.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProfileImageType {
	PNG(10), JPG(10), JPEG(10),
	;

	private final int maxMB;
}
