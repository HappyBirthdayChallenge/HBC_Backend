package inha.tnt.hbc.model.member.dto;

import inha.tnt.hbc.annotation.Phone;
import inha.tnt.hbc.domain.member.service.IdentityVerificationService.IdentityVerificationTypes;
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
public class PhoneRequest {

	@Phone
	@ApiModelProperty(value = "휴대폰 번호", required = true, example = "010-9128-5708")
	private String phone;

	@ApiModelProperty(value = "인증 유형", required = true, example = "SIGNUP")
	private IdentityVerificationTypes type;

}
