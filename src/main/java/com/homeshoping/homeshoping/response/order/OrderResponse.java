package com.homeshoping.homeshoping.response.order;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;

import java.time.LocalDateTime;

public class OrderResponse {
    private Long orderItemId; // 주문한 아이템의 Id
    private String orderItemName; // 주문한 아이템의 이름
    private String orderPrice; // 주문한 아이템의 가격
    private LocalDateTime orderDate; // 주문한 날짜
    private String orderItemStockQuantity; // 주문한 아이템의 재고수량

    @QueryProjection
    public OrderResponse(Long orderItemId, String orderItemName, String orderPrice, LocalDateTime orderDate, String orderItemStockQuantity) {
        this.orderItemId = orderItemId;
        this.orderItemName = orderItemName;
        this.orderPrice = orderPrice;
        this.orderDate = orderDate;
        this.orderItemStockQuantity = orderItemStockQuantity;
    }
}
