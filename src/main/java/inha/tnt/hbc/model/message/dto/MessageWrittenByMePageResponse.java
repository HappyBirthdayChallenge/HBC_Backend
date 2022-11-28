package inha.tnt.hbc.model.message.dto;

import org.springframework.data.domain.Page;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import inha.tnt.hbc.domain.message.dto.MessageWrittenByMeDto;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MessageWrittenByMePageResponse {

	private Page<MessageWrittenByMeDto> page;

	public static MessageWrittenByMePageResponse of(Page<MessageWrittenByMeDto> page) {
		return new MessageWrittenByMePageResponse(page);
	}

}
