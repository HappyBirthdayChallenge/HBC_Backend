package inha.tnt.hbc.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

	// Global
	INTERNAL_SERVER_ERROR(500, "E-G001", "내부 서버 오류입니다."),
	INPUT_VALUE_INVALID(400, "E-G002", "입력 값이 유효하지 않습니다."),
	INPUT_TYPE_INVALID(400, "E-G003", "입력 타입이 유효하지 않습니다."),
	METHOD_NOT_ALLOWED(405, "E-G004", "허용되지 않은 HTTP method 입니다."),
	HTTP_MESSAGE_NOT_READABLE(400, "E-G005", "HTTP Request Body 형식이 올바르지 않습니다."),
	REQUEST_PARAMETER_MISSING(400, "E-G006", "요청 파라미터는 필수입니다."),
	REQUEST_HEADER_MISSING(400, "E-G007", "요청 헤더는 필수입니다."),
	NOT_NULL(400, "E-G008", "널이어서는 안됩니다."),

	// Authorization
	AUTHORIZATION_HEADER_MISSING(401, "E-A001", "인증 헤더는 필수입니다."),
	BEARER_PREFIX_MISSING(401, "E-A002", "인증 토큰에 \"Bearer \" 접두사는 필수입니다."),
	AUTHENTICATION_FAILURE(401, "E-A003", "인증에 실패하였습니다."),
	INSUFFICIENT_AUTHORITY(403, "E-A004", "접근 권한이 부족합니다."),

	// Jwt
	JWT_INVALID(401, "E-J001", "유효하지 않은 토큰입니다."),
	JWT_EXPIRED(401, "E-J002", "만료된 토큰입니다."),
	JWT_MALFORMED(401, "E-J003", "구조가 올바르지 않은 토큰입니다."),
	JWT_UNSUPPORTED(401, "E-J004", "지원하지 않는 형식의 토큰입니다."),
	JWT_SIGNATURE_INVALID(401, "E-J005", "서명이 올바르지 않은 토큰입니다."),

	// Member
	MEMBER_UNFOUNDED(400, "E-M001", "존재하지 않는 회원입니다."),
	PASSWORD_MISMATCHED(400, "E-M002", "비밀번호와 비밀번호 확인이 일치하지 않습니다."),
	USERNAME_UNAVAILABLE(400, "E-M003", "사용할 수 없는 아이디입니다."),
	EMAIL_UNAVAILABLE(400, "E-M004", "사용할 수 없는 이메일입니다."),
	ALREADY_SETUP_BIRTHDAY(400, "E-M005", "이미 생일을 입력한 회원입니다."),
	ALREADY_FRIEND(400, "E-M006", "이미 친구관계입니다."),
	FRIEND_MYSELF_IMPOSSIBLE(400, "E-M007", "자기 자신은 친구로 추가할 수 없습니다."),
	NOT_FRIENDSHIP(400, "E-M008", "친구 관계가 아닙니다."),

	// Room
	ROOM_UNFOUNDED(400, "E-R001", "존재하지 않는 파티룸입니다."),
	CANNOT_GET_MESSAGES_BEFORE_BIRTHDAY(400, "E-R002", "생일 전에는 메시지 목록을 조회할 수 없습니다."),
	NOT_USED_R003(400, "E-R003", ""),
	CANNOT_GET_MESSAGES_OTHER_ROOM(400, "E-R004", "다른 사람의 메시지 목록은 조회할 수 없습니다."),
	CANNOT_GET_UNREAD_MESSAGES_COUNT_OTHER_ROOM(400, "E-R005", "본인 파티룸에서만 읽지 않은 메시지 개수를 조회할 수 있습니다."),

	// Message
	CANNOT_CREATE_MESSAGE_MY_ROOM(400, "E-RM001", "본인 파티룸에는 축하 메시지를 생성할 수 없습니다."),
	CANNOT_CREATE_MESSAGE_MORE_THAN_ONCE(400, "E-RM002", "한 파티룸에 두 번 이상 축하 메시지를 생성할 수 없습니다."),
	MESSAGE_UNFOUNDED(400, "E-RM003", "존재하지 않는 메시지입니다."),
	THIRD_PARTY_CANNOT_INQUIRY_MESSAGE(400, "E-RM004", "제 3자는 메시지를 조회할 수 없습니다."),
	ROOM_OWNER_CANNOT_INQUIRY_MESSAGE_BEFORE_BIRTHDAY(400, "E-RM005", "파티룸 주인은 생일 전에 메시지를 조회할 수 없습니다."),
	CANNOT_CANCEL_DELETED_MESSAGE(400, "E-RM006", "이미 삭제된 메시지는 작성 취소가 불가능합니다."),
	CANNOT_CANCEL_WRITTEN_MESSAGE(400, "E-RM007", "이미 업로드된 메시지는 작성 취소가 불가능합니다."),
	CANNOT_UPLOAD_AFTER_BIRTHDAY(400, "E-RM008", "생일 이후로는 축하 메시지를 업로드할 수 없습니다."),
	CANNOT_DELETE_NOT_WRITTEN_MESSAGE(400, "E-RM009", "작성된 메시지만 삭제 가능합니다."),
	CANNOT_DELETE_MESSAGE_AFTER_BIRTHDAY(400, "E-RM010", "생일 이후로는 메시지를 삭제할 수 없습니다."),
	CANNOT_EDIT_AFTER_BIRTHDAY(400, "E-RM011", "생일 이후로는 메시지를 수정할 수 없습니다."),
	CANNOT_EDIT_NOT_WRITTEN_MESSAGE(400, "E-RM012", "작성된 메시지만 수정 가능합니다."),
	CANNOT_CREATE_MESSAGE_AFTER_BIRTHDAY(400, "E-RM013", "생일 이후로는 메시지를 생성할 수 없습니다."),
	ALREADY_READ_MESSAGE(400, "E-RM014", "이미 읽은 메시지입니다."),
	ALREADY_LIKE_MESSAGE(400, "E-RM015", "이미 좋아요한 메시지입니다."),
	CANNOT_LIKE_OTHER_ROOM_MESSAGE(400, "E-RM016", "본인의 파티룸에 작성된 메시지만 좋아요할 수 있습니다."),
	CANNOT_LIKE_NOT_WRITTEN_MESSAGE(400, "E-RM017", "작성된 메시지만 좋아요 가능합니다."),
	CANNOT_READ_NOT_WRITTEN_MESSAGE(400, "E-RM018", "작성된 메시지만 읽기 가능합니다."),

	// File
	FILE_UNFOUNDED(400, "E-F001", "존재하지 않는 파일입니다."),
	CANNOT_ATTACH_FILE_TO_DELETED_MESSAGE(400, "E-F002", "삭제된 메시지에 파일을 첨부할 수 없습니다."),
	CANNOT_ATTACH_FILE_TO_WRITTEN_MESSAGE(400, "E-F003", "업로드된 메시지에 파일을 첨부할 수 없습니다."),
	;

	private final int status;
	private final String code;
	private final String message;

}
