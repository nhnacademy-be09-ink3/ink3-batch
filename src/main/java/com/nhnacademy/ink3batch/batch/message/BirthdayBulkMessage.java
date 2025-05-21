package com.nhnacademy.ink3batch.batch.message;

import java.time.LocalDateTime;
import java.util.List;

public record BirthdayBulkMessage(Long couponId, LocalDateTime issuedDate, String trigger, List<Long> userIds) {
}
