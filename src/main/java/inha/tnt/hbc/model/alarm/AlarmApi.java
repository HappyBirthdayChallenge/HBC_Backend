package inha.tnt.hbc.model.alarm;

import javax.validation.constraints.Min;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
import inha.tnt.hbc.model.alarm.dto.AlarmPageResponse;

@Api(tags = "알림 API")
@RequestMapping("/alarms")
public interface AlarmApi {

	@ApiOperation(value = "알림 목록 페이지 조회")
	@ApiResponses({
		@ApiResponse(code = 1, response = AlarmPageResponse.class, message = ""
			+ "status: 200 | code: R-AL001 | message: 알람 목록 조회에 성공하였습니다."),
		@ApiResponse(code = 500, response = ErrorResponse.class, message = ""
			+ "status: 401 | code: E-A003 | message: 인증에 실패하였습니다.\n"
			+ "status: 500 | code: E-G001 | message: 내부 서버 오류입니다.")
	})
	@ApiImplicitParams({
		@ApiImplicitParam(name = "page", value = "알림 페이지(1 이상)", required = true, example = "1"),
		@ApiImplicitParam(name = "size", value = "페이지당 개수(1 이상)", required = true, example = "10")
	})
	@GetMapping
	ResponseEntity<ResultResponse> getAlarms(@RequestParam @Min(1) Integer page,
		@RequestParam @Min(1) Integer size);

}
