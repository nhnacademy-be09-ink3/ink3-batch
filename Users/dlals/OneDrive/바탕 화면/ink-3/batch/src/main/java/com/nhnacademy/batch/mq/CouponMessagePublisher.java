package com.nhnacademy.batch.mq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.batch.message.BirthdayBulkMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CouponMessagePublisher {
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    public void send(BirthdayBulkMessage message){
        try{
            String json = objectMapper.writeValueAsString(message);
            rabbitTemplate.convertAndSend("coupon.exchange", "coupon.birthday.bulk" , json);
        }catch (JsonProcessingException e){
            log.error("Failed to publish birthday message", e);
        }
    }
}
