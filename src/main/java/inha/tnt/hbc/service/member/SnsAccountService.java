package inha.tnt.hbc.service.member;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import inha.tnt.hbc.domain.member.entity.Member;
import inha.tnt.hbc.domain.member.entity.oauth2.SnsAccount;
import inha.tnt.hbc.domain.member.entity.oauth2.SnsAccountPrimaryKey;
import inha.tnt.hbc.domain.member.repository.SnsAccountRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SnsAccountService {

	private final SnsAccountRepository snsAccountRepository;

	@Transactional
	public SnsAccount connect(Member member, SnsAccountPrimaryKey primaryKey) {
		return snsAccountRepository.save(new SnsAccount(primaryKey, member));
	}

	@Transactional(readOnly = true)
	public Optional<SnsAccount> getWithMember(SnsAccountPrimaryKey primaryKey) {
		return snsAccountRepository.findWithMemberById(primaryKey);
	}

}
