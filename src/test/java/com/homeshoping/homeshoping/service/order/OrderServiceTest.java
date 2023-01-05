package com.homeshoping.homeshoping.service.order;

import com.homeshoping.homeshoping.Exception.ItemNotFound;
import com.homeshoping.homeshoping.Exception.MemberNotFound;
import com.homeshoping.homeshoping.entity.Item.Album;
import com.homeshoping.homeshoping.entity.Item.Item;
import com.homeshoping.homeshoping.entity.member.Address;
import com.homeshoping.homeshoping.entity.member.Member;
import com.homeshoping.homeshoping.entity.order.OrderStatus;
import com.homeshoping.homeshoping.repository.Item.ItemRepository;
import com.homeshoping.homeshoping.repository.member.MemberRepository;
import com.homeshoping.homeshoping.repository.order.OrderRepository;
import com.homeshoping.homeshoping.request.Item.ItemCreate;
import com.homeshoping.homeshoping.request.member.AddressCreate;
import com.homeshoping.homeshoping.request.member.MemberCreate;
import com.homeshoping.homeshoping.request.order.OrderCreate;
import com.homeshoping.homeshoping.response.member.MemberResponse;
import com.homeshoping.homeshoping.response.order.OrderResponse;
import com.homeshoping.homeshoping.service.Item.ItemService;
import com.homeshoping.homeshoping.service.member.MemberService;
import org.junit.jupiter.api.Assertions;
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
    MemberRepository memberRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    OrderRepository orderRepository;

    @PersistenceContext
    EntityManager em;

    @BeforeEach
    void clean() {
        memberRepository.deleteAll();
        orderRepository.deleteAll();
        itemRepository.deleteAll();
    }

    @Test
    @DisplayName("order 생성 테스트")
    @Rollback(value = false)
    @Transactional
    void createOrderTest(){
        // given
        // member 생성
        MemberCreate newMember = createNewMember();

        // 생성한 member 찾아오기
//        Member member = memberRepository.findById(1L).orElseThrow(MemberNotFound::new);

        // item 생성 ( 앨범 )
        ItemCreate newItem = createNewItem();

        // 생성한 Item 찾아오기
//        Item item = itemRepository.findById(1L).orElseThrow(ItemNotFound::new);

        // orderCreate
        OrderCreate orderCreate = OrderCreate.builder()
                .memberId(1L)
                .itemId(1L)
                .orderCount(10)
                .build();

        // order 생성
        orderService.create(orderCreate);

        // then

    }

    @Test
    @DisplayName("회원이 주문한 '주문 정보 가져오기 테스트' ")
    @Rollback(value = false)
    @Transactional
    void getOrderTest(){

        // 새로운 member 생성
        MemberCreate newMember = createNewMember();

        // 새로운 item 생성 ( 앨범 )
        ItemCreate newItem = createNewItem();

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











    // 새로운 회원생성 메서드
    private MemberCreate createNewMember(){
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

        memberService.join(memberCreate);
        return memberCreate;
    }

    // 새로운 아이템( 앨범 ) 생성 메서드
    private ItemCreate createNewItem(){
        // 제품 한개 등록 ( 앨범 )
        Album album = Album.builder()
                .artist("에스파")
                .build();

        ItemCreate itemCreate = ItemCreate.builder()
                .name("savage")
                .price(10000)
                .stockQuantity(10000)
                .date(LocalDateTime.now())
                .itemType("Album")
                .album(album)
                .build();

        itemService.itemRegistration(itemCreate);
        return itemCreate;
    }

    // 새로운 주문 생성 메서드
    private OrderCreate createNewOrder(){

        OrderCreate orderCreate = OrderCreate.builder()
                .memberId(1L)
                .itemId(1L)
                .orderCount(10)
                .build();

        return orderCreate;
    }






}