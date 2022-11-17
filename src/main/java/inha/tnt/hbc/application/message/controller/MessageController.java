package inha.tnt.hbc.application.message.controller;

import static inha.tnt.hbc.model.ResultCode.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import inha.tnt.hbc.application.message.service.MessageFacadeService;
import inha.tnt.hbc.model.ResultResponse;
import inha.tnt.hbc.model.message.MessageApi;
import inha.tnt.hbc.model.message.dto.CreateMessageResponse;
import inha.tnt.hbc.model.message.dto.MessageRequest;

@RestController
@RequiredArgsConstructor
public class MessageController implements MessageApi {

	private final MessageFacadeService messageFacadeService;

	@Override
	public ResponseEntity<ResultResponse> upload(MessageRequest request) {
		return null;
	}

	@Override
	public ResponseEntity<ResultResponse> create(Long roomId) {
		final CreateMessageResponse response = messageFacadeService.createMessage(roomId);
		return ResponseEntity.ok(ResultResponse.of(CREATE_MESSAGE_SUCCESS, response));
	}

}
