package inha.tnt.hbc.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PageDto {

	@NotNull
	@Min(1)
	@ApiModelProperty(value = "페이지", required = true, example = "1")
	private int page;
	@NotNull
	@Min(1)
	@ApiModelProperty(value = "페이지당 개수", required = true, example = "10")
	private int size;
	
}
