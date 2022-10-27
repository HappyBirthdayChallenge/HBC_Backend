package inha.tnt.hbc.util;

public class MessageTemplates {

	private static final String PATTERN_MESSAGE_FORMAT = "\"%s\"와 일치해야 합니다";

	public static String getPatternMessage(String regexp) {
		return String.format(PATTERN_MESSAGE_FORMAT, regexp);
	}

}
