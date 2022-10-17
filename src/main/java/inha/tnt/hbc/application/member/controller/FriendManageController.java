package inha.tnt.hbc.application.member.controller;

import static inha.tnt.hbc.model.ResultCode.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import inha.tnt.hbc.application.member.service.FriendManageService;
import inha.tnt.hbc.model.PageDto;
import inha.tnt.hbc.model.ResultResponse;
import inha.tnt.hbc.model.member.FriendManageApi;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class FriendManageController implements FriendManageApi {

	private final FriendManageService friendManageService;

	@Override
	public ResponseEntity<ResultResponse> getFriends(PageDto pageDto) {

		return ResponseEntity.ok(ResultResponse.of(GET_FRIENDS_SUCCESS));
	}

	@Override
	public ResponseEntity<ResultResponse> addFriend(Long memberId) {
		friendManageService.addFriend(memberId);
		return ResponseEntity.ok(ResultResponse.of(ADD_FRIEND_SUCCESS));
	}

}
