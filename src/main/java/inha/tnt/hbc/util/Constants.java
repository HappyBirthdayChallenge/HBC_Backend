package inha.tnt.hbc.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Constants {

	public static final int DAYS_BEFORE_ROOM_CREATION = 7;
	public static final int NONE = -1;
	public static final int AUTH_CODE_LENGTH = 6;
	public static final int MEGA = 1_000_000;
	public static final String AUTH_CODE_MESSAGE = "[HappyBirthdayChallenge]\n인증 코드는 %s 입니다.";

	public static final String ALL_PATTERN = "/**";

	public static final String BREAK_TAG = "<br>";
	public static final String NEWLINE = "\n";

	public static final String DASH = "-";
	public static final String EMPTY = "";
	public static final String SPACE = " ";
	public static final String DELIMITER = "_";
	public static final String COMMA = ",";
	public static final String DOT = ".";
	public static final String BACK_SLASH = "\\";
	public static final String SLASH = "/";

	public static final String AUTHORIZATION_HEADER = "Authorization";
	public static final String CONTENT_TYPE_HEADER = "Content-type";

	public static final String ROOT_DIRECTORY = System.getProperty("user.dir");
	public static final String TEMPORAL_DIRECTORY = "upload";

}
