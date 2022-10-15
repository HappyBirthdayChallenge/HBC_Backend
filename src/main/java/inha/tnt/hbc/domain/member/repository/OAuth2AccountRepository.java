package inha.tnt.hbc.domain.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import inha.tnt.hbc.domain.member.entity.oauth2.OAuth2Account;
import inha.tnt.hbc.domain.member.entity.oauth2.OAuth2AccountPK;

public interface OAuth2AccountRepository extends JpaRepository<OAuth2Account, OAuth2AccountPK> {

	@Query("select oa from OAuth2Account oa join fetch oa.member where oa.id = :id")
	Optional<OAuth2Account> findWithMemberById(@Param("id") OAuth2AccountPK id);

}
