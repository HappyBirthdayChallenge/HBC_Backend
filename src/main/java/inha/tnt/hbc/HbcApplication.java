package inha.tnt.hbc;

import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HbcApplication {

	public static void main(String[] args) {
		SpringApplication.run(HbcApplication.class, args);
	}

	@PostConstruct
	protected void init() {
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
	}

}
