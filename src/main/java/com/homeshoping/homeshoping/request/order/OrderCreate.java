package com.homeshoping.homeshoping.request.order;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderCreate {

    private Long memberId; // 회원 id

    private Long itemId; // 아이템 id

    private Long deliveryId; // 배달 id

    private int orderCount; // 주문 수량

    private Long couponCode; // 쿠폰 코드


    @Builder
    public OrderCreate(Long memberId, Long itemId, Long deliveryId, int orderCount, Long couponCode) {
        this.memberId = memberId;
        this.itemId = itemId;
        this.deliveryId = deliveryId;
        this.orderCount = orderCount;
        this.couponCode = couponCode;
    }
}
