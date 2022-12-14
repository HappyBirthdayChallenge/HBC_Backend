package inha.tnt.hbc.domain.message.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "animations")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Animation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "animation_id")
	private Long id;
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "message_id")
	private Message message;
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private AnimationTypes type;

	public void change(AnimationTypes type) {
		this.type = type;
	}

}
