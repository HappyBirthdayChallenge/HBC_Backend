package inha.tnt.hbc.security.jwt.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class JwtDto {

	@ApiModelProperty(value = "인가 토큰", example = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJwayI6MSwiYXV0aG9yaXRpZXMiOiJST0xFX1VTRVIiLCJpc3N1ZXIiOiJoYmMiLCJzdWIiOiJBQ0NFU1NfVE9LRU4iLCJpYXQiOjE2NjU2OTA3NTQsImV4cCI6MTY2NTY5MjU1NH0.l8KA4fktOWPoVm2y6Abs0l4GYyu1LXdbj08fKViYmV7E49NYSZ3N6NLKB4F4SXP3nUtVSV1oJsYRHI1bMnATfw")
	private String accessToken;
	@ApiModelProperty(value = "재발급 토큰", example = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJwayI6MSwiYXV0aG9yaXRpZXMiOiJST0xFX1VTRVIiLCJpc3N1ZXIiOiJoYmMiLCJzdWIiOiJSRUZSRVNIX1RPS0VOIiwiaWF0IjoxNjY1NjkwNzU0LCJleHAiOjE2NjYyOTU1NTR9.72H3TohLuzi9eRKKMAa7_uXuP9eXYIM3EUNleyyyCDmo5bAic8N_syu2kj1G9EjL7AUoYuXuOtXPgiltu7pYuQ")
	private String refreshToken;

}
