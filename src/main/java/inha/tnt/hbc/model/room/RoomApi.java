package inha.tnt.hbc.model.room;

import javax.validation.constraints.Min;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import inha.tnt.hbc.model.ErrorResponse;
import inha.tnt.hbc.model.ResultResponse;
import inha.tnt.hbc.model.room.dto.UnreadMessagesCountResponse;
import inha.tnt.hbc.model.room.dto.RoomDecorationPageResponse;
import inha.tnt.hbc.model.room.dto.RoomDto;
import inha.tnt.hbc.model.room.dto.RoomMessagePageResponse;
import inha.tnt.hbc.model.room.dto.SearchRoomMessageWrittenByMeResponse;

@Api(tags = "파티룸 API")
@Validated
@RequestMapping("/rooms")
public interface RoomApi {

	@ApiOperation(value = "파티름 목록 조회")
	@ApiResponses({
		@ApiResponse(code = 1, response = RoomDto.class, message = ""
			+ "status: 200 | code: R-R001 | message: 파티룸 목록 조회에 성공하였습니다."),
		@ApiResponse(code = 500, response = ErrorResponse.class, message = ""
			+ "status: 400 | code: E-G002 | message: 입력 값이 유효하지 않습니다.\n"
			+ "status: 400 | code: E-M001 | message: 존재하지 않는 회원입니다.\n"
			+ "status: 401 | code: E-A003 | message: 인증에 실패하였습니다.\n"
			+ "status: 500 | code: E-G001 | message: 내부 서버 오류입니다.")
	})
	@ApiImplicitParam(name = "member_id", value = "회원 PK", required = true, example = "1")
	@GetMapping
	ResponseEntity<ResultResponse> getRooms(@RequestParam(name = "member_id") Long memberId);

	@ApiOperation(value = "파티룸 장식품 페이지 조회")
	@ApiResponses({
		@ApiResponse(code = 1, response = RoomDecorationPageResponse.class, message = ""
			+ "status: 200 | code: R-R002 | message: 파티룸 장식품 페이지 조회에 성공하였습니다."),
		@ApiResponse(code = 500, response = ErrorResponse.class, message = ""
			+ "status: 400 | code: E-G002 | message: 입력 값이 유효하지 않습니다.\n"
			+ "status: 400 | code: E-R001 | message: 존재하지 않는 파티룸입니다.\n"
			+ "status: 401 | code: E-A003 | message: 인증에 실패하였습니다.\n"
			+ "status: 500 | code: E-G001 | message: 내부 서버 오류입니다.")
	})
	@ApiImplicitParams({
		@ApiImplicitParam(name = "room_id", value = "파티룸 PK", required = true, example = "1"),
		@ApiImplicitParam(name = "page", value = "장식품 페이지(1 이상)", required = true, example = "1")
	})
	@GetMapping("/{room_id}/decorations")
	ResponseEntity<ResultResponse> getRoomDecorationPage(@PathVariable(name = "room_id") Long roomId,
		@RequestParam @Min(1) Integer page);

	@ApiOperation(value = "파티룸 받은 메시지 목록 페이지 조회", notes = ""
		+ "1. 생일 전에는 메시지 목록 조회가 불가능합니다.\n"
		+ "2. 다른 사람이 받은 메시지 목록은 조회할 수 없습니다.")
	@ApiResponses({
		@ApiResponse(code = 1, response = RoomMessagePageResponse.class, message = ""
			+ "status: 200 | code: R-R003 | message: 파티룸 받은 메시지 목록 페이지 조회에 성공하였습니다."),
		@ApiResponse(code = 500, response = ErrorResponse.class, message = ""
			+ "status: 400 | code: E-G002 | message: 입력 값이 유효하지 않습니다.\n"
			+ "status: 400 | code: E-R001 | message: 존재하지 않는 파티룸입니다.\n"
			+ "status: 400 | code: E-R002 | message: 생일 전에는 메시지 목록을 조회할 수 없습니다.\n"
			+ "status: 400 | code: E-R004 | message: 다른 사람의 메시지 목록은 조회할 수 없습니다.\n"
			+ "status: 401 | code: E-A003 | message: 인증에 실패하였습니다.\n"
			+ "status: 500 | code: E-G001 | message: 내부 서버 오류입니다.")
	})
	@ApiImplicitParams({
		@ApiImplicitParam(name = "room_id", value = "파티룸 PK", required = true, example = "1"),
		@ApiImplicitParam(name = "page", value = "메시지 페이지(1 이상)", required = true, example = "1"),
		@ApiImplicitParam(name = "size", value = "페이지당 개수(1 이상)", required = true, example = "10")
	})
	@GetMapping("/{room_id}/messages")
	ResponseEntity<ResultResponse> getRoomMessages(@PathVariable(name = "room_id") Long roomId,
		@RequestParam @Min(1) Integer page, @RequestParam @Min(1) Integer size);

	@ApiOperation(value = "파티룸 내가 작성한 메시지 찾기")
	@ApiResponses({
		@ApiResponse(code = 1, response = SearchRoomMessageWrittenByMeResponse.class, message = ""
			+ "status: 200 | code: R-R004 | message: 파티룸 내가 작성한 메시지 찾기에 성공하였습니다."),
		@ApiResponse(code = 500, response = ErrorResponse.class, message = ""
			+ "status: 400 | code: E-G002 | message: 입력 값이 유효하지 않습니다.\n"
			+ "status: 400 | code: E-R001 | message: 존재하지 않는 파티룸입니다.\n"
			+ "status: 400 | code: E-R003 | message: 해당 파티룸에 메시지를 작성하지 않았습니다.\n"
			+ "status: 401 | code: E-A003 | message: 인증에 실패하였습니다.\n"
			+ "status: 500 | code: E-G001 | message: 내부 서버 오류입니다.")
	})
	@ApiImplicitParam(name = "room_id", value = "파티룸 PK", required = true, example = "1")
	@GetMapping("/{room_id}/messages/mine")
	ResponseEntity<ResultResponse> searchRoomMessageWrittenByMe(@PathVariable(name = "room_id") Long roomId);

	@ApiOperation(value = "파티룸 읽지 않은 메시지 개수 조회")
	@ApiResponses({
		@ApiResponse(code = 1, response = UnreadMessagesCountResponse.class, message = ""
			+ "status: 200 | code: R-R005 | message: 파티룸 읽지 않은 메시지 개수 조회에 성공하였습니다."),
		@ApiResponse(code = 500, response = ErrorResponse.class, message = ""
			+ "status: 400 | code: E-G002 | message: 입력 값이 유효하지 않습니다.\n"
			+ "status: 400 | code: E-R001 | message: 존재하지 않는 파티룸입니다.\n"
			+ "status: 400 | code: E-R005 | message: 본인 파티룸에서만 읽지 않은 메시지 개수를 조회할 수 있습니다.\n"
			+ "status: 401 | code: E-A003 | message: 인증에 실패하였습니다.\n"
			+ "status: 500 | code: E-G001 | message: 내부 서버 오류입니다.")
	})
	@GetMapping("/{room_id}/unread")
	ResponseEntity<ResultResponse> getUnReadMessagesCount(@PathVariable(name = "room_id") Long roomId);

}
