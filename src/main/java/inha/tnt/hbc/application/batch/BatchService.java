package inha.tnt.hbc.application.batch;

import java.util.List;

import org.springframework.stereotype.Service;

import inha.tnt.hbc.domain.member.entity.Member;
import inha.tnt.hbc.domain.member.service.MemberService;
import inha.tnt.hbc.domain.room.service.RoomService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BatchService {

	private final MemberService memberService;
	private final RoomService roomService;

	public void createRooms() {
		final List<Member> members = memberService.findAllWhoCanCreateRoom();
		roomService.createRandomly(members);
	}

}
