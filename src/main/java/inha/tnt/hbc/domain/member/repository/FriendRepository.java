package inha.tnt.hbc.domain.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import inha.tnt.hbc.domain.member.entity.Friend;
import inha.tnt.hbc.domain.member.entity.Member;
import inha.tnt.hbc.domain.member.repository.querydsl.FriendRepositoryQuerydsl;

public interface FriendRepository extends JpaRepository<Friend, Long>, FriendRepositoryQuerydsl {

	Optional<Friend> findByMemberAndFriendMember(Member member, Member friendMember);

}
