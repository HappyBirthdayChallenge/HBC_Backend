package inha.tnt.hbc.domain.room.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Embedded;
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
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import inha.tnt.hbc.domain.BaseEntity;
import inha.tnt.hbc.domain.member.entity.Member;
import inha.tnt.hbc.domain.member.vo.BirthDate;

@Entity
@Table(name = "rooms", indexes = @Index(name = "idx_rooms_birthday", columnList = "birthday_year, birthday_month, birthday_date"))
@Getter
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
	@Embedded
	private BirthDate birthDate;

	@Builder
	public Room(Member member, RoomDecorationTypes roomType, CakeDecorationTypes cakeType) {
		this.member = member;
		this.roomType = roomType;
		this.cakeType = cakeType;
		this.birthDate = member.getBirthDate().getNextBirthdate();
	}

	public boolean isOwner(Member member) {
		return this.member.getId().equals(member.getId());
	}

	public boolean isBeforeBirthDay() {
		return LocalDate.now().isBefore(this.birthDate.convert());
	}

	public boolean isAfterBirthDay() {
		return LocalDate.now().isAfter(this.birthDate.convert());
	}

}
