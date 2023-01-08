package com.homeshoping.homeshoping.service.cart;

import com.homeshoping.homeshoping.entity.Item.Album;
import com.homeshoping.homeshoping.request.Item.ItemCreate;
import com.homeshoping.homeshoping.request.cart.CartCreate;
import com.homeshoping.homeshoping.request.member.AddressCreate;
import com.homeshoping.homeshoping.request.member.MemberCreate;
import com.homeshoping.homeshoping.service.Item.ItemService;
import com.homeshoping.homeshoping.service.member.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CartServiceTest {

    @Autowired
    CartService cartService;

    @Autowired
    MemberService memberService;

    @Autowired
    ItemService itemService;

    @Test
    @DisplayName("cart 생성 테스트 ")
    @Rollback(value = false)
    @Transactional
    void createCart(){
        // given
        // member 생성
        MemberCreate newMember = createNewMember();

        // item 생성 ( 앨범 )
        ItemCreate newItem = createNewItem();

        // 장바구니 셋팅
        CartCreate cartCreate = CartCreate.builder()
                .itemId(1L)
                .memberId(1L)
                .itemCount(10)
                .build();
        // when
        // 장바구니 생성
        cartService.createCart(cartCreate);

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
}