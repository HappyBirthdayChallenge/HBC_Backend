package inha.tnt.hbc.domain.alarm.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import inha.tnt.hbc.domain.alarm.entity.Alarm;
import inha.tnt.hbc.domain.alarm.entity.FriendAlarm;
import inha.tnt.hbc.domain.alarm.entity.MessageAlarm;
import inha.tnt.hbc.domain.alarm.entity.MessageLikeAlarm;
import inha.tnt.hbc.domain.alarm.entity.RoomAlarm;
import inha.tnt.hbc.domain.member.dto.MemberDto;
import inha.tnt.hbc.domain.message.entity.MessageDecorationTypes;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class AlarmDto {

	@ApiModelProperty(value = "알림 유형", example = "MESSAGE")
	private String alarmType;
	@ApiModelProperty(value = "알림 PK", example = "1")
	private Long alarmId;
	@ApiModelProperty(value = "알림 내용", example = "손흥민님이 회원님을 친구로 추가했어요!")
	private String content;
	@ApiModelProperty(value = "알람 생성 일시", example = "2022-11-22T13:00:21.6645316")
	private String createAt;
	private RoomAlarmDto roomAlarm;
	private MessageAlarmDto messageAlarm;
	private MessageLikeAlarmDto messageLikeAlarm;
	private FriendAlarmDto friendAlarm;

	protected AlarmDto(Alarm alarm) {
		this.alarmType = alarm.getDtype();
		this.alarmId = alarm.getId();
		this.content = alarm.getContent();
		this.createAt = alarm.getCreateAt().toString();
		if (alarm.getDtype().equals(RoomAlarm.DTYPE)) {
			this.roomAlarm = new RoomAlarmDto((RoomAlarm)alarm);
		}
		if (alarm.getDtype().equals(MessageAlarm.DTYPE)) {
			this.messageAlarm = new MessageAlarmDto((MessageAlarm)alarm);
		}
		if (alarm.getDtype().equals(MessageLikeAlarm.DTYPE)) {
			this.messageLikeAlarm = new MessageLikeAlarmDto((MessageLikeAlarm)alarm);
		}
		if (alarm.getDtype().equals(FriendAlarm.DTYPE)) {
			this.friendAlarm = new FriendAlarmDto((FriendAlarm)alarm);
		}
	}

	public static AlarmDto of(Alarm alarm) {
		return new AlarmDto(alarm);
	}

	@Getter
	@AllArgsConstructor
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
	public static class RoomAlarmDto {

		@ApiModelProperty(value = "파티룸 PK", example = "1")
		private Long roomId;
		private MemberDto member;

		public RoomAlarmDto(RoomAlarm alarm) {
			this.roomId = alarm.getRoom().getId();
			this.member = MemberDto.of(alarm.getRoom().getMember());
		}

	}

	@Getter
	@AllArgsConstructor
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
	public static class MessageAlarmDto {

		@ApiModelProperty(value = "파티룸 PK", example = "1")
		private Long roomId;
		@ApiModelProperty(value = "메시지 PK", example = "1")
		private Long messageId;
		@ApiModelProperty(value = "메시지 장식품 유형", example = "DOLL_TYPE1")
		private MessageDecorationTypes decorationType;

		public MessageAlarmDto(MessageAlarm alarm) {
			this.roomId = alarm.getMessage().getRoom().getId();
			this.messageId = alarm.getMessage().getId();
			this.decorationType = alarm.getMessage().getDecoration().getType();
		}

	}

	@Getter
	@AllArgsConstructor
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
	public static class MessageLikeAlarmDto {

		@ApiModelProperty(value = "파티룸 PK", example = "1")
		private Long roomId;
		@ApiModelProperty(value = "메시지 PK", example = "1")
		private Long messageId;
		@ApiModelProperty(value = "메시지 장식품 유형", example = "DOLL_TYPE1")
		private MessageDecorationTypes decorationType;
		private MemberDto member;

		public MessageLikeAlarmDto(MessageLikeAlarm alarm) {
			this.roomId = alarm.getMessage().getRoom().getId();
			this.messageId = alarm.getMessage().getId();
			this.decorationType = alarm.getMessage().getDecoration().getType();
			this.member = MemberDto.of(alarm.getMessage().getRoom().getMember());
		}

	}

	@Getter
	@AllArgsConstructor
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class FriendAlarmDto {

		private MemberDto member;
		@ApiModelProperty(value = "팔로우 여부", example = "true")
		private boolean follow = false;

		public FriendAlarmDto(FriendAlarm alarm) {
			this.member = MemberDto.of(alarm.getFriend().getMember());
		}

		public void raiseUpFollowFlag() {
			this.follow = true;
		}

	}

}
