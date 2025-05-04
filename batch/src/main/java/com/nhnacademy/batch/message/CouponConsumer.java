package com.nhnacademy.batch.message;

import com.nhnacademy.batch.config.RabbitConfig;
import com.nhnacademy.batch.domain.dto.CouponMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CouponConsumer {

    @RabbitListener(queues = RabbitConfig.QUEUE_NAME)
    public void handleCouponMessage(CouponMessage message) {
        log.info("쿠폰 발송: userId={}, couponCode={}", message.getUserId(), message.getCouponCode());

        // 실제 쿠폰 발급 로직
        // 예: couponService.issueCouponToUser(message.getUserId(), message.getCouponCode());
    }
}

