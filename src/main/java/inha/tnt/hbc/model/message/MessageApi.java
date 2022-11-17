package inha.tnt.hbc.model.message;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import inha.tnt.hbc.model.ErrorResponse;
import inha.tnt.hbc.model.ResultResponse;
import inha.tnt.hbc.model.message.dto.CreateMessageResponse;
import inha.tnt.hbc.model.message.dto.MessageRequest;
import inha.tnt.hbc.model.room.dto.RoomDto;

@Api(tags = "메시지 API")
@RequestMapping("/messages")
public interface MessageApi {

	@ApiOperation(value = "메시지 업로드")
	@ApiResponses({
		@ApiResponse(code = 1, response = RoomDto.class, message = ""
			+ "status: 200 | code: R-RM001 | message: 메시지 작성에 성공하였습니다."),
		@ApiResponse(code = 500, response = ErrorResponse.class, message = ""
			+ "status: 400 | code: E-G002 | message: 입력 값이 유효하지 않습니다.\n"
			+ "status: 401 | code: E-A003 | message: 인증에 실패하였습니다.\n"
			+ "status: 500 | code: E-G001 | message: 내부 서버 오류입니다.")
	})
	@PostMapping("/upload")
	ResponseEntity<ResultResponse> upload(@Valid @RequestBody MessageRequest request);

	@ApiOperation(value = "메시지 생성")
	@ApiResponses({
		@ApiResponse(code = 1, response = CreateMessageResponse.class, message = ""
			+ "status: 200 | code: R-RM002 | message: 메시지 생성에 성공하였습니다."),
		@ApiResponse(code = 500, response = ErrorResponse.class, message = ""
			+ "status: 400 | code: E-G002 | message: 입력 값이 유효하지 않습니다.\n"
			+ "status: 400 | code: E-RM001 | message: 본인 파티룸에는 축하 메시지를 생성할 수 없습니다.\n"
			+ "status: 400 | code: E-RM002 | message: 한 파티룸에 두 번 이상 축하 메시지를 생성할 수 없습니다.\n"
			+ "status: 401 | code: E-A003 | message: 인증에 실패하였습니다.\n"
			+ "status: 500 | code: E-G001 | message: 내부 서버 오류입니다.")
	})
	@ApiImplicitParam(name = "room_id", value = "파티룸 PK", required = true, example = "1")
	@PostMapping("/create")
	ResponseEntity<ResultResponse> create(@RequestParam(name = "room_id") Long roomId);

}
