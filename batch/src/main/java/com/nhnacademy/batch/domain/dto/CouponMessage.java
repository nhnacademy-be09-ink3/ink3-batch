package com.nhnacademy.batch.domain.dto;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class CouponMessage implements Serializable {
    private Long userId;
    private String couponCode;

}

