package inha.tnt.hbc.domain.member.repository.querydsl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import inha.tnt.hbc.domain.member.dto.MemberProfileDto;
import inha.tnt.hbc.domain.member.entity.Member;

public interface MemberRepositoryQuerydsl {

	List<Member> findAllByRoomCreateDate(LocalDate roomCreateDate);

	Optional<MemberProfileDto> findMemberProfileDto(Long memberId, Long targetMemberId);

}
