package inha.tnt.hbc.application.room.service;

import static inha.tnt.hbc.domain.message.entity.MessageDecorationTypes.*;
import static inha.tnt.hbc.util.Constants.*;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import inha.tnt.hbc.domain.message.dto.MessageDecorationDto;
import inha.tnt.hbc.domain.message.entity.Decoration;
import inha.tnt.hbc.domain.message.service.DecorationService;
import inha.tnt.hbc.domain.room.service.RoomService;
import inha.tnt.hbc.model.room.dto.RoomDecorationPageResponse;
import inha.tnt.hbc.model.room.dto.RoomDto;

@Service
@RequiredArgsConstructor
public class RoomFacadeService {

	private static final int SIZE_PER_PAGE = 4;
	private final RoomService roomService;
	private final DecorationService decorationService;

	public List<RoomDto> getRoomDtos(Long memberId) {
		return roomService.getRooms(memberId)
			.stream()
			.map(RoomDto::of)
			.collect(Collectors.toList());
	}

	public RoomDecorationPageResponse getRoomDecorationPage(Long roomId, int page) {
		final Pageable pageable = PageRequest.of(page - PAGE_CORRECTION_VALUE, SIZE_PER_PAGE);
		final Page<Decoration> foods = decorationService.findAllByRoomIdAndCategory(roomId, FOOD_TYPE1, pageable);
		final Page<Decoration> drinks = decorationService.findAllByRoomIdAndCategory(roomId, DRINK_TYPE1, pageable);
		final Page<Decoration> photos = decorationService.findAllByRoomIdAndCategory(roomId, PIC_TYPE1, pageable);
		final Page<Decoration> dolls = decorationService.findAllByRoomIdAndCategory(roomId, DOLL_TYPE1, pageable);
		final Page<Decoration> gifts = decorationService.findAllByRoomIdAndCategory(roomId, GIFT_TYPE1, pageable);
		final int totalPages = calculateTotalPages(foods, drinks, photos, dolls, gifts);
		return RoomDecorationPageResponse.builder()
			.foods(MessageDecorationDto.of(foods.getContent()))
			.drinks(MessageDecorationDto.of(drinks.getContent()))
			.photos(MessageDecorationDto.of(photos.getContent()))
			.dolls(MessageDecorationDto.of(dolls.getContent()))
			.gifts(MessageDecorationDto.of(gifts.getContent()))
			.isFirst(page == PAGE_FIRST_NUMBER)
			.isLast(page >= totalPages)
			.isEmpty(page > totalPages)
			.totalPages(totalPages)
			.build();
	}

	private int calculateTotalPages(Page<Decoration> foods, Page<Decoration> drinks, Page<Decoration> photos,
		Page<Decoration> dolls, Page<Decoration> gifts) {
		int totalPages = 0;
		totalPages = Math.max(totalPages, foods.getTotalPages());
		totalPages = Math.max(totalPages, drinks.getTotalPages());
		totalPages = Math.max(totalPages, photos.getTotalPages());
		totalPages = Math.max(totalPages, dolls.getTotalPages());
		totalPages = Math.max(totalPages, gifts.getTotalPages());
		return totalPages;
	}

}
