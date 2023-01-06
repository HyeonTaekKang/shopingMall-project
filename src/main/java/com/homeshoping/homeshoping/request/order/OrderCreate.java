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

    @Builder
    public OrderCreate(Long memberId, Long itemId, Long deliveryId, int orderCount) {
        this.memberId = memberId;
        this.itemId = itemId;
        this.deliveryId = deliveryId;
        this.orderCount = orderCount;
    }
}
