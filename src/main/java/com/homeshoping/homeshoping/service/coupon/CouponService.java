package com.homeshoping.homeshoping.service.coupon;

import com.homeshoping.homeshoping.entity.coupon.Coupon;
import com.homeshoping.homeshoping.repository.coupon.CouponRepository;
import com.homeshoping.homeshoping.request.coupon.CouponCreate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;

    // 쿠폰 생성
    public Long createCoupon(CouponCreate couponCreate){

        // DTO -> entity
        Coupon coupon = Coupon.createCoupon(couponCreate);

        couponRepository.save(coupon);
        return coupon.getCode();
    }

    // 생성된 쿠폰 조회
    public Coupon getOneCoupon(Long couponCode){

        // 쿠폰 코드로 쿠폰 찾아오기
        Coupon coupon = couponRepository.findByCode(couponCode);

        // 찾아온 쿠폰 return
        return coupon;
    }





}
