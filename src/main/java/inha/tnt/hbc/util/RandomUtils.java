package inha.tnt.hbc.util;

import java.util.Random;
import java.util.UUID;

public class RandomUtils {

	public static String generateNumber(int length) {
		final StringBuilder builder = new StringBuilder(length);
		final Random random = new Random();
		random.setSeed(System.currentTimeMillis());
		for (int i = 0; i < length; i++) {
			builder.append(random.nextInt(10));
		}
		return builder.toString();
	}

	public static String generateAuthKey() {
		return UUID.randomUUID().toString();
	}

}
