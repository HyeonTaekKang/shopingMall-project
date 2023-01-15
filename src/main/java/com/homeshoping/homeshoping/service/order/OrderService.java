package com.homeshoping.homeshoping.service.order;

import com.homeshoping.homeshoping.Exception.DeliveryNotFound;
import com.homeshoping.homeshoping.Exception.ItemNotFound;
import com.homeshoping.homeshoping.Exception.MemberNotFound;
import com.homeshoping.homeshoping.entity.Item.Item;
import com.homeshoping.homeshoping.entity.coupon.Coupon;
import com.homeshoping.homeshoping.entity.delivery.Delivery;
import com.homeshoping.homeshoping.entity.member.Member;
import com.homeshoping.homeshoping.entity.order.Order;
import com.homeshoping.homeshoping.entity.orderItem.OrderItem;
import com.homeshoping.homeshoping.repository.Item.ItemRepository;
import com.homeshoping.homeshoping.repository.coupon.CouponRepository;
import com.homeshoping.homeshoping.repository.delivery.DeliveryRepository;
import com.homeshoping.homeshoping.repository.member.MemberRepository;
import com.homeshoping.homeshoping.repository.order.OrderRepository;
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
    private final ItemRepository itemRepository;
    private final DeliveryRepository deliveryRepository;
    private final CouponRepository couponRepository;

    @Transactional    // OrderCreate =  ( memberId ,  itemId  , deliveryId  , 주문 수량 )
    public void create(OrderCreate orderCreate){

        // 주문한 회원의 정보 찾아오기 ( member )
        Member member = memberRepository.findById(orderCreate.getMemberId()).orElseThrow(MemberNotFound::new);

        // 회원이 주문한 아이템 찾아오기 ( item )
        Item item = itemRepository.findById(orderCreate.getItemId()).orElseThrow(ItemNotFound::new);

        // 회원이 주문한 배달 정보 찾아오기 ( delivery )
        Delivery delivery = deliveryRepository.findById(orderCreate.getDeliveryId()).orElseThrow(DeliveryNotFound::new);

        // 회원이 주문한 아이템으로 주문아이템 생성 ( orderItem )
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), orderCreate.getOrderCount());

        // 만약 회원이 쿠폰을 적용했으면
        if(orderCreate.getCouponCode() != null && !orderCreate.getCouponCode().equals("") ){

            // 회원이 적용한 쿠폰을 orderCreate에 있는 couponCode로 찾아와서
            Coupon coupon = couponRepository.findByCode(orderCreate.getCouponCode());

            // 쿠폰이 적용된 주문한 아이템의 가격 = 주문한 아이템의 가격 - 쿠폰의 할인가격
            int orderItemDiscountPrice = coupon.returnDiscountedPriceByCoupon(orderItem.getOrderPrice(), coupon.getDiscountPrice());

            // 주문한 아이템의 가격을 쿠폰 할인이 적용된 가격으로 변경.
            orderItem.setOrderPrice(orderItemDiscountPrice);
        }

        // 주문 생성 ( member + orderItem , delivery )
        Order order = Order.createOrder(member , delivery, orderItem );

        // 생성한 order 저장
        orderRepository.save(order);
    }

    public OrderResponse getOrder(Long memberId){

        // 주문한 회원의 "주문 정보" 가져오기
        OrderItem orderItem = orderRepository.getOrder(memberId);

        // orderItem 엔티티 객체에 있는 정보를 "OrderResponse DTO에 옮기기"
        OrderResponse orderResponse = OrderResponse.builder()
                .orderItemId(orderItem.getId())
                .orderItemName(orderItem.getItem().getName())
                .orderPrice(orderItem.getOrderPrice())
                .orderCount(orderItem.getOrderCount())
                .orderDate(orderItem.getOrder().getOrderDate())
                .orderItemStockQuantity(orderItem.getItem().getStockQuantity())
                .orderStatus(orderItem.getOrder().getOrderStatus())
                .build();

        return orderResponse;
    }



}
