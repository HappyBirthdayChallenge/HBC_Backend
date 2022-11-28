package inha.tnt.hbc.application.message.controller;

import static inha.tnt.hbc.model.ResultCode.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import inha.tnt.hbc.application.message.service.MessageFacadeService;
import inha.tnt.hbc.model.ResultResponse;
import inha.tnt.hbc.model.message.MessageApi;
import inha.tnt.hbc.model.message.dto.CreateMessageResponse;
import inha.tnt.hbc.model.message.dto.InquiryMessageResponse;
import inha.tnt.hbc.model.message.dto.MessageRequest;
import inha.tnt.hbc.model.message.dto.MessageWrittenByMePageResponse;

@RestController
@RequiredArgsConstructor
public class MessageController implements MessageApi {

	private final MessageFacadeService messageFacadeService;

	@Override
	public ResponseEntity<ResultResponse> upload(MessageRequest request) {
		messageFacadeService.uploadMessage(request);
		return ResponseEntity.ok(ResultResponse.of(UPLOAD_MESSAGE_SUCCESS));
	}

	@Override
	public ResponseEntity<ResultResponse> create(Long roomId) {
		final CreateMessageResponse response = messageFacadeService.createMessage(roomId);
		return ResponseEntity.ok(ResultResponse.of(CREATE_MESSAGE_SUCCESS, response));
	}

	@Override
	public ResponseEntity<ResultResponse> inquiry(Long messageId) {
		final InquiryMessageResponse response = messageFacadeService.inquiryMessage(messageId);
		return ResponseEntity.ok(ResultResponse.of(INQUIRY_MESSAGE_SUCCESS, response));
	}

	@Override
	public ResponseEntity<ResultResponse> cancel(Long messageId) {
		messageFacadeService.cancelMessage(messageId);
		return ResponseEntity.ok(ResultResponse.of(CANCEL_MESSAGE_SUCCESS));
	}

	@Override
	public ResponseEntity<ResultResponse> delete(Long messageId) {
		messageFacadeService.deleteMessage(messageId);
		return ResponseEntity.ok(ResultResponse.of(DELETE_MESSAGE_SUCCESS));
	}

	@Override
	public ResponseEntity<ResultResponse> edit(MessageRequest request) {
		messageFacadeService.editMessage(request);
		return ResponseEntity.ok(ResultResponse.of(EDIT_MESSAGE_SUCCESS));
	}

	@Override
	public ResponseEntity<ResultResponse> getMessagesWrittenByMe(Integer page, Integer size) {
		final MessageWrittenByMePageResponse response = messageFacadeService.getMessagesWrittenByMe(page, size);
		return ResponseEntity.ok(ResultResponse.of(GET_MESSAGE_WRITTEN_BY_ME_SUCCESS, response));
	}

	@Override
	public ResponseEntity<ResultResponse> readMessage(Long messageId) {
		messageFacadeService.readMessage(messageId);
		return ResponseEntity.ok(ResultResponse.of(READ_MESSAGE_SUCCESS));
	}

	@Override
	public ResponseEntity<ResultResponse> likeMessage(Long messageId) {
		messageFacadeService.likeMessage(messageId);
		return ResponseEntity.ok(ResultResponse.of(LIKE_MESSAGE_SUCCESS));
	}

}
