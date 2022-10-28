package inha.tnt.hbc.domain.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import inha.tnt.hbc.domain.member.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

	Optional<Member> findByUsername(String username);

	Optional<Member> findByPhone(String phone);

	Optional<Member> findByNameAndPhone(String name, String phone);

}
