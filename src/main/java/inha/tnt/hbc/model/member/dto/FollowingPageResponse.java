package inha.tnt.hbc.model.member.dto;

import org.springframework.data.domain.Page;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import inha.tnt.hbc.domain.member.dto.FriendDto;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FollowingPageResponse {

	private Page<FriendDto> page;

	public static FollowingPageResponse of(Page<FriendDto> page) {
		return FollowingPageResponse.builder()
			.page(page)
			.build();
	}

}
