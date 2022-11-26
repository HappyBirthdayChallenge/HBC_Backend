package inha.tnt.hbc.domain.member.vo;

import static inha.tnt.hbc.domain.member.vo.BirthDate.DateType.*;
import static inha.tnt.hbc.util.Constants.*;

import java.time.LocalDate;

import javax.persistence.Column;
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

	@Column(name = "birthday_year", nullable = false)
	@ApiModelProperty(value = "연도(yyyy)", example = "1997", required = true)
	private Integer year;
	@Column(name = "birthday_month", nullable = false)
	@ApiModelProperty(value = "월(MM)", example = "3", required = true)
	private Integer month;
	@Column(name = "birthday_date", nullable = false)
	@ApiModelProperty(value = "일(dd)", example = "6", required = true)
	private Integer date;
	@Column(name = "birthday_type", nullable = false)
	@ApiModelProperty(value = "유형(양력 | 음력)", example = "SOLAR", required = true)
	@Enumerated(EnumType.STRING)
	private DateType type;

	@JsonIgnore
	public static BirthDate getInitial() {
		return BirthDate.builder()
			.year(NONE)
			.month(NONE)
			.date(NONE)
			.type(SOLAR)
			.build();
	}

	private static BirthDate of(Integer year, Integer month, Integer date) {
		return BirthDate.builder()
			.year(year)
			.month(month)
			.date(date)
			.type(SOLAR)
			.build();
	}

	public LocalDate convert() {
		return LocalDate.of(this.year, this.month, this.date);
	}

	@JsonIgnore
	public boolean isInitial() {
		return this.year == NONE || this.month == NONE || this.date == NONE;
	}

	@JsonIgnore
	public BirthDate getNextBirthdate() {
		final LocalDate now = LocalDate.now();
		int year = now.getYear();
		int date = this.date;
		if (shouldIncreaseYear(now)) {
			year++;
		}
		if (shouldDecreaseDate(now)) {
			date--;
		}
		return BirthDate.of(year, this.month, date);
	}

	private boolean shouldIncreaseYear(LocalDate now) {
		return isAfterBirthday(now);
	}

	private boolean shouldDecreaseDate(LocalDate now) {
		return isBirthdayLeapDay() && (
			(isLeapYear(now) && isAfterLeapDay(now)) ||
			(!isLeapYear(now) && !isLeapYear(now.plusYears(1L))) ||
			(!isLeapYear(now) && isBeforeLeapDay(now))
		);
	}

	@JsonIgnore
	private boolean isAfterBirthday(LocalDate now) {
		return now.isAfter(LocalDate.of(now.getYear(), this.month, this.date));
	}

	@JsonIgnore
	private boolean isBirthdayLeapDay() {
		return this.month.equals(2) && this.date.equals(29);
	}

	@JsonIgnore
	private boolean isBeforeLeapDay(LocalDate now) {
		return now.getMonthValue() < 2 || (now.getMonthValue() == 2 && now.getDayOfMonth() < 29);
	}

	@JsonIgnore
	private boolean isAfterLeapDay(LocalDate now) {
		return now.getMonthValue() > 2;
	}

	@JsonIgnore
	private boolean isLeapYear(LocalDate now) {
		final int year = now.getYear();
		return year % 4 == 0 && year % 100 != 0;
	}

	@Getter
	@AllArgsConstructor
	public enum DateType {
		SOLAR("양력"), LUNAR("음력");

		private final String description;
	}

}
