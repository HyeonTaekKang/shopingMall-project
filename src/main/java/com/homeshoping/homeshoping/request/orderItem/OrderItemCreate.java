package com.homeshoping.homeshoping.request.orderItem;

import lombok.Builder;

public class OrderItemCreate {

    private String name; // 주문한 상품 이름

    private int orderPrice; // 주문 가격

    private int orderCount; // 주문 수량

    @Builder
    public OrderItemCreate(String name, int orderPrice, int orderCount) {
        this.name = name;
        this.orderPrice = orderPrice;
        this.orderCount = orderCount;
    }
}
