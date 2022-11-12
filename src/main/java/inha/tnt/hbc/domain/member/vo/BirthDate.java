package inha.tnt.hbc.domain.member.vo;

import static inha.tnt.hbc.util.Constants.*;

import java.time.LocalDate;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@Embeddable
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class BirthDate {

	@ApiModelProperty(value = "연도(yyyy)", example = "1997", required = true)
	private Integer year;
	@ApiModelProperty(value = "월(MM)", example = "3", required = true)
	private Integer month;
	@ApiModelProperty(value = "일(dd)", example = "6", required = true)
	private Integer date;
	@ApiModelProperty(value = "유형(양력 | 음력)", example = "SOLAR", required = true)
	@Enumerated(EnumType.STRING)
	private DateType type;

	public static BirthDate getInitial() {
		return BirthDate.builder()
			.year(NONE)
			.month(NONE)
			.date(NONE)
			.type(DateType.SOLAR)
			.build();
	}

	public short getNextBirthdayYear() {
		final LocalDate now = LocalDate.now();
		final LocalDate birthDate = LocalDate.of(now.getYear(), this.month, this.date);
		if (isBirthdayLeapDay() && !isTodayLeapYear() && isTodayBeforeLeapDay()) {
			return (short)now.getYear();
		}
		if (now.isAfter(birthDate)) {
			return (short)(now.getYear() + 1);
		}
		return (short)now.getYear();
	}

	@JsonIgnore
	public boolean isInitial() {
		return this.year == NONE || this.month == NONE || this.date == NONE;
	}

	@JsonIgnore
	private boolean isBirthdayLeapDay() {
		return month.equals(2) && date.equals(29);
	}

	@JsonIgnore
	private boolean isTodayBeforeLeapDay() {
		final LocalDate now = LocalDate.now();
		return now.getMonthValue() == 2 && now.getDayOfMonth() == 28;
	}

	@JsonIgnore
	private boolean isTodayLeapYear() {
		final int year = LocalDate.now().getYear();
		return year % 4 == 0 && year % 100 != 0;
	}

	@Getter
	@AllArgsConstructor
	public enum DateType {
		SOLAR("양력"), LUNAR("음력");

		private final String description;
	}

}
