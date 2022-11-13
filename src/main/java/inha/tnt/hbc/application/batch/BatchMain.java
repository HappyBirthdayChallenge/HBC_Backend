package inha.tnt.hbc.application.batch;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class BatchMain {

	private final BatchService batchService;

	// TODO: Scale out -> SchedulerLock 적용 필수 + DB 테이블 생성
	@Scheduled(cron = "0 0 2 * * *")
	public void createRooms() {
		log.info("BATCH createRooms START");
		batchService.createRooms();
		log.info("BATCH createRooms END");
	}

}
