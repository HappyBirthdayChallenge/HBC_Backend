package inha.tnt.hbc.application.member.controller;

import static inha.tnt.hbc.model.ResultCode.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import inha.tnt.hbc.model.member.dto.FollowerPageResponse;
import inha.tnt.hbc.model.member.dto.FollowingPageResponse;
import inha.tnt.hbc.application.member.service.FriendFacadeService;
import inha.tnt.hbc.model.ResultResponse;
import inha.tnt.hbc.model.member.FriendApi;
import inha.tnt.hbc.model.member.dto.MemberSearchResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class FriendController implements FriendApi {

	private final FriendFacadeService friendFacadeService;

	@Override
	public ResponseEntity<ResultResponse> getFollowings(int page, int size, Long memberId) {
		final FollowingPageResponse response = friendFacadeService.getFollowings(page, size, memberId);
		return ResponseEntity.ok(ResultResponse.of(GET_FOLLOWINGS_SUCCESS, response));
	}

	@Override
	public ResponseEntity<ResultResponse> addFriend(Long memberId) {
		friendFacadeService.addFriend(memberId);
		return ResponseEntity.ok(ResultResponse.of(ADD_FRIEND_SUCCESS));
	}

	@Override
	public ResponseEntity<ResultResponse> getFollowers(int page, int size, Long memberId) {
		final FollowerPageResponse response = friendFacadeService.getFollowers(page, size, memberId);
		return ResponseEntity.ok(ResultResponse.of(GET_FOLLOWERS_SUCCESS, response));
	}

	@Override
	public ResponseEntity<ResultResponse> deleteFriend(Long memberId) {
		friendFacadeService.deleteFriend(memberId);
		return ResponseEntity.ok(ResultResponse.of(DELETE_FRIEND_SUCCESS));
	}

	@Override
	public ResponseEntity<ResultResponse> searchFollower(String keyword) {
		final MemberSearchResponse response = friendFacadeService.searchFollower(keyword);
		return ResponseEntity.ok(ResultResponse.of(SEARCH_MEMBER_SUCCESS, response));
	}

	@Override
	public ResponseEntity<ResultResponse> searchFollowing(String keyword) {
		final MemberSearchResponse response = friendFacadeService.searchFollowing(keyword);
		return ResponseEntity.ok(ResultResponse.of(SEARCH_MEMBER_SUCCESS, response));
	}

}
