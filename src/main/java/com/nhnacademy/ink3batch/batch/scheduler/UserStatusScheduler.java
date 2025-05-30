//package com.nhnacademy.ink3batch.batch.scheduler;
//
//import com.nhnacademy.ink3batch.batch.service.JdbcQueryService;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.batch.core.launch.JobLauncher;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//@Slf4j
//@Component
//@RequiredArgsConstructor
//public class UserStatusScheduler {
//    private final JobLauncher jobLauncher;
//    private final JdbcQueryService jdbcQueryService;
//
//    @Scheduled(fixedRate = 5000)
//    @Transactional
//    public void userStatusUpdateScheduler() {
//        jdbcQueryService.userStatusUpdate();
//    }
//
//}
