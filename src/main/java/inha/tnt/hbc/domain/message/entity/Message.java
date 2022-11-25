package inha.tnt.hbc.domain.message.entity;

import static inha.tnt.hbc.domain.message.entity.MessageStatus.*;
import static inha.tnt.hbc.infra.aws.S3Constants.*;
import static inha.tnt.hbc.util.Constants.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import inha.tnt.hbc.domain.BaseEntity;
import inha.tnt.hbc.domain.member.entity.Member;
import inha.tnt.hbc.domain.room.entity.Room;

@Entity
@Table(name = "messages")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Message extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "message_id")
	private Long id;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "room_id")
	private Room room;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private MessageStatus status;
	private String content;
	@OneToMany(mappedBy = "message")
	private List<MessageFile> messageFiles = new ArrayList<>();
	@OneToOne(mappedBy = "message")
	private Decoration decoration;
	@OneToOne(mappedBy = "message")
	private Animation animation;

	public void uploadMessage(String content) {
		this.status = WRITTEN;
		this.content = content;
	}

	public List<String> getFileUris() {
		return messageFiles.stream()
			.map(MessageFile::getS3FileUri)
			.collect(Collectors.toList());
	}

	public boolean isWriter(Member member) {
		return this.member.getId().equals(member.getId());
	}

	public String getS3Directory() {
		return MESSAGE_S3_DIRECTORY + SLASH + this.id;
	}

	public void delete() {
		this.content = null;
		this.status = DELETED;
	}

}
