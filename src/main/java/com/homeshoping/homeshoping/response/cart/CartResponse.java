package com.homeshoping.homeshoping.response.cart;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CartResponse {

    private String seller; // 회사명 or 판매자 이름

    private String itemName; // 아이템명

    private int itemCount; // 아이템 수량

    private int itemPrice; // 아이템 가격

    @Builder
    public CartResponse(String seller, String itemName, int itemCount, int itemPrice) {
        this.seller = seller;
        this.itemName = itemName;
        this.itemCount = itemCount;
        this.itemPrice = itemPrice;
    }
}
