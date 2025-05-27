package com.nhnacademy.ink3batch.batch.scheduler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.ink3batch.batch.message.BirthdayCouponMessage;
import com.nhnacademy.ink3batch.batch.mq.produce.BirthdayCouponProducer;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class BirthdayCouponScheduler {
//    private final BirthdayCouponProducer birthdayCouponProducer;
//    private final JobLauncher jobLauncher;
//
//    // Test 버전
//    public void execute() throws JsonProcessingException {
//        // 생일자 조회
//        List<Long> birthdayUserIds = List.of(1L, 2L, 3L);
//        Long couponId = 1L;
//        LocalDate today = LocalDate.now();
//
//        BirthdayCouponMessage message = new BirthdayCouponMessage(birthdayUserIds, couponId, today);
//        birthdayCouponProducer.send(message);
//    }
//
//
//    // userId를 100개 단위로 끊어서 전송
//    @Scheduled(cron = "0 0 0 * * ?") // 매일 자정 실행
//    @Transactional
//    public void sendBirthdayCouponsInChunks() {
//        Long birthdayCouponId = 123L; // 또는 Config나 DB에서 가져오기
//        LocalDate today = LocalDate.now();
//
//        // 예: 오늘 생일인 사용자 조회
//        // DB에서 가져와야함
//        // List<Long> userIds = userRepository.findUserIdsByBirthday(today);
//
//        int chunkSize = 100;
//
////        for (int i = 0; i < userIds.size(); i += chunkSize) {
////            List<Long> chunk = userIds.subList(i, Math.min(i + chunkSize, userIds.size()));
////
////            BirthdayCouponMessage message = new BirthdayCouponMessage(
////                    chunk,
////                    birthdayCouponId,
////                    today
////            );
////
////            try {
////                birthdayCouponProducer.send(message);
////            } catch (JsonProcessingException e) {
////                log.error("메시지 직렬화 실패 - userId chunk 시작 인덱스 : {}", i, e);
////            }
////        }
//    }

}
