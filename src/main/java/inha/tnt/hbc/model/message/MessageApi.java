package inha.tnt.hbc.model.message;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
import inha.tnt.hbc.model.Void;
import inha.tnt.hbc.model.message.dto.CreateMessageResponse;
import inha.tnt.hbc.model.message.dto.InquiryMessageResponse;
import inha.tnt.hbc.model.message.dto.MessageRequest;
import inha.tnt.hbc.model.message.dto.MessageWrittenByMePageResponse;

@Api(tags = "메시지 API")
@RequestMapping("/messages")
public interface MessageApi {

	@ApiOperation(value = "메시지 업로드", notes = ""
		+ "1. 먼저 메시지 생성 API를 호출하여 메시지 PK를 얻어야 합니다.\n"
		+ "2. 파일 업로드를 한 경우, 파일 id를 함께 요청해 주세요.\n"
		+ "3. 생일 이후로는 축하 메시지를 업로드할 수 없습니다.")
	@ApiResponses({
		@ApiResponse(code = 1, response = Void.class, message = ""
			+ "status: 200 | code: R-RM001 | message: 메시지 작성에 성공하였습니다."),
		@ApiResponse(code = 500, response = ErrorResponse.class, message = ""
			+ "status: 400 | code: E-G002 | message: 입력 값이 유효하지 않습니다.\n"
			+ "status: 400 | code: E-RM003 | message: 존재하지 않는 메시지입니다.\n"
			+ "status: 400 | code: E-RM008 | message: 생일 이후로는 축하 메시지를 업로드할 수 없습니다.\n"
			+ "status: 401 | code: E-A003 | message: 인증에 실패하였습니다.\n"
			+ "status: 500 | code: E-G001 | message: 내부 서버 오류입니다.")
	})
	@PostMapping("/upload")
	ResponseEntity<ResultResponse> upload(@Valid @RequestBody MessageRequest request);

	@ApiOperation(value = "메시지 생성", notes = ""
		+ "1. 본인 파티룸에는 축하 메시지를 생성할 수 없습니다.\n"
		+ "2. 한 파티룸에 두 번 이상 축하 메시지를 작성할 수 없습니다.\n"
		+ "3. 생일 이후로는 메시지를 생성할 수 없습니다.")
	@ApiResponses({
		@ApiResponse(code = 1, response = CreateMessageResponse.class, message = ""
			+ "status: 200 | code: R-RM002 | message: 메시지 생성에 성공하였습니다."),
		@ApiResponse(code = 500, response = ErrorResponse.class, message = ""
			+ "status: 400 | code: E-G002 | message: 입력 값이 유효하지 않습니다.\n"
			+ "status: 400 | code: E-R001 | message: 존재하지 않는 파티룸입니다.\n"
			+ "status: 400 | code: E-RM001 | message: 본인 파티룸에는 축하 메시지를 생성할 수 없습니다.\n"
			+ "status: 400 | code: E-RM002 | message: 한 파티룸에 두 번 이상 축하 메시지를 작성할 수 없습니다.\n"
			+ "status: 400 | code: E-RM013 | message: 생일 이후로는 메시지를 생성할 수 없습니다.\n"
			+ "status: 401 | code: E-A003 | message: 인증에 실패하였습니다.\n"
			+ "status: 500 | code: E-G001 | message: 내부 서버 오류입니다.")
	})
	@ApiImplicitParam(name = "room_id", value = "파티룸 PK", required = true, example = "1")
	@PostMapping("/create")
	ResponseEntity<ResultResponse> create(@RequestParam(name = "room_id") Long roomId);

	@ApiOperation(value = "메시지 조회", notes = ""
		+ "1. 제 3자는 메시지를 조회할 수 없습니다.\n"
		+ "2. 파티룸 주인은 생일 전에 메시지를 조회할 수 없습니다.")
	@ApiResponses({
		@ApiResponse(code = 1, response = InquiryMessageResponse.class, message = ""
			+ "status: 200 | code: R-RM003 | message: 메시지 조회에 성공하였습니다."),
		@ApiResponse(code = 500, response = ErrorResponse.class, message = ""
			+ "status: 400 | code: E-G002 | message: 입력 값이 유효하지 않습니다.\n"
			+ "status: 400 | code: E-RM004 | message: 제 3자는 메시지를 조회할 수 없습니다.\n"
			+ "status: 400 | code: E-RM005 | message: 파티룸 주인은 생일 전에 메시지를 조회할 수 없습니다.\n"
			+ "status: 400 | code: E-RM003 | message: 존재하지 않는 메시지입니다.\n"
			+ "status: 401 | code: E-A003 | message: 인증에 실패하였습니다.\n"
			+ "status: 500 | code: E-G001 | message: 내부 서버 오류입니다.")
	})
	@ApiImplicitParam(name = "message_id", value = "메시지 PK", required = true, example = "1")
	@GetMapping("/{message_id}")
	ResponseEntity<ResultResponse> inquiry(@PathVariable(name = "message_id") Long messageId);

