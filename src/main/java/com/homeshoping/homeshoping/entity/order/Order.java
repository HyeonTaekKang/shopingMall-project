package com.homeshoping.homeshoping.entity.order;

import com.homeshoping.homeshoping.entity.orderItem.OrderItem;
import com.homeshoping.homeshoping.entity.member.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
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
    private Member member;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems = new ArrayList<>();

    // 연관관계 편의메서드 ( member 셋팅 )
    public void setMember(Member member){
        this.member = member;
    }

    // 연관관계 편의메서드 ( orderItems 셋팅 )
    public void addOrderItems(OrderItem orderItem){
        orderItems.add(orderItem);
//        orderItem.setOrder(this);
        OrderItem.builder()
                .order(this)
                .build();
    }



}
