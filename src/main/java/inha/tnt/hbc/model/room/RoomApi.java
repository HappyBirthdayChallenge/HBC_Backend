package inha.tnt.hbc.model.room;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import inha.tnt.hbc.model.ErrorResponse;
import inha.tnt.hbc.model.ResultResponse;
import inha.tnt.hbc.model.room.dto.RoomDto;

@Api(tags = "파티룸 API")
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

}
