package com.nhnacademy.batch.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class CouponPolicy {
    @Id @GeneratedValue
    private Long id;

    private String name;
    private DiscountType discount_type;
    private int minimum_order_amount;
    private int discount_value;
    private int maximum_discount_amount;
}
