package inha.tnt.hbc.application.room.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import inha.tnt.hbc.domain.room.service.RoomService;
import inha.tnt.hbc.model.room.dto.RoomDto;

@Service
@RequiredArgsConstructor
public class RoomManageService {

	private final RoomService roomService;

	@Transactional(readOnly = true)
	public List<RoomDto> getRoomDtos(Long memberId) {
		return roomService.getRooms(memberId)
			.stream()
			.map(RoomDto::of)
			.collect(Collectors.toList());
	}

}
