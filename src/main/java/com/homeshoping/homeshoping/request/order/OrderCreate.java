package com.homeshoping.homeshoping.request.order;

import com.homeshoping.homeshoping.entity.member.Address;
import com.homeshoping.homeshoping.request.member.AddressCreate;
import com.homeshoping.homeshoping.request.member.MemberCreate;
import com.homeshoping.homeshoping.request.orderItem.OrderItemCreate;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class OrderCreate {

    private Long memberId; // 회원 id

    private Long itemId; // 아이템 id

    private int orderCount; // 주문 수량

    @Builder
    public OrderCreate(Long memberId, Long itemId, int orderCount) {
        this.memberId = memberId;
        this.itemId = itemId;
        this.orderCount = orderCount;
    }
}
