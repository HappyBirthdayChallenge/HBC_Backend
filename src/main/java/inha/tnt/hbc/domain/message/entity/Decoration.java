package inha.tnt.hbc.domain.message.entity;

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
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "decorations", indexes = @Index(name = "idx_decorations_category", columnList = "category"))
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Decoration {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "decoration_id")
	private Long id;
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "message_id")
	private Message message;
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private MessageDecorationTypes type;
	@Column(name = "category", nullable = false)
	private String category;

	@Builder
	public Decoration(Message message, MessageDecorationTypes type) {
		this.message = message;
		this.type = type;
		this.category = type.getCategory();
	}

	public void change(MessageDecorationTypes type) {
		this.type = type;
		this.category = type.getCategory();
	}

}
