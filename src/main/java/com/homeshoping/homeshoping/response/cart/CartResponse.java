package com.homeshoping.homeshoping.response.cart;

import com.homeshoping.homeshoping.entity.cart.Cart;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CartResponse {

    private String seller; // 아이템을 판매하는 회사명 or 판매자 이름

    private String itemName; // 카드에 담은 아이템명

    private int itemCount; // 카드에 담은 아이템의 수량

    private int itemPrice; // 카드에 담은 아이템의 가격

    @Builder
    public CartResponse(String seller, String itemName, int itemCount, int itemPrice) {
        this.seller = seller;
        this.itemName = itemName;
        this.itemCount = itemCount;
        this.itemPrice = itemPrice;
    }

    // 생성자 오버로딩 ( 엔티티 -> DTO )
    @Builder
    public CartResponse(Cart cart) {
        this.seller = cart.getMember().getName();
        this.itemName = cart.getItem().getName();
        this.itemCount = cart.getItemCount();
        this.itemPrice = cart.getItem().getPrice();
    }
}
