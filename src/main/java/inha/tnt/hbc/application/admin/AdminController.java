package inha.tnt.hbc.application.admin;

import static inha.tnt.hbc.model.ResultCode.*;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import inha.tnt.hbc.domain.member.entity.Member;
import inha.tnt.hbc.domain.member.repository.MemberRepository;
import inha.tnt.hbc.model.ResultResponse;
import inha.tnt.hbc.model.admin.AdminApi;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AdminController implements AdminApi {

	private final MemberRepository memberRepository;

	@Override
	public ResponseEntity<ResultResponse> getMembers() {
		final List<Member> members = memberRepository.findAll();
		return ResponseEntity.ok(ResultResponse.of(GET_MEMBERS_SUCCESS, members));
	}

	@Override
	public ResponseEntity<ResultResponse> deleteMember(Long memberId) {
		memberRepository.deleteById(memberId);
		return ResponseEntity.ok(ResultResponse.of(DELETE_MEMBER_SUCCESS));
	}

}
