package inha.tnt.hbc.domain.member.entity;

import static inha.tnt.hbc.util.Constants.*;
import static javax.persistence.CascadeType.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import inha.tnt.hbc.domain.BaseEntity;
import inha.tnt.hbc.domain.member.entity.oauth2.OAuth2Account;
import inha.tnt.hbc.domain.member.exception.AlreadySetupBirthDateException;
import inha.tnt.hbc.vo.BirthDate;
import inha.tnt.hbc.vo.Image;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "members")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "member_id")
	private Long id;
	private String username;
	private String password;
	private String name;
	private BirthDate birthDate;
	@Embedded
	private Image image;
	private String phone;
	private String authorities;
	@OneToMany(mappedBy = "member", cascade = REMOVE)
	private final List<OAuth2Account> oAuth2Accounts = new ArrayList<>();

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

}
