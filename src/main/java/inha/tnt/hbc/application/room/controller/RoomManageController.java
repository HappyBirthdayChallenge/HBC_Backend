package inha.tnt.hbc.application.room.controller;

import static inha.tnt.hbc.model.ResultCode.*;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import inha.tnt.hbc.application.room.service.RoomManageService;
import inha.tnt.hbc.model.ResultResponse;
import inha.tnt.hbc.model.room.RoomApi;
import inha.tnt.hbc.model.room.dto.RoomDto;

@RestController
@RequiredArgsConstructor
public class RoomManageController implements RoomApi {

	private final RoomManageService roomManageService;

	@Override
	public ResponseEntity<ResultResponse> getRooms(Long memberId) {
		final List<RoomDto> roomDtos = roomManageService.getRoomDtos(memberId);
		return ResponseEntity.ok(ResultResponse.of(GET_ROOMS_SUCCESS, roomDtos));
	}

}
