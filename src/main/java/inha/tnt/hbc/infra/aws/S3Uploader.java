package inha.tnt.hbc.infra.aws;

import static inha.tnt.hbc.infra.aws.S3Constants.*;
import static inha.tnt.hbc.util.Constants.*;

import java.io.File;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;

import lombok.RequiredArgsConstructor;

import inha.tnt.hbc.application.file.dto.LocalFile;
import inha.tnt.hbc.domain.member.vo.ProfileImage;

@Component
@RequiredArgsConstructor
public class S3Uploader {

	private final AmazonS3Client amazonS3Client;
	@Value("${cloud.aws.s3.bucket}")
	private String bucket;

	public void uploadOAuth2ProfileImage(ProfileImage image, File file, Long memberId) {
		putS3(file, generateProfileImageFilename(memberId, image));
	}

	public void uploadInitialProfileImage(Long memberId) {
		final ProfileImage image = ProfileImage.initial();
		putS3(image.getFile(), generateProfileImageFilename(memberId, image));
	}

	public void upload(LocalFile localFile, String directory) {
		putS3(localFile.getFile(), directory + SLASH + localFile.getFullName());
	}

	private String generateProfileImageFilename(Long memberId, ProfileImage image) {
		return PROFILE_IMAGE_DIR + SLASH + memberId + SLASH + image.getFullName();
	}

	private void putS3(File uploadFile, String fileName) {
		final PutObjectRequest request = new PutObjectRequest(bucket, fileName, uploadFile)
			.withCannedAcl(CannedAccessControlList.PublicRead);
		amazonS3Client.putObject(request);
	}

	private void deleteS3(String filename) {
		amazonS3Client.deleteObject(bucket, filename);
	}

}
