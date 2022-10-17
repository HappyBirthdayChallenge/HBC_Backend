package inha.tnt.hbc.domain.member.entity;

import static inha.tnt.hbc.util.Constants.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import inha.tnt.hbc.domain.BaseEntity;
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
	private String email;
	private String authorities;

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

}
