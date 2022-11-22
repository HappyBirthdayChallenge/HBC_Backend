package inha.tnt.hbc.application.file.service;

import static inha.tnt.hbc.domain.message.entity.MessageStatus.*;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import inha.tnt.hbc.application.file.dto.LocalFile;
import inha.tnt.hbc.application.file.exception.CannotAttachFileToDeletedMessage;
import inha.tnt.hbc.application.file.exception.CannotAttachFileToWrittenMessage;
import inha.tnt.hbc.domain.member.entity.Member;
import inha.tnt.hbc.domain.message.entity.Message;
import inha.tnt.hbc.domain.message.service.MessageFileRedisService;
import inha.tnt.hbc.domain.message.service.MessageService;
import inha.tnt.hbc.infra.aws.S3Uploader;
import inha.tnt.hbc.model.file.dto.FileUploadResponse;
import inha.tnt.hbc.util.FileUtils;
import inha.tnt.hbc.util.SecurityContextUtils;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileService {

	private final static String S3_DIRECTORY = "temp";
	private final S3Uploader s3Uploader;
	private final MessageService messageService;
	private final MessageFileRedisService messageFileRedisService;
	private final SecurityContextUtils securityContextUtils;

	public FileUploadResponse uploadToS3(MultipartFile multipartFile, Long messageId) {
		final Member member = securityContextUtils.takeoutMember();
		final Message message = messageService.findByIdAndMember(messageId, member);
		if (message.getStatus().equals(DELETED)) {
			throw new CannotAttachFileToDeletedMessage();
		}
		if (message.getStatus().equals(WRITTEN)) {
			throw new CannotAttachFileToWrittenMessage();
		}
		final LocalFile localFile = FileUtils.convert(multipartFile);
		s3Uploader.upload(localFile, S3_DIRECTORY);
		final String fileId = String.valueOf(System.currentTimeMillis());
		messageFileRedisService.save(messageId, fileId, localFile);
		return FileUploadResponse
				.builder()
				.fileId(fileId)
				.build();
	}

}
