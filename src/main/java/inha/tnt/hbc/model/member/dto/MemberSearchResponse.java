package inha.tnt.hbc.model.member.dto;

import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import inha.tnt.hbc.domain.member.dto.MemberSearchDto;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberSearchResponse {

	private List<MemberSearchDto> result;

	public static MemberSearchResponse of(List<MemberSearchDto> result) {
		return new MemberSearchResponse(result);
	}

}
