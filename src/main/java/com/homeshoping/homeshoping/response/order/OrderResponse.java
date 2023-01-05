package com.homeshoping.homeshoping.response.order;

import com.homeshoping.homeshoping.entity.order.OrderStatus;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class OrderResponse {
    private Long orderItemId; // 주문한 아이템의 Id
    private String orderItemName; // 주문한 아이템의 이름
    private int orderPrice; // 주문한 아이템의 가격
    private int orderCount; // 주문한 아이템의 갯수
    private LocalDateTime orderDate; // 주문한 날짜
    private int orderItemStockQuantity; // 주문한 아이템의 재고수량
    private OrderStatus orderStatus; // 주문 상태 [ ORDER, CANCEL ]

    @Builder
    public OrderResponse(Long orderItemId, String orderItemName, int orderPrice, int orderCount, LocalDateTime orderDate, int orderItemStockQuantity, OrderStatus orderStatus) {
        this.orderItemId = orderItemId;
        this.orderItemName = orderItemName;
        this.orderPrice = orderPrice;
        this.orderCount = orderCount;
        this.orderDate = orderDate;
        this.orderItemStockQuantity = orderItemStockQuantity;
        this.orderStatus = orderStatus;
    }
}
