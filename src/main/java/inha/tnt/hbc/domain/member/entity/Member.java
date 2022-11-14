package inha.tnt.hbc.domain.member.entity;

import static inha.tnt.hbc.infra.aws.S3Constants.*;
import static inha.tnt.hbc.util.Constants.*;
import static javax.persistence.CascadeType.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import inha.tnt.hbc.domain.BaseEntity;
import inha.tnt.hbc.domain.member.entity.oauth2.OAuth2Account;
import inha.tnt.hbc.domain.member.exception.AlreadySetupBirthDateException;
import inha.tnt.hbc.domain.member.vo.BirthDate;
import inha.tnt.hbc.domain.member.vo.ProfileImage;
import inha.tnt.hbc.domain.room.entity.Room;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "members", indexes = @Index(name = "idx_members_month_date", columnList = "birthday_month, birthday_date"))
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "member_id")
	private Long id;
	@Column(unique = true, nullable = false)
	private String username;
	@Column(nullable = false)
	private String password;
	@Column(nullable = false)
	private String name;
	@Embedded
	private BirthDate birthDate;
	@Embedded
	private ProfileImage image;
	@Column(unique = true)
	private String phone;
	@Column(nullable = false)
	private String authorities;
	@OneToMany(mappedBy = "member", cascade = REMOVE)
	private List<OAuth2Account> oAuth2Accounts = new ArrayList<>();
	@JsonIgnore
	@OneToMany(mappedBy = "member", cascade = REMOVE)
	private List<Room> rooms = new ArrayList<>();

	public void setupBirthDate(BirthDate birthDate) {
		if (!this.birthDate.isInitial()) {
			throw new AlreadySetupBirthDateException();
		}
		this.birthDate = birthDate;
	}

	public List<String> combineAndGetAuthorities() {
		return Arrays.stream(this.authorities.split(COMMA))
			.collect(Collectors.toList());
	}

	public void changePassword(String password) {
		this.password = password;
	}

	public String getImageUri() {
		return BASE_URL + SLASH +
			PROFILE_IMAGE_DIR + SLASH +
			this.id + SLASH + this.image.getFullName();
	}

}
