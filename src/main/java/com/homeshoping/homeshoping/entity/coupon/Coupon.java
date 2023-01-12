package com.homeshoping.homeshoping.entity.coupon;

import com.homeshoping.homeshoping.request.coupon.CouponCreate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coupon_id")
    private Long id;

    private Long code; // 쿠폰코드 ( 이걸로 쿠폰을 구분할꺼임 )

    private String name; // 쿠폰명;

    private String description; // 쿠폰 설명

    private int discountPrice; // 할인가격

    private LocalDateTime createdAt; // 생성일

    private LocalDateTime expiredAt; // 만료일

    // coupon 생성메서드
    public static Coupon createCoupon(CouponCreate couponCreate){
        Coupon coupon = new Coupon();

        coupon.code = couponCreate.getCode();
        coupon.name = couponCreate.getName();
        coupon.description = couponCreate.getDescription();
        coupon.discountPrice = couponCreate.getDiscountPrice();
        coupon.createdAt = couponCreate.getCreatedAt();
        coupon.expiredAt = couponCreate.getExpiredAt();

        return coupon;
    }

    // === 비지니스 메서드 ===
    // 쿠폰에 의해 할인된 가격을 리턴하는 함수
                                              // 주문한 아이템의 가격  // 쿠폰의 할인 가격
    public int returnDiscountedPriceByCoupon(int orderItemPrice , int couponDiscountPrice){
        return orderItemPrice - couponDiscountPrice;
    }

}
