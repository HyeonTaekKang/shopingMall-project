package com.homeshoping.homeshoping.service.order;

import com.homeshoping.homeshoping.entity.Item.Category.Album;
import com.homeshoping.homeshoping.entity.Item.Item;
import com.homeshoping.homeshoping.entity.address.Address;
import com.homeshoping.homeshoping.entity.coupon.Coupon;
import com.homeshoping.homeshoping.entity.delivery.Delivery;
import com.homeshoping.homeshoping.entity.member.Member;
import com.homeshoping.homeshoping.entity.order.OrderStatus;
import com.homeshoping.homeshoping.repository.Item.ItemRepository;
import com.homeshoping.homeshoping.repository.coupon.CouponRepository;
import com.homeshoping.homeshoping.repository.delivery.DeliveryRepository;
import com.homeshoping.homeshoping.repository.member.MemberRepository;
import com.homeshoping.homeshoping.repository.order.OrderRepository;
import com.homeshoping.homeshoping.request.Item.ItemCreate;
import com.homeshoping.homeshoping.request.coupon.CouponCreate;
import com.homeshoping.homeshoping.request.member.AddressCreate;
import com.homeshoping.homeshoping.request.member.MemberCreate;
import com.homeshoping.homeshoping.request.order.OrderCreate;
import com.homeshoping.homeshoping.response.order.OrderResponse;
import com.homeshoping.homeshoping.service.Item.ItemService;
import com.homeshoping.homeshoping.service.coupon.CouponService;
import com.homeshoping.homeshoping.service.member.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrderServiceTest {

    @Autowired
    OrderService orderService;

    @Autowired
    MemberService memberService;

    @Autowired
    ItemService itemService;

    @Autowired
    CouponService couponService;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    DeliveryRepository deliveryRepository;

    @Autowired
    CouponRepository couponRepository;

    @PersistenceContext
    EntityManager em;

    @BeforeEach
    void clean() {
        memberRepository.deleteAll();
        orderRepository.deleteAll();
        itemRepository.deleteAll();
        deliveryRepository.deleteAll();
    }

    @Test
    @DisplayName("order 생성 테스트")
    @Rollback(value = false)
    @Transactional
    void createOrderTest(){
        // given
        // member 생성
        createNewMember();

        // item 생성 ( 앨범 )
        createNewItem();

        // delivery 생성
        createNewDelivery();

        // when
        // 새로운 order 셋팅
        OrderCreate newOrder = createNewOrder();

        // 새로운 order 생성
        orderService.create(newOrder);

        // then
    }

    @Test
    @DisplayName("order 생성 테스트 ( with coupon )")
    @Rollback(value = false)
    @Transactional
    void createOrderWithCouponTest(){
        // given
        // member 생성
        Long newMemberId = createNewMember();

        // item 생성 ( 앨범 )
        Long newItemId = createNewItem();

        // delivery 생성
        Long newDeliveryId = createNewDelivery();

        // 쿠폰 셋팅 ( 쿠폰 코드 리턴 )
        Long couponCode = createNewCoupon();

        // Order 정보 셋팅
        OrderCreate orderCreate = OrderCreate.builder()
                .memberId(newMemberId)
                .itemId(newItemId)
                .deliveryId(newDeliveryId)
                .orderCount(10)
                .couponCode(couponCode)
                .build();

        // when
        // 위에서 만든 Order 정보로 새로운 order 생성
        orderService.create(orderCreate);

        // then
        // ( member , item(album) , delivery , coupon , member_coupon , orders , order_item )

    }

    @Test
    @DisplayName("회원이 주문한 '주문 정보 가져오기 테스트' ")
    @Rollback(value = false)
    @Transactional
    void getOrderTest(){

        // 새로운 member 생성
        createNewMember();

        // 새로운 item 생성 ( 앨범 )
        createNewItem();

        // 새로운 order 셋팅
        OrderCreate newOrder = createNewOrder();

        // 새로운 order 생성 ( 앨범 10개 주문 )
        orderService.create(newOrder);

        // order 정보 가져오기
        OrderResponse orderResponse = orderService.getOrder(1L);

        assertEquals(1L,orderResponse.getOrderItemId()); // 주문한 아이템의 id
        assertEquals("savage",orderResponse.getOrderItemName()); // 주문한 아이템의 이름
        assertEquals(10000,orderResponse.getOrderPrice()); // 주문한 아이템의 가격
        assertEquals(10,orderResponse.getOrderCount()); // 주문한 아이템 수량
        assertEquals(9990,orderResponse.getOrderItemStockQuantity()); // 주문후 남은 아이템의 수량 (10000 - 10 = 9990)
        assertEquals(OrderStatus.ORDER, orderResponse.getOrderStatus()); // 주문후 주문의 상태 ( ORDER )
    }





    // =====================================================================================================

    // 새로운 배달정보 생성 메서드
    private Long createNewDelivery(){
        Address address = Address.builder()
                .city("송파구")
                .street("석촌동")
                .zipcode("1234")
                .build();

        Delivery newDelivery = Delivery.createDelivery(address);

        deliveryRepository.save(newDelivery);
        return newDelivery.getId();
    }



    // 새로운 회원생성 메서드
    private Long createNewMember(){
        AddressCreate addressCreate = AddressCreate.builder()
                .city("송파구")
                .street("석촌동")
                .zipcode("1234").build();

        // 회원정보
        MemberCreate memberCreate = MemberCreate.builder()
                .name("강현택")
                .email("wjavmtngkr1@naver.com")
                .password("ekzmfkdl11!!")
                .addressCreate(addressCreate)
                .build();

        // 새로운 멤버 생성
        Member newMember = Member.createMember(memberCreate);

        memberRepository.save(newMember);
        return newMember.getId();
    }

    // 새로운 아이템( 앨범 ) 생성 메서드
    private Long createNewItem(){
        // 제품 한개 등록 ( 앨범 )
        Album album = Album.builder()
                .artist("에스파")
                .build();

        ItemCreate itemCreate = ItemCreate.builder()
                .name("savage")
                .price(10000)
                .stockQuantity(10000)
                .itemType("Album")
                .album(album)
                .build();

        // 아이템 생성
        Item newItem = Item.createItem(itemCreate);

        // 아이템 저장
        itemRepository.save(newItem);
        return newItem.getId();
    }

    // 새로운 주문 생성 메서드
    private OrderCreate createNewOrder(){

        OrderCreate orderCreate = OrderCreate.builder()
                .memberId(1L)
                .itemId(1L)
                .deliveryId(1L)
                .orderCount(10)
                .build();

        return orderCreate;
    }

    // 쿠폰 생성 메서드
    private Long createNewCoupon(){

        // 쿠폰 정보 셋팅
        CouponCreate couponCreate = CouponCreate.builder()
                .name("설날기념 10% 할인 쿠폰")
                .description("설날 기념으로 전상품 10%할인!")
                .discountPrice(1000)
                .createdAt(LocalDateTime.now())
                .expiredAt(LocalDateTime.now().plusDays(20))
                .build();

        // 위에서 만든 쿠폰 정보로 쿠폰을 생성하고 , 만들어진 쿠폰의 쿠폰코드 리턴
        Long couponCode = couponService.createCoupon(couponCreate);

        return couponCode;
    }

    // 새로운 쿠폰 생성
    public Long createCoupon(CouponCreate couponCreate){

        // DTO -> entity
        Coupon coupon = Coupon.createCoupon(couponCreate);

        couponRepository.save(coupon);
        return coupon.getCode();
    }






}