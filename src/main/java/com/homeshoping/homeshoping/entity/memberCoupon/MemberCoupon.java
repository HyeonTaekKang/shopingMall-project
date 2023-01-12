package com.homeshoping.homeshoping.entity.memberCoupon;

import com.homeshoping.homeshoping.entity.coupon.Coupon;
import com.homeshoping.homeshoping.entity.member.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberCoupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_coupon_id")
    private Long memberCouponId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="coupon_id")
    private Coupon coupon;

    // MemberCoupon 생성 메서드
    public static MemberCoupon createMemberCoupon(Member member , Coupon coupon){
        MemberCoupon memberCoupon = new MemberCoupon();

        memberCoupon.setMember(member);
        memberCoupon.setCoupon(coupon);

        return memberCoupon;
    }

    // 연관관계 편의 메서드 ( memberCoupon - member )
    public void setMember(Member member){
        this.member = member;
    }

    // 연관관계 편의 메서드 ( memberCoupon - coupon )
    public void setCoupon(Coupon coupon){
        this.coupon = coupon;
    }
}
