package inha.tnt.hbc.util;

import static java.time.format.DateTimeFormatter.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeUtils {

	public static final DateTimeFormatter YYYY_MM_DD_HH_MM_SS = ofPattern("yyyyMMddhhmmss");
	public static final DateTimeFormatter YYYY_MM_DD = ofPattern("yyyyMMdd");

	public static String convertToString(LocalDateTime time) {
		return time.format(YYYY_MM_DD_HH_MM_SS);
	}

	public static String convertToString(LocalDate time) {
		return time.format(YYYY_MM_DD);
	}

	public static LocalDateTime convertToLocalDateTime(String time) {
		return LocalDateTime.parse(time, YYYY_MM_DD_HH_MM_SS);
	}

}
