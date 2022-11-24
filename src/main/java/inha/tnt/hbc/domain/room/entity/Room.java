package inha.tnt.hbc.domain.room.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import inha.tnt.hbc.domain.BaseEntity;
import inha.tnt.hbc.domain.member.entity.Member;

@Entity
@Table(name = "rooms", indexes = @Index(name = "idx_rooms_createAt", columnList = "create_at"))
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Room extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "room_id")
	private Long id;
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;
	@Enumerated(EnumType.STRING)
	private RoomDecorationTypes roomType;
	@Enumerated(EnumType.STRING)
	private CakeDecorationTypes cakeType;

	public boolean isOwner(Member member) {
		return this.member.getId().equals(member.getId());
	}

	public boolean isBeforeBirthDay() {
		return LocalDate.now().isBefore(this.member.getBirthDate().convert());
	}

}
