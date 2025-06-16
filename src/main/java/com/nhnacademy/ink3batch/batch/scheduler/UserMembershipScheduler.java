package com.nhnacademy.ink3batch.batch.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserMembershipScheduler {
    private final JdbcQueryService jdbcQueryService;

    @Scheduled(cron = "0 0 0 L * ?")
    // @Scheduled(fixedRate = 5000)
    public void scheduleMembership(){
        int updateCount = jdbcQueryService.updateMemberRate();
        log.info("Update count: {}", updateCount);
    }
}
