package inha.tnt.hbc.application.room.controller;

import static inha.tnt.hbc.model.ResultCode.*;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import inha.tnt.hbc.application.room.service.RoomFacadeService;
import inha.tnt.hbc.model.ResultResponse;
import inha.tnt.hbc.model.room.RoomApi;
import inha.tnt.hbc.model.room.dto.RoomDecorationPageResponse;
import inha.tnt.hbc.model.room.dto.RoomDto;
import inha.tnt.hbc.model.room.dto.RoomMessagePageResponse;

@RestController
@RequiredArgsConstructor
public class RoomController implements RoomApi {

	private final RoomFacadeService roomFacadeService;

	@Override
	public ResponseEntity<ResultResponse> getRooms(Long memberId) {
		final List<RoomDto> roomDtos = roomFacadeService.getRoomDtos(memberId);
		return ResponseEntity.ok(ResultResponse.of(GET_ROOMS_SUCCESS, roomDtos));
	}

	@Override
	public ResponseEntity<ResultResponse> getRoomDecorationPage(Long roomId, Integer page) {
		final RoomDecorationPageResponse response = roomFacadeService.getRoomDecorationPage(roomId, page);
		return ResponseEntity.ok(ResultResponse.of(GET_ROOM_DECORATION_PAGE_SUCCESS, response));
	}

	@Override
	public ResponseEntity<ResultResponse> getRoomMessages(Long roomId, Integer page, Integer size) {
		final RoomMessagePageResponse response = roomFacadeService.getRoomMessagePage(roomId, page, size);
		return ResponseEntity.ok(ResultResponse.of(GET_ROOM_MESSAGE_PAGE_SUCCESS, response));
	}

}
