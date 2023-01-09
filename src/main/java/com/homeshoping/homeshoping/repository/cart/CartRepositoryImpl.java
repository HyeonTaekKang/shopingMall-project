package com.homeshoping.homeshoping.repository.cart;


import com.homeshoping.homeshoping.entity.cart.Cart;
import com.homeshoping.homeshoping.entity.cart.QCart;
import com.homeshoping.homeshoping.response.cart.CartResponse;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.homeshoping.homeshoping.entity.Item.QItem.item;
import static com.homeshoping.homeshoping.entity.cart.QCart.*;
import static com.homeshoping.homeshoping.entity.member.QMember.member;
import static com.homeshoping.homeshoping.entity.order.QOrder.order;

@RequiredArgsConstructor
public class CartRepositoryImpl implements CartRepositoryCustom{


    private final JPAQueryFactory jpaQueryFactory;

    @Override  // 카트에 담긴 아이템들의 정보를 리스트 형태로 반환한다.
    // cart 를 가져올 때 member , item 도 같이 fetch join해서 select해온다.
    public List<Cart> getCartItems(Long memberId) {
        return jpaQueryFactory
                .selectFrom(cart)
                .innerJoin(cart.member , member).fetchJoin()
                .innerJoin(cart.item , item).fetchJoin()
                .where(cart.member.id.eq(memberId))
                .orderBy(cart.reg_date.desc())
                .fetch();
    }
}
