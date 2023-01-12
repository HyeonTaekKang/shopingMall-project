package com.homeshoping.homeshoping.repository.coupon;

import com.homeshoping.homeshoping.entity.coupon.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<Coupon, Long> {

    // coupon의 code로 쿠폰 찾기
    Coupon findByCode(Long couponCode);
}
