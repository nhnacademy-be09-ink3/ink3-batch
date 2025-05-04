package com.nhnacademy.batch.controller;

import com.nhnacademy.batch.message.CouponProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CouponController {

    private final CouponProducer couponProducer;

    @PostMapping("/send-coupon")
    public ResponseEntity<String> sendCoupon(@RequestParam Long userId) {
        couponProducer.sendCoupon(userId, "WELCOME2025");
        return ResponseEntity.ok("쿠폰 발송 요청 완료");
    }
}

