package com.nhnacademy.ink3batch.batch.scheduler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.ink3batch.batch.message.BirthdayBulkMessage;
import com.nhnacademy.ink3batch.batch.message.BirthdayCouponMessage;
import com.nhnacademy.ink3batch.batch.mq.produce.BirthdayCouponProducer;
import com.nhnacademy.ink3batch.batch.service.JdbcQueryService;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class BirthdayCouponScheduler {
    private final BirthdayCouponProducer birthdayCouponProducer;
    private final JobLauncher jobLauncher;
    private final JdbcQueryService jdbcQueryService;


    // userId를 100개 단위로 끊어서 전송
    //@Scheduled(cron = "0 0 0 L * ?")// 매일 자정 실행
    @Scheduled(fixedRate = 1000)
    @Transactional
    public void sendBirthdayCouponsInChunks() {

        int month = LocalDate.now().plusMonths(1).getMonthValue();
        List<Long> userIds = jdbcQueryService.printUsersBornIn(month);
        System.out.println(userIds);
        int chunkSize = 100;

        for (int i = 0; i < userIds.size(); i += chunkSize) {
            List<Long> chunk = userIds.subList(i, Math.min(i + chunkSize, userIds.size()));

            BirthdayBulkMessage message = new BirthdayBulkMessage(chunk);

            try {
                birthdayCouponProducer.send(message);
            } catch (JsonProcessingException e) {
                log.error("메시지 직렬화 실패 - userId chunk 시작 인덱스 : {}", i, e);
            }
        }
    }

}
