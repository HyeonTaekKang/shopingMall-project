package com.homeshoping.homeshoping.entity.orderItem;

import com.homeshoping.homeshoping.entity.Item.Item;
import com.homeshoping.homeshoping.entity.order.Order;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    private int orderPrice; // 주문 가격 ( 주문 당시의 상품 가격 )

    private int orderCount; // 주문 수량 ( 몇 개를 주문했는지 )

    @Builder
    public OrderItem(Order order, Item item, int orderPrice, int orderCount) {
        this.order = order;
        this.item = item;
        this.orderPrice = orderPrice;
        this.orderCount = orderCount;
    }

    // 생성메서드
    public static OrderItem createOrderItem(Item item , int orderPrice , int orderCount){
        OrderItem orderItem = new OrderItem();

        // 연관관계 셋팅 ( orderItem - item  )
        orderItem.setItem(item);

        orderItem.orderPrice = orderPrice;
        orderItem.orderCount = orderCount;

        // 주문한 수량만큼 item 의 재고량 감소
        item.removeStockQuantity(orderCount);

        return orderItem;
    }

    // 연관관계 편의메서드 ( orderItem - item )
    public void setItem(Item item){
        this.item = item;
    }

    // 연관관계 편의메서드 ( orderItem - order )
    public void setOrder(Order order){
        this.order = order;
    }


    // === 비지니스 메서드 ===
    // 쿠폰할인이 적용된 가격을 주문가격으로 설정하기 위한 메서드.
    public void setOrderPrice(int orderItemDiscountPrice){
        this.orderPrice = orderItemDiscountPrice;
    }
}
