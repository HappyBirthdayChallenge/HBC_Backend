package inha.tnt.hbc.model.member.dto;

import org.springframework.data.domain.Page;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import inha.tnt.hbc.domain.member.dto.FollowerDto;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FollowerPageResponse {

	private Page<FollowerDto> page;

	public static FollowerPageResponse of(Page<FollowerDto> page) {
		return FollowerPageResponse.builder()
			.page(page)
			.build();
	}

}
