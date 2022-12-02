package inha.tnt.hbc.model.member.dto;

import org.springframework.data.domain.Page;

import inha.tnt.hbc.domain.member.dto.FollowingDto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FollowingPageResponse {

	private Page<FollowingDto> page;

	public static FollowingPageResponse of(Page<FollowingDto> page) {
		return FollowingPageResponse.builder()
			.page(page)
			.build();
	}

}
