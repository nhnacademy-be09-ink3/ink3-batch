package com.nhnacademy.ink3batch.scheduler;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import com.nhnacademy.ink3batch.batch.scheduler.JdbcQueryService;
import com.nhnacademy.ink3batch.batch.scheduler.UserStatusScheduler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.LoggerFactory;

@ExtendWith(MockitoExtension.class)
class UserStatusSchedulerTest {

    @Mock
    private JdbcQueryService jdbcQueryService;

    @InjectMocks
    private UserStatusScheduler scheduler;

    private ListAppender<ILoggingEvent> listAppender;

    @BeforeEach
    void setUp() {
        // UserStatusScheduler 로거에 ListAppender 붙여서 로그 캡처
        Logger logger = (Logger) LoggerFactory.getLogger(UserStatusScheduler.class);
        listAppender = new ListAppender<>();
        listAppender.start();
        logger.addAppender(listAppender);
    }

    @Test
    @DisplayName("scheduleMarkDormantUsers — markDormantUsers(90) 호출 후 로그에 처리 건수를 남긴다")
    void scheduleMarkDormantUsers_invokesAndLogsCount() {
        // Arrange: markDormantUsers(90)이 5를 반환하도록 stub
        when(jdbcQueryService.markDormantUsers(90)).thenReturn(5);

        // Act
        scheduler.scheduleMarkDormantUsers();

        // Assert: 서비스 호출 검증
        verify(jdbcQueryService, times(1)).markDormantUsers(90);

        // Assert: 로그 메시지에 "휴면 처리된 사용자 수: 5 명" 이 포함되어 있는지 확인
        assertThat(listAppender.list)
                .extracting(ILoggingEvent::getFormattedMessage)
                .anyMatch(msg -> msg.contains("휴면 처리된 사용자 수: 5 명"));
    }
}
