package inha.tnt.hbc.domain.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import inha.tnt.hbc.domain.member.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

}
