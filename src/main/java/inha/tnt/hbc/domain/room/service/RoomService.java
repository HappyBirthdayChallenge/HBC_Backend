package inha.tnt.hbc.domain.room.service;

import static inha.tnt.hbc.model.ErrorCode.*;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import inha.tnt.hbc.domain.member.entity.Member;
import inha.tnt.hbc.domain.room.entity.CakeDecorationTypes;
import inha.tnt.hbc.domain.room.entity.Room;
import inha.tnt.hbc.domain.room.entity.RoomDecorationTypes;
import inha.tnt.hbc.domain.room.repository.RoomRepository;
import inha.tnt.hbc.exception.EntityNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoomService {

	private final RoomRepository roomRepository;

	@Transactional
	public void createRandomly(Member member) {
		final Room room = Room.builder()
			.member(member)
			.roomType(RoomDecorationTypes.random())
			.cakeType(CakeDecorationTypes.random())
			.build();

		roomRepository.save(room);
	}

	public void createRandomly(List<Member> members) {
		final List<Room> rooms = members.stream()
			.map(member -> Room.builder()
				.member(member)
				.roomType(RoomDecorationTypes.random())
				.cakeType(CakeDecorationTypes.random())
				.build()
			)
			.collect(Collectors.toList());
		roomRepository.saveAllInBatch(rooms);
	}

	@Transactional(readOnly = true)
	public List<Room> getRooms(Long memberId) {
		return roomRepository.findAllByMemberId(memberId);
	}

	public Room findById(Long roomId) {
		return roomRepository.findById(roomId)
			.orElseThrow(() -> new EntityNotFoundException(ROOM_UNFOUNDED));
	}

}
