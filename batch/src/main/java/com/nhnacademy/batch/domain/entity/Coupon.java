package com.nhnacademy.batch.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Coupon {
    @Id
    @GeneratedValue
    private Long id;

    private Long coupon_policy_id;
    private String coupon_name;
    private TriggerType trigger_type;
}
