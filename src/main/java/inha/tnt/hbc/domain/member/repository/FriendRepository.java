package inha.tnt.hbc.domain.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import inha.tnt.hbc.domain.member.entity.Friend;
import inha.tnt.hbc.domain.member.entity.Member;

public interface FriendRepository extends JpaRepository<Friend, Long> {

	Optional<Friend> findByMemberAndFriendMember(Member member, Member friendMember);

}
