package inha.tnt.hbc.model.member.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FindUsernameResponse {

	@ApiModelProperty(value = "아이디", example = "dkdlel123")
	private String username;

}
