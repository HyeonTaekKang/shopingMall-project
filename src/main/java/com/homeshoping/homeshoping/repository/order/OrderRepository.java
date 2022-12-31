package com.homeshoping.homeshoping.repository.order;


import com.homeshoping.homeshoping.entity.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
