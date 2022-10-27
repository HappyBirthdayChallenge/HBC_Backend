package inha.tnt.hbc.infra.sms.naver.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SMSRequest {

	private final String type = "SMS";
	private final String contentType = "COMM";
	private final String countryCode = "82";
	private String from;
	private String content;
	private List<SMSMessage> messages = new ArrayList<>();

	public SMSRequest(String content, List<SMSMessage> messages, String from) {
		this.content = content;
		this.messages = messages;
		this.from = from;
	}

}
