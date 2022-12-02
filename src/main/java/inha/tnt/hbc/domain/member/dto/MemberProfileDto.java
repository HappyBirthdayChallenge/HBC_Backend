package inha.tnt.hbc.domain.member.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.querydsl.core.annotations.QueryProjection;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import inha.tnt.hbc.domain.member.entity.Member;
import inha.tnt.hbc.domain.room.entity.Room;
import inha.tnt.hbc.model.room.dto.RoomDto;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class MemberProfileDto {

	private MemberDto member;
	@ApiModelProperty(value = "팔로잉 수", example = "100")
	private int followings;
	@ApiModelProperty(value = "팔로워 수", example = "100")
	private int followers;
	@ApiModelProperty(value = "메시지 좋아요 받은 횟수", example = "100")
	private int messageLikes;
	@ApiModelProperty(value = "팔로워 여부", example = "true")
	private boolean follow;
	private List<RoomDto> rooms;

	@Builder
	@QueryProjection
	public MemberProfileDto(Member member, long followings, long followers, long messageLikes, boolean follow) {
		this.member = MemberDto.of(member);
		this.followings = (int)followings;
		this.followers = (int)followers;
		this.messageLikes = (int)messageLikes;
		this.follow = follow;
	}

	public void setRooms(List<Room> rooms) {
		this.rooms = rooms.stream()
			.map(RoomDto::of)
			.collect(Collectors.toList());
	}

}
