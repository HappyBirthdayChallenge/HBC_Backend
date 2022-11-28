package inha.tnt.hbc.domain.alarm.entity;

import static inha.tnt.hbc.domain.alarm.entity.AlarmStatus.*;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import inha.tnt.hbc.domain.BaseEntity;
import inha.tnt.hbc.domain.member.entity.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "alarms")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Alarm extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "alarm_id")
	private Long id;
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sender_id")
	private Member sender;
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "receiver_id")
	private Member receiver;
	@Enumerated(EnumType.STRING)
	private AlarmStatus status = CREATED;
	private String content;
	@Column(insertable = false, updatable = false)
	private String dtype;

	public Alarm(Member sender, Member receiver, String content) {
		this.sender = sender;
		this.receiver = receiver;
		this.content = content;
		this.dtype = getClass().getAnnotation(DiscriminatorValue.class).value();
	}

}
