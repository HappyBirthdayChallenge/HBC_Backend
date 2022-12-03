package inha.tnt.hbc.application.alarm.controller;

import static inha.tnt.hbc.model.ResultCode.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import inha.tnt.hbc.application.alarm.service.AlarmFacadeService;
import inha.tnt.hbc.model.ResultResponse;
import inha.tnt.hbc.model.alarm.AlarmApi;
import inha.tnt.hbc.model.alarm.dto.AlarmPageResponse;

@RestController
@RequiredArgsConstructor
public class AlarmController implements AlarmApi {

	private final AlarmFacadeService alarmFacadeService;

	@Override
	public ResponseEntity<ResultResponse> getAlarms(Integer page, Integer size) {
		final AlarmPageResponse response = alarmFacadeService.getAlarms(page, size);
		return ResponseEntity.ok(ResultResponse.of(GET_ALARMS_SUCCESS, response));
	}

}
