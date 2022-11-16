package inha.tnt.hbc.domain.member.repository.querydsl;

import java.time.LocalDate;
import java.util.List;

import inha.tnt.hbc.domain.member.entity.Member;

public interface MemberRepositoryQuerydsl {

	List<Member> findAllByRoomCreateDate(LocalDate roomCreateDate);

}
