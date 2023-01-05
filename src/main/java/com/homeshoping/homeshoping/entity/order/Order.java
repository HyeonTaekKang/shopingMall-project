package com.homeshoping.homeshoping.entity.order;

import com.homeshoping.homeshoping.entity.delivery.Delivery;
import com.homeshoping.homeshoping.entity.orderItem.OrderItem;
import com.homeshoping.homeshoping.entity.member.Member;
import com.homeshoping.homeshoping.request.member.MemberCreate;
import com.homeshoping.homeshoping.request.order.OrderCreate;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Getter
@Table(name="orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;  // 누가 주문했는지

    @OneToMany(mappedBy = "order" , cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>(); // 주문한 아이템들은 뭔지

    private LocalDateTime orderDate; // 언제 주문했는지

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus; // 주문 상태 ( ORDER ,CANCEL )

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;
//    @Builder
//    public Order(Long id, Member member, List<OrderItem> orderItems, LocalDateTime orderDate) {
//        this.id = id;
//        this.member = member;
//        this.orderItems = orderItems;
//        this.orderDate = orderDate;
//
//        // 연관관계 맵핑 ( order - orderItem )
//        OrderItem.builder().order(this);
//    }

    //생성자 오버로딩 ( Order 추가 (OrderCreate -> Order) )
//    @Builder
//    public Order(OrderCreate orderCreate) {
//        // 주문한 회원의 정보중 이름만 전달
//        member = orderCreate.getMemberCreate()
//
//    }

    // Order 생성 메서드
    public static Order createOrder(Member member , OrderItem ...orderItems){

        Order order = new Order();

        // order엔티티에 매개변수로 받아온 member를 'setMember'라는 연관관계 편의메세드로 연관관계를 맺어줌.
        order.setMember(member);

        // orderItem 리스트에 주문한 아이템들 add
        for (OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }

        // 주문날짜 = order 생성시간 ( 지금)
        order.orderDate = LocalDateTime.now();

        // 주문상태 = ORDER
        order.orderStatus = OrderStatus.ORDER;

        return order;
    }


    // 연관관계 편의메서드 ( member 셋팅 )
    public void setMember(Member member){
        this.member = member;
    }


    // 연관관계 편의메서드 ( orderItem 셋팅 )
    public void addOrderItem(OrderItem orderItem){
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }





}
