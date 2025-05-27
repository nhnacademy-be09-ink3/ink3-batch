package com.nhnacademy.ink3batch.batch.message;

import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BirthdayCouponMessage {
    private List<Long> userIds;         // 생일자 ID 목록
    private Long couponId;              // 발급할 쿠폰 ID
    private LocalDate issuedDate;       // 발급 일자 (ex: LocalDate.now())
}
