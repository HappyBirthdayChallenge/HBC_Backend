package inha.tnt.hbc.vo;

import java.util.Objects;

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
public class Image {

	@Enumerated(EnumType.STRING)
	private ImageType imageType;

	private String imageName;

	private String imageUuid;

	@Override
	public int hashCode() {
		return Objects.hash(getImageUuid());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;

		Image image = (Image)obj;
		return Objects.equals(getImageUuid(), image.getImageUuid());
	}

}
