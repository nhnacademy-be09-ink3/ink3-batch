package com.nhnacademy.ink3batch.batch.message;

import java.util.List;

public record BirthdayCouponMessage(List<Long> userIds) {
}
