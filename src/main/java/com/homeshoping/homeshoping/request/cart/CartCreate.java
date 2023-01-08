package com.homeshoping.homeshoping.request.cart;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CartCreate {

    private Long itemId;  // 상품 id

    private Long memberId;  // 회원 id

    private int itemCount;  // 카트에 담긴 아이템의 주문수량

    @Builder
    public CartCreate(Long itemId, Long memberId, int itemCount) {
        this.itemId = itemId;
        this.memberId = memberId;
        this.itemCount = itemCount;
    }
}

