package inha.tnt.hbc.infra.push.firebase;

import static com.google.firebase.messaging.MessagingErrorCode.*;

import java.io.IOException;
import java.time.LocalDateTime;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import inha.tnt.hbc.domain.member.entity.Member;
import inha.tnt.hbc.domain.member.service.FCMTokenService;

/**
 * <a href="https://firebase.google.com/docs/reference/fcm/rest/v1/projects.messages?authuser=0&hl=ko">Reference</a>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {

	private final static String TITLE = "Happy Birthday Challenge";
	private final static String IMAGE_URI = "https://hbc-bucket.s3.ap-northeast-2.amazonaws.com/logo.png?response-content-disposition=inline&X-Amz-Security-Token=IQoJb3JpZ2luX2VjEJL%2F%2F%2F%2F%2F%2F%2F%2F%2F%2FwEaDmFwLW5vcnRoZWFzdC0yIkYwRAIgWCg7%2Bdxbf0FvrxVd8IQlCn8NNkVg9eKp8vZQK7FwB%2BYCIBYbeTrWbN0CmJNsuukdB6w73sF7nptktvSiIO6kwAYkKu0CCIv%2F%2F%2F%2F%2F%2F%2F%2F%2F%2FwEQABoMMjEzNjczOTcyODk2IgyZZiUWew3phnzDoG4qwQIs8aLDOkZNCrZvX8NPYai7L5M6%2FNxW6uH8y3aIuPaadDjHmfuA7vHOPWnSfI5QxZkNk5416B%2F4xcoqJWcqM0Sa4yTDa3aTGtUH2lbaoc2ac4A3tq81xDmeTgTfiMRoy1KfsYI34FQvQ5NEU%2FYwSNZ5E8Md1JAsZs6u6Pj20eq38Tzi0ewRbTpGqeu%2BArfKZKa8tJZj%2BHtskK2QBC%2BLpYBbusWyMZkmA3DEHFIyoygZxZj7UEAs9WxshoANB8V6CJtrb0XlBh0S8KcOioWU%2BD2aB3LU%2Bsopf8dyDfZXT1BxUloMZQOhc%2BaiVmjgmZs2WU8w9k%2BPMLIQejFPE%2FY%2FHUfEXr4xl5vU6ql3zCi2MIYGWYLjl8wLhy2GNqua79co6VtArw%2BS5KI%2BPDLoZVKP6nURWJxch7jbk9f2JnUX0OWw2RIwytW3mwY6tALIKtUsUc7%2FiM6iRylwu4ho0nZlRSLAc1OeK4l%2Fs%2B3QqzSsJazlu%2FYd1GgZzBZF3QmzkAkvfmSM%2BSmwBejaenu3qu8oukD4lep4%2F1yA05beuldiaN8t5B2%2BlQRwNX1WXSDiekZ2S2l2Vl3nQrbACofQT23sFmD2Of5KxCizek1k%2FcSmZ1eLUgZrsnkA3siTz5hqDyn4xzsRc7hEiNagS0mRHfKiA4nlD5EmKOMuZpPTzOmoTCxRN8mbWgMBwboUviNoPgTkX85eFb5VAGzNZvs4l8BkpX2cy64zQhc%2FEjfhCsprI4p5TLoUJlhT5wob%2BBTYXTbdArnopLaQyT9fQd%2BlX9VRMaw9vrMKig78WrDM2%2FoxGmHbe6rxQeWWQRZS3HoGzC%2F%2F0MRRKyAmyDw078Js1C6dFA%3D%3D&X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Date=20221111T140911Z&X-Amz-SignedHeaders=host&X-Amz-Expires=300&X-Amz-Credential=ASIATDP7WCSQKSOWTEA6%2F20221111%2Fap-northeast-2%2Fs3%2Faws4_request&X-Amz-Signature=0b7caf5bb3b88b043294ed66ed5ef26920f63e48f097cc67403d4efd94121d6d";
	private final static String TIME = "time";
	private final static String NEW_MESSAGE = "누군가 내 파티룸에 축하 메시지를 작성했어요!";
	private final static String NEW_FRIEND = "%s님이 회원님을 친구로 추가했어요!";
	private final FCMTokenService fcmTokenService;
	@Value("${fcm.key.path}")
	private String FCM_KEY_PATH;

	@PostConstruct
	public void init() {
		try {
			final ClassPathResource resource = new ClassPathResource(FCM_KEY_PATH);
			final FirebaseOptions options = FirebaseOptions.builder()
					.setCredentials(GoogleCredentials.fromStream(resource.getInputStream()))
					.build();
			FirebaseApp.initializeApp(options);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Async
	public void sendFriendAlarm(Member subject, Member object) {
		fcmTokenService.getFcmTokens(object)
				.forEach(token -> sendNotification(token, object.getId(),
						generateMessage(token, String.format(NEW_FRIEND, subject.getName()))));
	}

	private void sendNotification(String token, Long memberId, Message message) {
		try {
			final String response = FirebaseMessaging.getInstance().send(message);
			log.info("Send Notification Response: {}", response);
		} catch (FirebaseMessagingException e) {
			log.error("[FirebaseMessagingException] ", e);
			if (e.getMessagingErrorCode().equals(UNREGISTERED)) {
				fcmTokenService.deleteFCMToken(memberId, token);
			}
		}
	}

	private Message generateMessage(String token, String messageBody) {
		final String now = LocalDateTime.now().toString();
		final Notification notification = Notification.builder()
				.setTitle(TITLE)
				.setBody(messageBody)
				.setImage(IMAGE_URI)
				.build();
		return Message.builder()
				.putData(TIME, now)
				.setNotification(notification)
				.setToken(token)
				.build();
	}

}
