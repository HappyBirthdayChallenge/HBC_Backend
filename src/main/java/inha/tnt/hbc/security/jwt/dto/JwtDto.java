package inha.tnt.hbc.security.jwt.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class JwtDto {

	private String accessToken;
	private String refreshToken;

}
