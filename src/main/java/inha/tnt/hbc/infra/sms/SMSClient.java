package inha.tnt.hbc.infra.sms;

import org.springframework.scheduling.annotation.Async;

public interface SMSClient {

	@Async
	void sendSMS(String phone, String content);

}
