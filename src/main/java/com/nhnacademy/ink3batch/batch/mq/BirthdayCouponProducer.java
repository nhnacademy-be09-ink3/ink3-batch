package com.nhnacademy.ink3batch.batch.mq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.ink3batch.batch.message.BirthdayCouponMessage;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class BirthdayCouponProducer {

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    @PostConstruct
    public void init() {
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if (!ack) {
                log.error("메시지가 브로커에 도달하지 못했습니다. cause: {}", cause);
            }
        });

        rabbitTemplate.setReturnsCallback(returned -> {
            log.error("큐에 메시지 전달 실패: {}", returned.getMessage());
        });
    }

    public void send(BirthdayCouponMessage message) throws JsonProcessingException {
        try {
            // spring 기본으로 제공하는 역/직렬화 제거하고 header 다시 작성
            // ㄴ이거 때문에 consumer에서 역직렬화가 안됨
            byte[] body = objectMapper.writeValueAsBytes(message);
            MessageProperties props = new MessageProperties();
            props.setContentType("application/json");
            props.setHeader("type", "BirthdayCouponMessage");

            Message rabbitMessage = new Message(body, props);
            rabbitTemplate.send("coupon.exchange", "coupon.birthday", rabbitMessage);
            log.info("메시지 전송 성공: {}", message);
        } catch (AmqpException e) {
            log.error("RabbitMQ 전송 중 예외 발생", e);
            // 추가: DB에 실패 기록, 재시도 큐에 넣기, 알림 등
        }
    }
}

