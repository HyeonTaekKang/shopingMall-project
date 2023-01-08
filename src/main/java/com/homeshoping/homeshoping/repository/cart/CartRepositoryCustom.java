package com.homeshoping.homeshoping.repository.cart;

import com.homeshoping.homeshoping.entity.cart.Cart;
import com.homeshoping.homeshoping.response.cart.CartResponse;

import java.util.List;

public interface CartRepositoryCustom {

    // cart 에 담긴 아이템들 가져오기
    List<Cart> getCartItems();
}
