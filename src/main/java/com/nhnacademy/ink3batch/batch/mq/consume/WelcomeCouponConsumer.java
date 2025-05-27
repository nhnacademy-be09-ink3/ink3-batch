package com.nhnacademy.ink3batch.batch.mq.consume;

import com.nhnacademy.ink3batch.batch.message.WelcomeBulkMessage;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class WelcomeCouponConsumer {
    @RabbitListener(queues = "coupon.welcome")
    public void receiveMessage(WelcomeBulkMessage message) {
        System.out.println("💬 받은 메시지: " + message);
    }
}
