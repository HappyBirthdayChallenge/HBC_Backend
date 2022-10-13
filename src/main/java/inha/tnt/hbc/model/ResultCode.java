package inha.tnt.hbc.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResultCode {

	// Member
	USERNAME_AVAILABLE(200, "R-M001", "사용 가능한 아이디입니다."),
	EMAIL_AVAILABLE(200, "R-M002", "사용 가능한 이메일입니다."),
	SIGNUP_SUCCESS(200, "R-M003", "회원가입에 성공하였습니다."),
	USERNAME_ALREADY_USED(200, "R-M004", "이미 사용하고 있는 아이디입니다."),
	EMAIL_ALREADY_USED(200, "R-M005", "이미 사용하고 있는 이메일입니다."),
	NAME_EMAIL_UNFOUNDED(200, "R-M006", "이름 혹은 이메일을 사용하는 회원이 존재하지 않습니다."),
	NAME_EMAIL_FOUNDED(200, "R-M007", "이름과 이메일을 사용하는 회원이 존재합니다."),
	USERNAME_FIND_SUCCESS(200, "R-M008", "아이디 찾기에 성공하였습니다."),
	PASSWORD_FIND_SUCCESS(200, "R-M009", "비밀번호 찾기에 성공하였습니다."),
	USERNAME_PASSWORD_INCORRECT(200, "R-M010", "아이디와 비밀번호가 올바르지 않습니다."),
	SIGNIN_SUCCESS(200, "R-M011", "로그인에 성공하였습니다."),

	// Jwt
	JWT_REISSUE_SUCCESS(200, "R-J001", "토큰 재발급에 성공하였습니다."),

	// Email
	EMAIL_SEND_SUCCESS(200, "R-E001", "메일 전송에 성공하였습니다."),

	// Identity Verification
	CODE_VERIFIED(200, "R-IV001", "인증 코드 검증에 성공하였습니다."),
	CODE_UNVERIFIED(200, "R-IV002", "유효하지 않은 인증 코드입니다."),
	KEY_UNVERIFIED(200, "R-IV003", "유효하지 않은 인증 키입니다."),
	;

	private final int status;
	private final String code;
	private final String message;

}
