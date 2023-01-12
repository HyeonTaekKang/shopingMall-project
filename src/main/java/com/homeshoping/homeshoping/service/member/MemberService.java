package com.homeshoping.homeshoping.service.member;

import com.homeshoping.homeshoping.Exception.MemberNotFound;
import com.homeshoping.homeshoping.entity.coupon.Coupon;
import com.homeshoping.homeshoping.entity.member.Member;
import com.homeshoping.homeshoping.entity.memberCoupon.MemberCoupon;
import com.homeshoping.homeshoping.repository.coupon.CouponRepository;
import com.homeshoping.homeshoping.repository.member.MemberRepository;
import com.homeshoping.homeshoping.repository.memberCoupon.MemberCouponRepository;
import com.homeshoping.homeshoping.request.member.MemberCreate;
import com.homeshoping.homeshoping.response.member.MemberResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final CouponRepository couponRepository;
    private final MemberCouponRepository memberCouponRepository;

    // 회원가입
    public void join(MemberCreate memberCreate){
        memberRepository.save(new Member(memberCreate));
    }


    // 쿠폰 발급받기
    public void downloadCoupon(Long memberId , Long couponCode){

        // 쿠폰을 발급받을 멤버 찾아오기
        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFound::new);

        // 매개변수로 받아온 couponCode로 쿠폰 찾아오기
        Coupon coupon = couponRepository.findByCode(couponCode);

        MemberCoupon memberCoupon = MemberCoupon.createMemberCoupon(member, coupon);

        memberCouponRepository.save(memberCoupon);

    }

    // 쿠폰 사용



    // 회원정보 가져오기
//    public MemberResponse find(Long memberId){
//
//        // member 찾아오고
//        Member member = memberRepository.findById(memberId).orElseThrow(() -> new MemberNotFound());
//
//        // 엔티티 -> DTO로 변경해서 리턴
//        return new Member;
//    }
}
