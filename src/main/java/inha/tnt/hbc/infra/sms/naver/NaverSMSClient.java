package inha.tnt.hbc.infra.sms.naver;

import static com.amazonaws.auth.SigningAlgorithm.*;
import static inha.tnt.hbc.util.Constants.*;
import static java.nio.charset.StandardCharsets.*;
import static org.springframework.http.HttpMethod.*;
import static org.springframework.http.MediaType.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import inha.tnt.hbc.infra.sms.SMSClient;
import inha.tnt.hbc.infra.sms.naver.dto.SMSMessage;
import inha.tnt.hbc.infra.sms.naver.dto.SMSRequest;
import inha.tnt.hbc.infra.sms.naver.dto.SMSResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Primary
@Component
@RequiredArgsConstructor
public class NaverSMSClient implements SMSClient {

	private final RestTemplate restTemplate;
	private final ObjectMapper objectMapper;
	@Value("${sms.naver.key.access}")
	private String ACCESS_KEY;
	@Value("${sms.naver.key.secret}")
	private String SECRET_KEY;
	@Value("${sms.naver.url.host}")
	private String SMS_HOST;
	@Value("${sms.naver.url.endpoint}")
	private String SMS_ENDPOINT;
	@Value("${sms.naver.from}")
	private String FROM;

	/**
	 * <a href="https://api.ncloud-docs.com/docs/ai-application-service-sens-smsv2#%EB%A9%94%EC%8B%9C%EC%A7%80%EB%B0%9C%EC%86%A1"><b>Reference</b></a>
	 * @param phone 휴대폰 번호
	 * @param content SMS 내용
	 */
	@Override
	public void sendSMS(String phone, String content) {
		final String timestamp = Long.toString(System.currentTimeMillis());
		final HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(APPLICATION_JSON);
		httpHeaders.set("x-ncp-apigw-timestamp", timestamp);
		httpHeaders.set("x-ncp-iam-access-key", ACCESS_KEY);
		httpHeaders.set("x-ncp-apigw-signature-v2", getSignature(timestamp));
		final List<SMSMessage> messages = List.of(new SMSMessage(phone.replaceAll(DASH, EMPTY), content));
		final SMSRequest smsRequest = new SMSRequest(content, messages, FROM);
		try {
			final String json = objectMapper.writeValueAsString(smsRequest);
			final HttpEntity<String> request = new HttpEntity<>(json, httpHeaders);
			final URI uri = new URI(SMS_HOST + SMS_ENDPOINT);
			final ResponseEntity<SMSResponse> response = restTemplate.postForEntity(uri, request, SMSResponse.class);
			if (response.getStatusCode().is2xxSuccessful()) {
				log.info("SMS Request Success: {}", phone);
			} else {
				log.error("SMS Request Failed: {}", response);
			}
		} catch (JsonProcessingException | URISyntaxException e) {
			log.error("SMS Request Failed: ", e);
			throw new RuntimeException(e);
		}
	}

	private String getSignature(String timestamp) {
		final String message = POST.name()
			+ SPACE
			+ SMS_ENDPOINT
			+ NEWLINE
			+ timestamp
			+ NEWLINE
			+ ACCESS_KEY;

		final SecretKeySpec signingKey = new SecretKeySpec(SECRET_KEY.getBytes(UTF_8), HmacSHA256.name());
		final Mac mac;
		try {
			mac = Mac.getInstance(HmacSHA256.name());
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
		try {
			mac.init(signingKey);
		} catch (InvalidKeyException e) {
			throw new RuntimeException(e);
		}
		final byte[] rawHmac = mac.doFinal(message.getBytes(UTF_8));
		return Base64.encodeBase64String(rawHmac);
	}

}
