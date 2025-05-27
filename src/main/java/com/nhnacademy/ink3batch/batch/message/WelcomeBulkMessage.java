package com.nhnacademy.ink3batch.batch.message;

import java.time.LocalDateTime;
import java.util.List;

public record WelcomeBulkMessage(Long couponId, List<Long> userIds, LocalDateTime issuedDate){
}
