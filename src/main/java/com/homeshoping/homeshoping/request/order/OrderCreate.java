package com.homeshoping.homeshoping.request.order;

import com.homeshoping.homeshoping.entity.member.Address;
import com.homeshoping.homeshoping.request.member.AddressCreate;
import com.homeshoping.homeshoping.request.member.MemberCreate;
import com.homeshoping.homeshoping.request.orderItem.OrderItemCreate;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class OrderCreate {

    private Long orderId; // order ID

    private MemberCreate memberCreate; // 주문한 회원 정보

    private List<OrderItemCreate> orderItems = new ArrayList<>(); // 주문한 아이템들

    private LocalDateTime orderDate; // 주문한 날짜

    private AddressCreate addressCreate; // 주문한 회원의 주소

    @Builder
    public OrderCreate(Long orderId, MemberCreate memberCreate, List<OrderItemCreate> orderItems, LocalDateTime orderDate, AddressCreate addressCreate) {
        this.orderId = orderId;
        this.memberCreate = memberCreate;
        this.orderItems = orderItems;
        this.orderDate = orderDate;
        this.addressCreate = addressCreate;
    }
}
