package inha.tnt.hbc.model.alarm.dto;

import org.springframework.data.domain.Page;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import inha.tnt.hbc.domain.alarm.dto.AlarmDto;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AlarmPageResponse {

	private Page<AlarmDto> page;

	public static AlarmPageResponse of(Page<AlarmDto> page) {
		return new AlarmPageResponse(page);
	}

}
