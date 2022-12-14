package inha.tnt.hbc.domain.member.service;

import static inha.tnt.hbc.domain.member.entity.MemberRoles.*;
import static inha.tnt.hbc.model.ErrorCode.*;
import static inha.tnt.hbc.util.Constants.*;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import inha.tnt.hbc.domain.member.dto.MemberProfileDto;
import inha.tnt.hbc.domain.member.entity.Member;
import inha.tnt.hbc.domain.member.repository.MemberRepository;
import inha.tnt.hbc.domain.member.vo.BirthDate;
import inha.tnt.hbc.domain.member.vo.ProfileImage;
import inha.tnt.hbc.exception.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;

	@Transactional
	public Member save(String username, String encodedPassword, String name, String phone, BirthDate birthDate,
		ProfileImage image) {
		final Member member = Member.builder()
			.username(username)
			.password(encodedPassword)
			.name(name)
			.phone(phone)
			.birthDate(birthDate)
			.image(image)
			.authorities(ROLE_USER.name())
			.build();
		return memberRepository.save(member);
	}

	public Member findByUsername(String username) {
		return memberRepository.findByUsername(username)
			.orElseThrow(() -> new EntityNotFoundException(MEMBER_UNFOUNDED));
	}

	public boolean isExistingUsername(String username) {
		return memberRepository.findByUsername(username).isPresent();
	}

	public Member findById(Long memberId) {
		return memberRepository.findById(memberId)
			.orElseThrow(() -> new EntityNotFoundException(MEMBER_UNFOUNDED));
	}

	public boolean isExistingPhone(String phone) {
		return memberRepository.findByPhone(phone).isPresent();
	}

	public Member findByNameAndPhone(String name, String phone) {
		return memberRepository.findByNameAndPhone(name, phone)
			.orElseThrow(() -> new EntityNotFoundException(MEMBER_UNFOUNDED));
	}

	public Member findByNameAndPhoneAndUsername(String name, String phone, String username) {
		return memberRepository.findByNameAndPhoneAndUsername(name, phone, username)
			.orElseThrow(() -> new EntityNotFoundException(MEMBER_UNFOUNDED));
	}

	public List<Member> findAllWhoCanCreateRoom() {
		return memberRepository.findAllByRoomCreateDate(LocalDate.now().plusDays(DAYS_BEFORE_ROOM_CREATION));
	}

	public MemberProfileDto findMemberProfileDto(Long memberId, Long targetMemberId) {
		return memberRepository.findMemberProfileDto(memberId, targetMemberId)
			.orElseThrow(() -> new EntityNotFoundException(MEMBER_UNFOUNDED));
	}

	public List<Member> findTop20ByUsernameStartsWithOrNameStartsWith(String keyword) {
		return memberRepository.findTop20ByUsernameStartsWithOrNameStartsWith(keyword, keyword);
	}

}
