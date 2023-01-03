package com.homeshoping.homeshoping.controller.order;

import com.homeshoping.homeshoping.request.order.OrderCreate;
import com.homeshoping.homeshoping.service.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    // 주문 생성
    @PostMapping("/order")
    public void createOrder(@RequestBody OrderCreate orderCreate){
        orderService.create(orderCreate);
    }
}
