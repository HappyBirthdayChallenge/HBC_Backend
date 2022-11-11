package inha.tnt.hbc.infra.aws;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class S3Constants {

	public static final String BASE_URL = "https://hbc-bucket.s3.ap-northeast-2.amazonaws.com";
	public static final String PROFILE_IMAGE_DIR = "member";

}
