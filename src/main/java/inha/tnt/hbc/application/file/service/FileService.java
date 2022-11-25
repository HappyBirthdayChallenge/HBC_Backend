package inha.tnt.hbc.application.file.service;

import static inha.tnt.hbc.domain.message.entity.MessageStatus.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import inha.tnt.hbc.application.file.dto.LocalFile;
import inha.tnt.hbc.application.file.exception.CannotAttachFileToDeletedMessage;
import inha.tnt.hbc.application.file.exception.CannotAttachFileToWrittenMessage;
import inha.tnt.hbc.domain.member.entity.Member;
import inha.tnt.hbc.domain.message.entity.Message;
import inha.tnt.hbc.domain.message.entity.MessageFile;
import inha.tnt.hbc.domain.message.service.MessageFileRedisService;
import inha.tnt.hbc.domain.message.service.MessageFileService;
import inha.tnt.hbc.domain.message.service.MessageService;
import inha.tnt.hbc.infra.aws.S3Uploader;
import inha.tnt.hbc.model.file.dto.FileUploadResponse;
import inha.tnt.hbc.util.FileUtils;
import inha.tnt.hbc.util.SecurityContextUtils;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileService {

	private final S3Uploader s3Uploader;
	private final MessageService messageService;
	private final MessageFileRedisService messageFileRedisService;
	private final SecurityContextUtils securityContextUtils;
	private final MessageFileService messageFileService;

	@Transactional
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
		final MessageFile messageFile = messageFileService.save(message, localFile);
		messageFileRedisService.save(messageId, messageFile.getId(), localFile);
		s3Uploader.upload(localFile, messageFile.getS3Directory());
		localFile.deleteFile();
		return FileUploadResponse.builder()
			.fileId(messageFile.getId())
			.build();
	}

	@Transactional
	public void deleteMessageFile(Long fileId) {
		final MessageFile messageFile = messageFileService.findById(fileId);
		messageFileService.deleteById(fileId);
		messageFileRedisService.delete(messageFile.getMessage().getId(), fileId); // TODO: key 삭제 안됨 검토
		s3Uploader.delete(messageFile.getS3Directory(), messageFile.getFileFullName());
	}

}
