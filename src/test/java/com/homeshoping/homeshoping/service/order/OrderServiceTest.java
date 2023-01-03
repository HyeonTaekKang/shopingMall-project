package com.homeshoping.homeshoping.service.order;

import com.homeshoping.homeshoping.Exception.ItemNotFound;
import com.homeshoping.homeshoping.Exception.MemberNotFound;
import com.homeshoping.homeshoping.entity.Item.Album;
import com.homeshoping.homeshoping.entity.Item.Item;
import com.homeshoping.homeshoping.entity.member.Address;
import com.homeshoping.homeshoping.entity.member.Member;
import com.homeshoping.homeshoping.repository.Item.ItemRepository;
import com.homeshoping.homeshoping.repository.member.MemberRepository;
import com.homeshoping.homeshoping.repository.order.OrderRepository;
import com.homeshoping.homeshoping.request.Item.ItemCreate;
import com.homeshoping.homeshoping.request.member.AddressCreate;
import com.homeshoping.homeshoping.request.member.MemberCreate;
import com.homeshoping.homeshoping.request.order.OrderCreate;
import com.homeshoping.homeshoping.response.member.MemberResponse;
import com.homeshoping.homeshoping.service.Item.ItemService;
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
@Transactional
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
        Member member = memberRepository.findById(1L).orElseThrow(MemberNotFound::new);

        // item 생성
        ItemCreate newItem = createNewItem();

        // 생성한 Item 찾아오기
        Item item = itemRepository.findById(1L).orElseThrow(ItemNotFound::new);

        // order 생성
        orderService.create(member.getId(),item.getId(),10);

        // then

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

    // 새로운 아이템 생성 메서드
    private ItemCreate createNewItem(){
        // 제품 한개 등록 ( 앨범 )
        Album album = Album.builder()
                .artist("에스파")
                .build();

        ItemCreate itemCreate = ItemCreate.builder()
                .name("savage")
                .price("10000")
                .stockQuantity("1000")
                .date(LocalDateTime.now())
                .itemType("Album")
                .album(album)
                .build();

        itemService.itemRegistration(itemCreate);
        return itemCreate;
    }






}