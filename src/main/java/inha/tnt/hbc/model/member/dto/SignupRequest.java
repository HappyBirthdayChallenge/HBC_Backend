package inha.tnt.hbc.model.member.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import inha.tnt.hbc.annotation.BirthDay;
import inha.tnt.hbc.annotation.Phone;
import inha.tnt.hbc.annotation.Username;
import inha.tnt.hbc.vo.BirthDate;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

// TODO: Bean Validation 2개 이상 -> custom validator
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class SignupRequest {

	@Username(check = true)
	@ApiModelProperty(value = "아이디(영문자 || 숫자)[5, 20]", example = "dkdlel123", required = true)
	private String username;
	@NotNull
	@Pattern(regexp = "^.*(?=^.{10,20}$)(?=.*\\d)(?=.*[a-zA-Z])(?=.*[`~!@#$%^&*()]).*$")
	@ApiModelProperty(value = "비밀번호(영문자 && 숫자 && 특수문자)[10, 20]", example = "qlalfqjsgh1@", required = true)
	private String password;
	@NotNull
	@Pattern(regexp = "^.*(?=^.{10,20}$)(?=.*\\d)(?=.*[a-zA-Z])(?=.*[`~!@#$%^&*()]).*$")
	@ApiModelProperty(value = "비밀번호(영문자 && 숫자 && 특수문자)[10, 20]", example = "qlalfqjsgh1@", required = true)
	private String passwordCheck;
	@Phone(check = true)
	@ApiModelProperty(value = "휴대폰 번호", required = true, example = "010-9128-5708")
	private String phone;
	@NotNull
	@Pattern(regexp = "^[가-힣A-Za-z\\d]{2,20}$")
	@ApiModelProperty(value = "이름(한글 || 영문자 || 숫자)[2, 20]", example = "손흥민", required = true)
	private String name;
	@BirthDay
	private BirthDate birthDate;
	@NotNull
	@Pattern(regexp = "^[A-Za-z\\d]{8}-[A-Za-z\\d]{4}-[A-Za-z\\d]{4}-[A-Za-z\\d]{4}-[A-Za-z\\d]{12}$")
	@ApiModelProperty(value = "인증 키", example = "550e8400-e29b-41d4-a716-446655440000", required = true)
	private String key;

}
