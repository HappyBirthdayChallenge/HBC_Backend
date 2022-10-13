package inha.tnt.hbc.vo;

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
public class Image {

	@Enumerated(EnumType.STRING)
	@Column(name = "image_type")
	private ImageType type;
	@Column(name = "image_name")
	private String name;
	@Column(name = "image_uuid")
	private String uuid;
	@Column(name = "image_url")
	private String url;

	public static Image getInitial() {
		return Image.builder()
			.url(
				"https://hbc-bucket.s3.ap-northeast-2.amazonaws.com/member/550e8400-e29b-41d4-a716-446655440000_account_circle.png")
			.name("account_circle")
			.type(PNG)
			.uuid("550e8400-e29b-41d4-a716-446655440000")
			.build();
	}

	@Override
	public int hashCode() {
		return Objects.hash(getUuid());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;

		Image image = (Image)obj;
		return Objects.equals(getUuid(), image.getUuid());
	}

}
