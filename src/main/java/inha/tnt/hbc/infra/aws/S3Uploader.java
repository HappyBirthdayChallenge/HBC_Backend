package inha.tnt.hbc.infra.aws;

import static inha.tnt.hbc.util.Constants.*;

import java.io.File;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;

import inha.tnt.hbc.util.FileUtils;
import inha.tnt.hbc.util.FileUtils.SimpleFile;
import inha.tnt.hbc.vo.Image;
import inha.tnt.hbc.vo.ImageType;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class S3Uploader {

	private final AmazonS3Client amazonS3Client;
	@Value("${cloud.aws.s3.bucket}")
	private String bucket;

	public Image uploadImage(File file, String dirName) {
		final SimpleFile simpleFile = FileUtils.convertToSimpleFile(file);
		final String name = simpleFile.getName();
		final String type = simpleFile.getType();
		final String uuid = UUID.randomUUID().toString();
		final String url = upload(file, generateFilename(dirName, uuid, name, type));
		return Image.builder()
			.name(name)
			.type(ImageType.valueOf(type.toUpperCase()))
			.uuid(uuid)
			.url(url)
			.build();
	}

	public Image uploadImage(MultipartFile multipartFile, String dirName) {
		return uploadImage(FileUtils.convertToFile(multipartFile), dirName);
	}

	public void deleteImage(Image image, String dirName) {
		// TODO: 기본 이미지인 경우 삭제 x -> Image default value로 설정
		deleteS3(generateImageFilename(dirName, image));
	}

	private String upload(File file, String filename) {
		final String uploadImageUrl = putS3(file, filename);
		FileUtils.deleteFile(file);
		return uploadImageUrl;
	}

	private String generateImageFilename(String dirName, Image image) {
		return generateFilename(dirName, image.getUuid(), image.getName(), image.getType().name());
	}

	private String generateFilename(String dirName, String uuid, String name, String type) {
		return dirName + SLASH + uuid + DELIMITER + name + DOT + type;
	}

	private String putS3(File uploadFile, String fileName) {
		amazonS3Client.putObject(
			new PutObjectRequest(bucket, fileName, uploadFile).withCannedAcl(CannedAccessControlList.PublicRead));
		return amazonS3Client.getUrl(bucket, fileName).toString();
	}

	private void deleteS3(String filename) {
		amazonS3Client.deleteObject(bucket, filename);
	}

}
