package inha.tnt.hbc.application.file.controller;

import static inha.tnt.hbc.model.ResultCode.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

import inha.tnt.hbc.application.file.service.FileService;
import inha.tnt.hbc.model.ResultResponse;
import inha.tnt.hbc.model.file.FileApi;
import inha.tnt.hbc.model.file.dto.FileUploadResponse;

@RestController
@RequiredArgsConstructor
public class FileController implements FileApi {

	private final FileService fileService;

	@Override
	public ResponseEntity<ResultResponse> uploadImage(MultipartFile image, Long messageId, Long clientId) {
		final Long fileId = fileService.uploadToS3(image, messageId);
		final FileUploadResponse response = FileUploadResponse.of(fileId, clientId);
		return ResponseEntity.ok(ResultResponse.of(UPLOAD_IMAGE_SUCCESS, response));
	}

	@Override
	public ResponseEntity<ResultResponse> uploadVideo(MultipartFile video, Long messageId, Long clientId) {
		final Long fileId = fileService.uploadToS3(video, messageId);
		final FileUploadResponse response = FileUploadResponse.of(fileId, clientId);
		return ResponseEntity.ok(ResultResponse.of(UPLOAD_VIDEO_SUCCESS, response));
	}

	@Override
	public ResponseEntity<ResultResponse> uploadAudio(MultipartFile audio, Long messageId, Long clientId) {
		final Long fileId = fileService.uploadToS3(audio, messageId);
		final FileUploadResponse response = FileUploadResponse.of(fileId, clientId);
		return ResponseEntity.ok(ResultResponse.of(UPLOAD_AUDIO_SUCCESS, response));
	}

	@Override
	public ResponseEntity<ResultResponse> deleteMessageFile(Long fileId) {
		fileService.deleteMessageFile(fileId);
		return ResponseEntity.ok(ResultResponse.of(DELETE_MESSAGE_FILE_SUCCESS));
	}

}
