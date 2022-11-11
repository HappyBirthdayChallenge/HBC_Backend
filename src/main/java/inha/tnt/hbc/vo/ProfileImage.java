package inha.tnt.hbc.vo;

import static inha.tnt.hbc.util.Constants.*;
import static inha.tnt.hbc.vo.ImageType.*;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@Embeddable
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProfileImage {

	private static final String DEFAULT_NAME = "image";
	private static final String DEFAULT_UUID = "default";
	@Enumerated(EnumType.STRING)
	@Column(name = "image_type")
	private ImageType type;
	@Column(name = "image_name")
	private String name;
	@Column(name = "image_uuid")
	private String uuid;

	public static ProfileImage initial() {
		return ProfileImage.builder()
			.name(DEFAULT_NAME)
			.type(PNG)
			.uuid(DEFAULT_UUID)
			.build();
	}

	public String getFullName() {
		return this.uuid + DELIMITER + this.name + DOT + this.type.name().toLowerCase();
	}

	@Override
	public int hashCode() {
		return Objects.hash(getUuid());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}

		ProfileImage image = (ProfileImage)obj;
		return Objects.equals(getUuid(), image.getUuid());
	}

}
