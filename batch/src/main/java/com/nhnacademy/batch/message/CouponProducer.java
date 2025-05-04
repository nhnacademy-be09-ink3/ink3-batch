package com.nhnacademy.batch.message;

import com.nhnacademy.batch.config.RabbitConfig;
import com.nhnacademy.batch.domain.dto.CouponMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CouponProducer {

    private final RabbitTemplate rabbitTemplate;

    public void sendCoupon(Long userId, String couponCode) {
        CouponMessage message = new CouponMessage(userId, couponCode);
        rabbitTemplate.convertAndSend(
                RabbitConfig.EXCHANGE_NAME,
                RabbitConfig.ROUTING_KEY,
                message
        );
    }
}

