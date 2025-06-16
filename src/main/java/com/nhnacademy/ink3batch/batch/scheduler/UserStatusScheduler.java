package com.nhnacademy.ink3batch.batch.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@RequiredArgsConstructor
public class UserStatusScheduler {
    private final JdbcQueryService jdbcQueryService;

    @Scheduled(cron = "0 0 0 L * ?")
    //@Scheduled(fixedRate = 5000)
    public void scheduleMarkDormantUsers() {
        int updatedCount = jdbcQueryService.markDormantUsers(90);
        log.info("휴면 처리된 사용자 수: {} 명", updatedCount);
    }
}
