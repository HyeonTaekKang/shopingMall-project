package com.homeshoping.homeshoping.controller.cart;

import com.homeshoping.homeshoping.response.cart.ListResponse;
import com.homeshoping.homeshoping.service.cart.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping("/cart/{memberId}")
    public ListResponse readItemInCart(@PathVariable Long memberId){
        return cartService.getItemsInCart(memberId);
    }

}
