package inha.tnt.hbc.infra.sms.naver.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SMSResponse {

	private String requestId;
	private String requestTime;
	private String statusCode;
	private String statusName;

}
