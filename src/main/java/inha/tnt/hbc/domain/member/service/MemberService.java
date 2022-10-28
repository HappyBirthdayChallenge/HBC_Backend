package inha.tnt.hbc.domain.member.service;

import static inha.tnt.hbc.model.ErrorCode.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import inha.tnt.hbc.domain.member.entity.Member;
import inha.tnt.hbc.domain.member.repository.MemberRepository;
import inha.tnt.hbc.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;

	@Transactional
	public Member save(Member member) {
		return memberRepository.save(member);
	}

	@Transactional(readOnly = true)
	public Member findByUsername(String username) {
		return memberRepository.findByUsername(username)
			.orElseThrow(() -> new EntityNotFoundException(MEMBER_UNFOUNDED));
	}

	@Transactional(readOnly = true)
	public boolean isExistingUsername(String username) {
		return memberRepository.findByUsername(username).isPresent();
	}

	public Member findById(Long memberId) {
		return memberRepository.findById(memberId)
			.orElseThrow(() -> new EntityNotFoundException(MEMBER_UNFOUNDED));
	}

	@Transactional(readOnly = true)
	public boolean isExistingPhone(String phone) {
		return memberRepository.findByPhone(phone).isPresent();
	}

	@Transactional(readOnly = true)
	public Member findByNameAndPhone(String name, String phone) {
		return memberRepository.findByNameAndPhone(name, phone)
			.orElseThrow(() -> new EntityNotFoundException(MEMBER_UNFOUNDED));
	}

}
