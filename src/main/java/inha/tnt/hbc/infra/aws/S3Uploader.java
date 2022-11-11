package inha.tnt.hbc.infra.aws;

import static inha.tnt.hbc.infra.aws.S3Constants.*;
import static inha.tnt.hbc.util.Constants.*;

import java.io.File;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;

import inha.tnt.hbc.util.FileUtils;
import inha.tnt.hbc.util.FileUtils.SimpleFile;
import inha.tnt.hbc.vo.ProfileImage;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class S3Uploader {

	private final AmazonS3Client amazonS3Client;
	@Value("${cloud.aws.s3.bucket}")
	private String bucket;

	@Async
	public void uploadOAuth2ProfileImage(File file, Long memberId) {
		final SimpleFile simpleFile = FileUtils.convertToSimpleFile(file);
		final String name = simpleFile.getName();
		final String type = simpleFile.getType();
		final String uuid = UUID.randomUUID().toString();
		upload(file, generateFilename(generateProfileImageDir(memberId), uuid, name, type));
	}

	@Async
	public void uploadInitialProfileImage(Long memberId) {
		final ProfileImage image = ProfileImage.initial();
		final String pathname = ROOT_DIRECTORY + BACK_SLASH + TEMPORAL_DIRECTORY + BACK_SLASH + image.getFullName();
		final File file = new File(pathname);
		final String filename = generateImageFilename(generateProfileImageDir(memberId), image);
		putS3(file, filename);
	}

	private String generateProfileImageDir(Long memberId) {
		return PROFILE_IMAGE_DIR + SLASH + memberId;
	}

	private void upload(File file, String filename) {
		putS3(file, filename);
		FileUtils.deleteFile(file);
	}

	private String generateImageFilename(String dirName, ProfileImage image) {
		return generateFilename(dirName, image.getUuid(), image.getName(), image.getType().name());
	}

	private String generateFilename(String dirName, String uuid, String name, String type) {
		return dirName + SLASH + uuid + DELIMITER + name + DOT + type;
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
