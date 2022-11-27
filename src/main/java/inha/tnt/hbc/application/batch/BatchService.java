package inha.tnt.hbc.application.batch;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import inha.tnt.hbc.domain.alarm.service.AlarmService;
import inha.tnt.hbc.domain.member.entity.Member;
import inha.tnt.hbc.domain.member.service.FriendService;
import inha.tnt.hbc.domain.member.service.MemberService;
import inha.tnt.hbc.domain.room.entity.Room;
import inha.tnt.hbc.domain.room.service.RoomService;

@Service
@RequiredArgsConstructor
public class BatchService {

	private final MemberService memberService;
	private final RoomService roomService;
	private final FriendService friendService;
	private final AlarmService alarmService;

	public void createRooms() {
		final List<Member> members = memberService.findAllWhoCanCreateRoom();
		roomService.createRandomly(members);
		members.forEach(member -> {
			final List<Member> followers = friendService.findFollowers(member);
			final Room room = roomService.findByMemberMostRecent(member);
			alarmService.alarmRoom(room, followers);
		});
	}

}
