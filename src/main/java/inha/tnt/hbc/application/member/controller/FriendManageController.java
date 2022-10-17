package inha.tnt.hbc.application.member.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import inha.tnt.hbc.model.PageDto;
import inha.tnt.hbc.model.ResultResponse;
import inha.tnt.hbc.model.member.FriendManageApi;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class FriendManageController implements FriendManageApi {

	@Override
	public ResponseEntity<ResultResponse> getFriends(PageDto pageDto) {
		return null;
	}

	@Override
	public ResponseEntity<ResultResponse> addFriend(Long memberId) {
		return null;
	}

}
