package inha.tnt.hbc.domain.member.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import inha.tnt.hbc.domain.member.entity.Member;
import inha.tnt.hbc.domain.member.entity.oauth2.OAuth2Account;
import inha.tnt.hbc.domain.member.entity.oauth2.OAuth2AccountPK;
import inha.tnt.hbc.domain.member.repository.OAuth2AccountRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OAuth2AccountService {

	private final OAuth2AccountRepository OAuth2AccountRepository;

	@Transactional
	public OAuth2Account connect(Member member, OAuth2AccountPK primaryKey) {
		return OAuth2AccountRepository.save(new OAuth2Account(primaryKey, member));
	}

	@Transactional(readOnly = true)
	public Optional<OAuth2Account> getWithMember(OAuth2AccountPK primaryKey) {
		return OAuth2AccountRepository.findWithMemberById(primaryKey);
	}

}
