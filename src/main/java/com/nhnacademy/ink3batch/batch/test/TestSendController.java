package com.nhnacademy.ink3batch.batch.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.ink3batch.batch.scheduler.BirthdayCouponScheduler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/batch")
public class TestSendController {
    private final BirthdayCouponScheduler birthdayCouponScheduler;

    @PostMapping("/issue-birthday-coupons")
    public ResponseEntity<Void> triggerBirthdayCouponIssue() throws JsonProcessingException {

        birthdayCouponScheduler.execute();
        return ResponseEntity.ok().build();
    }
}
