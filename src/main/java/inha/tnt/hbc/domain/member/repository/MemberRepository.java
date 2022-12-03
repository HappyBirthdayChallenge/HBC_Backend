package inha.tnt.hbc.domain.member.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import inha.tnt.hbc.domain.member.entity.Member;
import inha.tnt.hbc.domain.member.repository.querydsl.MemberRepositoryQuerydsl;

@Transactional(readOnly = true)
public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryQuerydsl {

	Optional<Member> findByUsername(String username);

	Optional<Member> findByPhone(String phone);

	Optional<Member> findByNameAndPhone(String name, String phone);

	Optional<Member> findByNameAndPhoneAndUsername(String name, String phone, String username);

	@Query("select distinct m from Member m left join fetch m.oAuth2Accounts")
	List<Member> findAllFetchOAuth2Accounts();

	List<Member> findTop20ByUsernameStartsWithOrNameStartsWith(String username, String name);

}
