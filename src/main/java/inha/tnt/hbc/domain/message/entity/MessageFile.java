package inha.tnt.hbc.domain.message.entity;

import static inha.tnt.hbc.infra.aws.S3Constants.*;
import static inha.tnt.hbc.util.Constants.*;

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
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "message_files")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MessageFile {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "message_file_id")
	private Long id;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "message_id")
	private Message message;
	@Column(nullable = false)
	private String name;
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private MessageFileTypes type;
	@Column(nullable = false)
	private String uuid;

	public String getS3Directory() {
		return MESSAGE_S3_DIRECTORY + SLASH + this.message.getId();
	}

	public String getS3FileUri() {
		return S3_BASE_URL + SLASH + this.getS3Directory() + SLASH + this.getFileFullName();
	}

	public String getFileFullName() {
		return this.uuid + DELIMITER + this.name + DOT + this.type.name().toLowerCase();
	}

}
