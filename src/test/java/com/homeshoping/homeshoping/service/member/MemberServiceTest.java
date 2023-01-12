package com.homeshoping.homeshoping.service.member;

import com.homeshoping.homeshoping.entity.coupon.Coupon;
import com.homeshoping.homeshoping.entity.member.Member;
import com.homeshoping.homeshoping.entity.memberCoupon.MemberCoupon;
import com.homeshoping.homeshoping.repository.member.MemberRepository;
import com.homeshoping.homeshoping.request.coupon.CouponCreate;
import com.homeshoping.homeshoping.request.member.AddressCreate;
import com.homeshoping.homeshoping.request.member.MemberCreate;
import com.homeshoping.homeshoping.service.coupon.CouponService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    CouponService couponService;

    @Autowired
    MemberRepository memberRepository;


    @Test
    @Transactional
    @Rollback(value = false)
    @DisplayName("회원 쿠폰 발급 테스트")
    void downloadCouponTest(){

        // given
        // 회원 생성
        Long memberId = createNewMember();

        // 쿠폰 생성
        Long couponCode = createNewCoupon();

        // when
        // 회원이 쿠폰을 발급받음.
        memberService.downloadCoupon(memberId , couponCode);

        // then
    }

    // 쿠폰 생성 메서드
    private Long createNewCoupon(){

        // 쿠폰 정보 셋팅
        CouponCreate couponCreate = CouponCreate.builder()
                .name("설날기념 10% 할인 쿠폰")
                .description("설날 기념으로 전상품 10%할인!")
                .discountPrice(10000)
                .createdAt(LocalDateTime.now())
                .expiredAt(LocalDateTime.now().plusDays(20))
                .build();

        // 위에서 만든 쿠폰 정보로 쿠폰을 생성하고 , 만들어진 쿠폰의 쿠폰코드 리턴
        Long couponCode = couponService.createCoupon(couponCreate);

        return couponCode;
    }

    // 멤버 생성 테스트
    // 새로운 회원생성 메서드
    private Long createNewMember(){
        AddressCreate addressCreate = AddressCreate.builder()
                .city("송파구")
                .street("석촌동")
                .zipcode("1234").build();

        // 회원정보
        MemberCreate memberCreate = MemberCreate.builder()
                .name("강현택")
                .email("wjavmtngkr1@naver.com")
                .password("ekzmfkdl11!!")
                .addressCreate(addressCreate)
                .build();

        // 새로운 멤버 생성
        Member newMember = Member.createMember(memberCreate);

        memberRepository.save(newMember);
        return newMember.getId();
    }

}