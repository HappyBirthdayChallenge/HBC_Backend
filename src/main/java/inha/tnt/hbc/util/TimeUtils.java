package inha.tnt.hbc.util;

import static java.time.format.DateTimeFormatter.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeUtils {

	public static final String YYYY_MM_DD_HH_MM_SS = "yyyyMMddhhmmss";
	public static final DateTimeFormatter formatter = ofPattern(YYYY_MM_DD_HH_MM_SS);

	public static String convertToString(LocalDateTime time) {
		return time.format(formatter);
	}

	public static LocalDateTime convertToLocalDateTime(String time) {
		return LocalDateTime.parse(time, formatter);
	}

}
