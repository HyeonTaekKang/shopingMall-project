package com.homeshoping.homeshoping.repository.order;

import com.homeshoping.homeshoping.entity.Item.QItem;
import com.homeshoping.homeshoping.entity.member.QMember;
import com.homeshoping.homeshoping.entity.order.Order;
import com.homeshoping.homeshoping.entity.order.QOrder;
import com.homeshoping.homeshoping.entity.orderItem.OrderItem;
import com.homeshoping.homeshoping.entity.orderItem.QOrderItem;
import com.homeshoping.homeshoping.response.order.OrderResponse;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.homeshoping.homeshoping.entity.Item.QItem.*;
import static com.homeshoping.homeshoping.entity.member.QMember.*;
import static com.homeshoping.homeshoping.entity.order.QOrder.*;
import static com.homeshoping.homeshoping.entity.orderItem.QOrderItem.*;

@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    // 해당 회원이 주문한 주문의 정보를 최신순으로 가져오는 메서드
    public OrderItem getOrder(Long memberId) {
        return jpaQueryFactory
                .selectFrom(orderItem)
                .innerJoin(orderItem.item , item).fetchJoin()
                .innerJoin(orderItem.order, order).fetchJoin()
                .where(order.member.id.eq(memberId))
                .orderBy(order.orderDate.desc())
                .fetchOne();
    }
}
