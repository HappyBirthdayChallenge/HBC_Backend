package inha.tnt.hbc.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResultCode {

	// Admin
	GET_MEMBERS_SUCCESS(200, "R-A001", "회원 목록 조회에 성공하였습니다."),
	DELETE_MEMBER_SUCCESS(200, "R-A002", "회원 탈퇴에 성공하였습니다."),

	// Member
	USERNAME_AVAILABLE(200, "R-M001", "사용 가능한 아이디입니다."),
	EMAIL_AVAILABLE(200, "R-M002", "사용 가능한 이메일입니다."),
	SIGNUP_SUCCESS(200, "R-M003", "회원가입에 성공하였습니다."),
	USERNAME_ALREADY_USED(200, "R-M004", "이미 사용하고 있는 아이디입니다."),
	PHONE_ALREADY_USED(200, "R-M005", "이미 사용하고 있는 휴대폰 번호입니다."),
	NAME_EMAIL_UNFOUNDED(200, "R-M006", "이름 혹은 이메일을 사용하는 회원이 존재하지 않습니다."),
	NAME_EMAIL_FOUNDED(200, "R-M007", "이름과 이메일을 사용하는 회원이 존재합니다."),
	USERNAME_FIND_SUCCESS(200, "R-M008", "아이디 찾기에 성공하였습니다."),
	PASSWORD_FIND_SUCCESS(200, "R-M009", "비밀번호 찾기에 성공하였습니다."),
	USERNAME_PASSWORD_INCORRECT(200, "R-M010", "아이디 혹은 비밀번호가 올바르지 않습니다."),
	SIGNIN_SUCCESS(200, "R-M011", "로그인에 성공하였습니다."),
	BIRTHDAY_SETUP_SUCCESS(200, "R-M012", "생일 입력에 성공하였습니다."),
	ADD_FRIEND_SUCCESS(200, "R-M013", "친구 추가에 성공하였습니다."),
	GET_FRIENDS_SUCCESS(200, "R-M014", "친구 목록 조회에 성공하였습니다."),
	PHONE_AVAILABLE(200, "R-M015", "사용 가능한 휴대폰 번호입니다."),
	IDENTIFY_SUCCESS(200, "R-M016", "본인 여부 확인에 성공하였습니다."),
	IDENTIFY_FAILURE(200, "R-M017", "본인 여부 확인에 실패하였습니다."),
	SIGNOUT_SUCCESS(200, "R-M018", "로그아웃에 성공하였습니다."),
	GET_MY_INFO_SUCCESS(200, "R-M019", "본인 정보 조회에 성공하였습니다."),

	// Jwt
	JWT_REISSUE_SUCCESS(200, "R-J001", "토큰 재발급에 성공하였습니다."),
	JWT_REISSUE_FAILURE(200, "R-J002", "토큰 재발급에 실패하였습니다."),
	JWT_VERIFIED(200, "R-J003", "유효한 토큰입니다."),

	// FCM
	FCM_REFRESH_SUCCESS(200, "R-F001", "FCM 토큰 갱신 요청에 성공하였습니다."),

	// Identity Verification
	CODE_VERIFIED(200, "R-IV001", "인증 코드 검증에 성공하였습니다."),
	CODE_INVALID(200, "R-IV002", "유효하지 않은 인증 코드입니다."),
	KEY_INVALID(200, "R-IV003", "유효하지 않은 인증 키입니다."),
	SEND_CODE_SUCCESS(200, "R-IV004", "인증 코드 전송에 성공하였습니다."),

	// Room
	GET_ROOMS_SUCCESS(200, "R-R001", "파티룸 목록 조회에 성공하였습니다."),
	GET_ROOM_DECORATION_PAGE_SUCCESS(200, "R-R002", "파티룸 장식품 페이지 조회에 성공하였습니다."),
	GET_ROOM_MESSAGE_PAGE_SUCCESS(200, "R-R003", "파티룸 받은 메시지 목록 페이지 조회에 성공하였습니다."),
	SEARCH_MESSAGE_WRITTEN_BY_ME_SUCCESS(200, "R-R004", "파티룸 내가 작성한 메시지 찾기에 성공하였습니다."),
	GET_UNREAD_MESSAGES_COUNT(200, "R-R005", "파티룸 읽지 않은 메시지 개수 조회"),

	// Message
	UPLOAD_MESSAGE_SUCCESS(200, "R-RM001", "메시지 작성에 성공하였습니다."),
	CREATE_MESSAGE_SUCCESS(200, "R-RM002", "메시지 생성에 성공하였습니다."),
	INQUIRY_MESSAGE_SUCCESS(200, "R-RM003", "메시지 조회에 성공하였습니다."),
	CANCEL_MESSAGE_SUCCESS(200, "R-RM004", "메시지 작성 취소에 성공하였습니다."),
	DELETE_MESSAGE_SUCCESS(200, "R-RM005", "메시지 삭제에 성공하였습니다."),
	EDIT_MESSAGE_SUCCESS(200, "R-RM006", "메시지 수정에 성공하였습니다."),
	GET_MESSAGE_WRITTEN_BY_ME_SUCCESS(200, "R-RM007", "내가 작성한 메시지 목록 페이지 조회에 성공하였습니다."),
	READ_MESSAGE_SUCCESS(200, "R-RM008", "메시지 읽음 상태로 변경에 성공하였습니다."),
	LIKE_MESSAGE_SUCCESS(200, "R-RM009", "메시지 좋아요에 성공하였습니다."),

	// File
	UPLOAD_IMAGE_SUCCESS(200, "R-FI001", "이미지 파일 업로드에 성공하였습니다."),
	UPLOAD_VIDEO_SUCCESS(200, "R-FI002", "동영상 파일 업로드에 성공하였습니다."),
	UPLOAD_AUDIO_SUCCESS(200, "R-FI003", "오디오 파일 업로드에 성공하였습니다."),
	DELETE_MESSAGE_FILE_SUCCESS(200, "R-FI004", "메시지 파일 삭제에 성공하였습니다."),
	;

	private final int status;
	private final String code;
	private final String message;

}
