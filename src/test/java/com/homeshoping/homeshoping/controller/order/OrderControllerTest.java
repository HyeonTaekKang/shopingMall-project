package com.homeshoping.homeshoping.controller.order;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.homeshoping.homeshoping.Exception.ItemNotFound;
import com.homeshoping.homeshoping.Exception.MemberNotFound;
import com.homeshoping.homeshoping.entity.Item.Album;
import com.homeshoping.homeshoping.entity.Item.Item;
import com.homeshoping.homeshoping.entity.member.Member;
import com.homeshoping.homeshoping.repository.Item.ItemRepository;
import com.homeshoping.homeshoping.repository.member.MemberRepository;
import com.homeshoping.homeshoping.request.Item.ItemCreate;
import com.homeshoping.homeshoping.request.member.AddressCreate;
import com.homeshoping.homeshoping.request.member.MemberCreate;
import com.homeshoping.homeshoping.request.order.OrderCreate;
import com.homeshoping.homeshoping.service.Item.ItemService;
import com.homeshoping.homeshoping.service.member.MemberService;
import com.homeshoping.homeshoping.service.order.OrderService;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class OrderControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    OrderController orderController;

    @Autowired
    MemberService memberService;

    @Autowired
    ItemService itemService;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("order 생성 테스트")
    @Transactional
    @Rollback(value = false)
    void createOrderTest() throws Exception {

        // given
        // member 생성
        MemberCreate newMember = createNewMember();

        // 생성한 member 찾아오기
//        Member member = memberRepository.findById(1L).orElseThrow(MemberNotFound::new);

        // item 생성
        ItemCreate newItem = createNewItem();

        // 생성한 Item 찾아오기
//        Item item = itemRepository.findById(1L).orElseThrow(ItemNotFound::new);

//        int orderCount = 10;

        OrderCreate orderCreate = OrderCreate.builder()
                .memberId(1L)
                .itemId(1L)
                .orderCount(10)
                .build();

        // 객체 -> JSON
        String orderJson = objectMapper.writeValueAsString(orderCreate);

        // expected
        mockMvc.perform(post("/order")
                .contentType(MediaType.APPLICATION_JSON)
                .content(orderJson)
        )
                .andExpect(status().isOk())
                .andDo(print());
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

