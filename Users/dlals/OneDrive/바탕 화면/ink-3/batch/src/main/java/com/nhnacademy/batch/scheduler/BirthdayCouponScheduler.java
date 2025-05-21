package com.nhnacademy.batch.scheduler;

import com.nhnacademy.batch.client.UserClient;
import com.nhnacademy.batch.message.BirthdayBulkMessage;
import com.nhnacademy.batch.mq.CouponMessagePublisher;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class BirthdayCouponScheduler {

    private final UserClient userClient;
    private final CouponMessagePublisher publisher;

    @Scheduled(cron = "0 0 0 * * *")
    public void issueBirthdayCoupons() {
        List<Long> userIds = userClient.getTodayBirthdayUserIds();

        if (!userIds.isEmpty()) {
            BirthdayBulkMessage message = new BirthdayBulkMessage(5678L, LocalDateTime.now(), "BIRTHDAY", userIds);
            publisher.send(message);
            log.info("Published birthday coupon message for {} users", userIds.size());
        } else {
            log.info("No birthday users today");
        }
    }
}
