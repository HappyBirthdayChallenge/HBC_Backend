package inha.tnt.hbc.application.member.controller;

import static inha.tnt.hbc.model.ResultCode.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import inha.tnt.hbc.model.member.dto.FriendListResponse;
import inha.tnt.hbc.application.member.service.FriendFacadeService;
import inha.tnt.hbc.model.ResultResponse;
import inha.tnt.hbc.model.member.FriendApi;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class FriendController implements FriendApi {

	private final FriendFacadeService friendFacadeService;

	@Override
	public ResponseEntity<ResultResponse> getFriends(int page, int size) {
		final FriendListResponse response = friendFacadeService.getFriends(page, size);
		return ResponseEntity.ok(ResultResponse.of(GET_FRIENDS_SUCCESS, response));
	}

	@Override
	public ResponseEntity<ResultResponse> addFriend(Long memberId) {
		friendFacadeService.addFriend(memberId);
		return ResponseEntity.ok(ResultResponse.of(ADD_FRIEND_SUCCESS));
	}

}