	@ApiOperation(value = "메시지 작성 취소", notes = ""
		+ "1. 이미 삭제된 메시지는 작성 취소할 수 없습니다.\n"
		+ "2. 이미 업르도된 메시지는 작성 취소할 수 없습니다.")
	@ApiResponses({
		@ApiResponse(code = 1, response = Void.class, message = ""
			+ "status: 200 | code: R-RM004 | message: 메시지 작성 취소에 성공하였습니다."),
		@ApiResponse(code = 500, response = ErrorResponse.class, message = ""
			+ "status: 400 | code: E-G002 | message: 입력 값이 유효하지 않습니다.\n"
			+ "status: 400 | code: E-RM006 | message: 이미 삭제된 메시지는 작성 취소가 불가능합니다.\n"
			+ "status: 400 | code: E-RM007 | message: 이미 업로드된 메시지는 작성 취소가 불가능합니다.\n"
			+ "status: 400 | code: E-RM003 | message: 존재하지 않는 메시지입니다.\n"
			+ "status: 401 | code: E-A003 | message: 인증에 실패하였습니다.\n"
			+ "status: 500 | code: E-G001 | message: 내부 서버 오류입니다.")
	})
	@ApiImplicitParam(name = "message_id", value = "메시지 PK", required = true, example = "1")
	@DeleteMapping("/cancel/{message_id}")
	ResponseEntity<ResultResponse> cancel(@PathVariable(name = "message_id") Long messageId);

	@ApiOperation(value = "메시지 삭제", notes = ""
		+ "1. 생일 이전에만 삭제할 수 있습니다.\n"
		+ "2. 작성된 메시지만 삭제할 수 있습니다.")
	@ApiResponses({
		@ApiResponse(code = 1, response = Void.class, message = ""
			+ "status: 200 | code: R-RM005 | message: 메시지 삭제에 성공하였습니다."),
		@ApiResponse(code = 500, response = ErrorResponse.class, message = ""
			+ "status: 400 | code: E-G002 | message: 입력 값이 유효하지 않습니다.\n"
			+ "status: 400 | code: E-RM009 | message: 작성된 메시지만 삭제 가능합니다.\n"
			+ "status: 400 | code: E-RM010 | message: 생일 이후로는 메시지를 삭제할 수 없습니다.\n"
			+ "status: 400 | code: E-RM003 | message: 존재하지 않는 메시지입니다.\n"
			+ "status: 401 | code: E-A003 | message: 인증에 실패하였습니다.\n"
			+ "status: 500 | code: E-G001 | message: 내부 서버 오류입니다.")
	})
	@ApiImplicitParam(name = "message_id", value = "메시지 PK", required = true, example = "1")
	@DeleteMapping("/delete/{message_id}")
	ResponseEntity<ResultResponse> delete(@PathVariable(name = "message_id") Long messageId);

	@ApiOperation(value = "메시지 수정", notes = ""
		+ "1. 생일 이전에만 수정할 수 있습니다.\n"
		+ "2. 작성된 메시지만 수정할 수 있습니다.")
	@ApiResponses({
		@ApiResponse(code = 1, response = Void.class, message = ""
			+ "status: 200 | code: R-RM006 | message: 메시지 수정에 성공하였습니다."),
		@ApiResponse(code = 500, response = ErrorResponse.class, message = ""
			+ "status: 400 | code: E-G002 | message: 입력 값이 유효하지 않습니다.\n"
			+ "status: 400 | code: E-RM009 | message: 작성된 메시지만 수정 가능합니다.\n"
			+ "status: 400 | code: E-RM011 | message: 생일 이후로는 메시지를 수정할 수 없습니다.\n"
			+ "status: 400 | code: E-RM003 | message: 존재하지 않는 메시지입니다.\n"
			+ "status: 401 | code: E-A003 | message: 인증에 실패하였습니다.\n"
			+ "status: 500 | code: E-G001 | message: 내부 서버 오류입니다.")
	})
	@PutMapping("/edit")
	ResponseEntity<ResultResponse> edit(@Valid @RequestBody MessageRequest request);

	@ApiOperation(value = "내가 작성한 메시지 목록 페이지 조회")
	@ApiResponses({
		@ApiResponse(code = 1, response = MessageWrittenByMePageResponse.class, message = ""
			+ "status: 200 | code: R-RM007 | message: 내가 작성한 메시지 목록 페이지 조회에 성공하였습니다."),
		@ApiResponse(code = 500, response = ErrorResponse.class, message = ""
			+ "status: 400 | code: E-G002 | message: 입력 값이 유효하지 않습니다.\n"
			+ "status: 401 | code: E-A003 | message: 인증에 실패하였습니다.\n"
			+ "status: 500 | code: E-G001 | message: 내부 서버 오류입니다.")
	})
	@ApiImplicitParams({
		@ApiImplicitParam(name = "page", value = "메시지 페이지(1 이상)", required = true, example = "1"),
		@ApiImplicitParam(name = "size", value = "페이지당 개수(1 이상)", required = true, example = "10")
	})
	@GetMapping
	ResponseEntity<ResultResponse> getMessagesWrittenByMe(@RequestParam @Min(1) Integer page,
		@RequestParam @Min(1) Integer size);

}
