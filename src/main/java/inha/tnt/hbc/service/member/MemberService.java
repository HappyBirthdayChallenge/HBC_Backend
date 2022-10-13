package inha.tnt.hbc.service.member;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import inha.tnt.hbc.domain.member.entity.Member;
import inha.tnt.hbc.domain.member.repository.MemberRepository;
import inha.tnt.hbc.vo.BirthDate;
import inha.tnt.hbc.vo.Image;
import lombok.RequiredArgsConstructor;

// TODO: facade 패턴 적용 고민
//  application's service / domain's service 분리
@Service
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;

	@Transactional
	public Member signup(String username, String password, String name, String email, BirthDate birthDate,
		Image image) {
		final Member member = Member.builder()
			.username(username)
			.password(passwordEncoder.encode(password))
			.name(name)
			.email(email)
			.birthDate(birthDate)
			.image(image)
			.build();
		return memberRepository.save(member);
	}

}
