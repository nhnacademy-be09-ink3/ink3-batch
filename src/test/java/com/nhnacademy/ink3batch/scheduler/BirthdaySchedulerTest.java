package com.nhnacademy.ink3batch.scheduler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.ink3batch.batch.message.BirthdayCouponMessage;
import com.nhnacademy.ink3batch.batch.mq.BirthdayCouponProducer;
import com.nhnacademy.ink3batch.batch.scheduler.BirthdayCouponScheduler;
import com.nhnacademy.ink3batch.batch.scheduler.JdbcQueryService;
import java.util.stream.LongStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BirthdaySchedulerTest {

    @Mock
    private BirthdayCouponProducer birthdayCouponProducer;

    @Mock
    private JdbcQueryService jdbcQueryService;

    @InjectMocks
    private BirthdayCouponScheduler scheduler;

    @BeforeEach
    void setUp() {
        // no-op: @InjectMocks handles injection
    }

    @Test
    @DisplayName("sendBirthdayCouponsInChunks — 전체 유저를 100개씩 잘라서 전송한다")
    void sendBirthdayCouponsInChunks_sendsInBatches() throws JsonProcessingException {
        // Arrange: 250명의 유저 ID를 반환하도록 stub
        List<Long> userIds = LongStream.rangeClosed(1, 250)
                .boxed()
                .collect(Collectors.toList());
        when(jdbcQueryService.printUsersBornIn(anyInt()))
                .thenReturn(userIds);

        // Act
        scheduler.sendBirthdayCouponsInChunks();

        // Assert: 3번 호출 (100, 100, 50)
        ArgumentCaptor<BirthdayCouponMessage> captor = ArgumentCaptor.forClass(BirthdayCouponMessage.class);
        verify(birthdayCouponProducer, times(3)).send(captor.capture());

        List<BirthdayCouponMessage> messages = captor.getAllValues();
        assertEquals(3, messages.size());

        // 첫 번째 청크: 1~100
        assertEquals(100, messages.get(0).userIds().size());
        assertEquals(List.of(1L, 2L, 3L), messages.get(0).userIds().subList(0, 3));
        // 두 번째 청크: 101~200
        assertEquals(100, messages.get(1).userIds().size());
        assertEquals(List.of(101L, 102L, 103L), messages.get(1).userIds().subList(0, 3));
        // 세 번째 청크: 201~250
        assertEquals(50, messages.get(2).userIds().size());
        assertEquals(List.of(201L, 202L, 203L), messages.get(2).userIds().subList(0, 3));
    }

    @Test
    @DisplayName("sendBirthdayCouponsInChunks — 중간에 직렬화 오류 발생해도 다음 청크를 계속 전송한다")
    void sendBirthdayCouponsInChunks_continuesAfterSerializationError() throws JsonProcessingException {
        // Arrange: 150명의 유저 ID
        List<Long> userIds = LongStream.rangeClosed(1, 150)
                .boxed()
                .collect(Collectors.toList());
        when(jdbcQueryService.printUsersBornIn(anyInt()))
                .thenReturn(userIds);

        // 첫 번째 호출은 정상, 두 번째 호출에서 예외, 세 번째는 다시 정상 (총 2청크)
        doNothing().when(birthdayCouponProducer).send(argThat(msg -> msg.userIds().size() == 100));
        doThrow(new JsonProcessingException("fail") {}).when(birthdayCouponProducer)
                .send(argThat(msg -> msg.userIds().size() == 50));

        // Act
        scheduler.sendBirthdayCouponsInChunks();

        // Assert: 두 번 호출되었지만, 예외에도 불구하고 두 번째 호출도 시도됨
        verify(birthdayCouponProducer, times(2)).send(any(BirthdayCouponMessage.class));
    }
}
