package inha.tnt.hbc.vo;

import javax.persistence.Embeddable;

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
	private DateType type;

	@Getter
	@AllArgsConstructor
	public enum DateType {
		SOLAR("양력"), LUNAR("음력");

		private final String description;
	}

}
