package inha.tnt.hbc.domain.room.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import inha.tnt.hbc.domain.member.entity.Member;
import inha.tnt.hbc.domain.room.entity.CakeDecorationTypes;
import inha.tnt.hbc.domain.room.entity.Room;
import inha.tnt.hbc.domain.room.entity.RoomDecorationTypes;
import inha.tnt.hbc.domain.room.repository.RoomRepository;
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
			.year(member.getBirthDate().getNextBirthdayYear())
			.build();

		roomRepository.save(room);
	}

}
