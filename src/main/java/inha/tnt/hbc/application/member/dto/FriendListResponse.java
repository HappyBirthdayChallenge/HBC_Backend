package inha.tnt.hbc.application.member.dto;

import org.springframework.data.domain.Page;

import inha.tnt.hbc.domain.member.dto.FriendDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FriendListResponse {

	private Page<FriendDto> friendPage;

}
