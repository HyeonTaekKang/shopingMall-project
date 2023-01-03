package com.homeshoping.homeshoping.service.order;

import com.homeshoping.homeshoping.Exception.ItemNotFound;
import com.homeshoping.homeshoping.Exception.MemberNotFound;
import com.homeshoping.homeshoping.entity.Item.Item;
import com.homeshoping.homeshoping.entity.member.Member;
import com.homeshoping.homeshoping.entity.order.Order;
import com.homeshoping.homeshoping.entity.orderItem.OrderItem;
import com.homeshoping.homeshoping.repository.Item.ItemRepository;
import com.homeshoping.homeshoping.repository.member.MemberRepository;
import com.homeshoping.homeshoping.repository.order.OrderRepository;
import com.homeshoping.homeshoping.repository.orderItem.OrderItemRepository;
import com.homeshoping.homeshoping.request.member.MemberCreate;
import com.homeshoping.homeshoping.request.order.OrderCreate;
import com.homeshoping.homeshoping.response.order.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final OrderItemRepository orderItemRepository;
    private final ItemRepository itemRepository;

    @Transactional      // memberId    // itemId    // 주문 수량
    public void create(Long memberId, Long itemId , int orderCount){

        // 주문한 회원의 정보 찾아오기 ( member )
        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFound::new);

        // 회원이 주문한 아이템 찾아오기 ( item )
        Item item = itemRepository.findById(itemId).orElseThrow(ItemNotFound::new);

        // 회원이 주문한 아이템으로 주문아이템 생성 ( orderItem )
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), orderCount);

        // 주문 생성 ( member + orderItem )
        Order order = Order.createOrder(member , orderItem);

        // 생성한 order 저장
        orderRepository.save(order);
    }

}
