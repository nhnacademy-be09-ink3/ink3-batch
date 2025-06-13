package com.nhnacademy.ink3batch.scheduler;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import com.nhnacademy.ink3batch.batch.scheduler.JdbcQueryService;
import com.nhnacademy.ink3batch.batch.scheduler.UserMembershipScheduler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.LoggerFactory;

@ExtendWith(MockitoExtension.class)
class MembershipSchedulerTest {

    @Mock
    private JdbcQueryService jdbcQueryService;

    @InjectMocks
    private UserMembershipScheduler scheduler;

    private ListAppender<ILoggingEvent> listAppender;

    @BeforeEach
    void setUp() {
        // UserMembershipScheduler 의 로거에 ListAppender 붙여서 로그 캡처
        Logger logger = (Logger) LoggerFactory.getLogger(UserMembershipScheduler.class);
        listAppender = new ListAppender<>();
        listAppender.start();
        logger.addAppender(listAppender);
    }

    @Test
    @DisplayName("scheduleMembership — updateMemberRate() 호출 후 로그에 업데이트 건수를 남긴다")
    void scheduleMembership_invokesUpdateAndLogsCount() {
        // Arrange: updateMemberRate() 가 7 을 반환하도록 stub
        when(jdbcQueryService.updateMemberRate()).thenReturn(7);

        // Act
        scheduler.scheduleMembership();

        // Assert: 서비스 호출 검증
        verify(jdbcQueryService, times(1)).updateMemberRate();

        // Assert: 로그 메시지에 "Update count: 7" 이 포함되어 있는지 확인
        assertThat(listAppender.list)
                .extracting(ILoggingEvent::getFormattedMessage)
                .anyMatch(msg -> msg.contains("Update count: 7"));
    }
}
