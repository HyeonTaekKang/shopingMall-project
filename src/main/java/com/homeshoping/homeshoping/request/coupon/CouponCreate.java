package com.homeshoping.homeshoping.request.coupon;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CouponCreate {

    private Long code; // 쿠폰코드

    private String name; // 쿠폰명;

    private String description; // 쿠폰 설명

    private int discountPrice; // 할인가격

    private LocalDateTime createdAt; // 생성일

    private LocalDateTime expiredAt; // 만료일

    @Builder
    public CouponCreate(String name, String description, int discountPrice, LocalDateTime createdAt, LocalDateTime expiredAt) {
        this.code = makeCouponCodeWithTodayDate();
        this.name = name;
        this.description = description;
        this.discountPrice = discountPrice;
        this.createdAt = createdAt;
        this.expiredAt = expiredAt;
    }

    private Long makeCouponCodeWithTodayDate(){
        LocalDateTime date = LocalDateTime.now();
        String dateToStr = String.format("%1$tY%1$tm%1$td", date);
        return Long.parseLong(dateToStr);
    }
}
