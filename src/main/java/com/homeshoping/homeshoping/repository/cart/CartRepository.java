package com.homeshoping.homeshoping.repository.cart;

import com.homeshoping.homeshoping.entity.cart.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
