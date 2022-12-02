package inha.tnt.hbc.domain.member.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import inha.tnt.hbc.domain.member.entity.Friend;
import inha.tnt.hbc.domain.member.entity.Member;
import inha.tnt.hbc.domain.member.repository.querydsl.FriendRepositoryQuerydsl;

@Transactional(readOnly = true)
public interface FriendRepository extends JpaRepository<Friend, Long>, FriendRepositoryQuerydsl {

	Optional<Friend> findByMemberAndFriendMember(Member member, Member friendMember);

	List<Friend> findAllByFriendMember(Member member);

	List<Friend> findAllByMemberIdAndFriendMemberIdIn(Long memberId, List<Long> friendMemberIds);

}
