package com.homeshoping.homeshoping.service.cart;

import com.homeshoping.homeshoping.entity.Item.Category.Album;
import com.homeshoping.homeshoping.entity.Item.Item;
import com.homeshoping.homeshoping.entity.member.Member;
import com.homeshoping.homeshoping.repository.Item.ItemRepository;
import com.homeshoping.homeshoping.repository.member.MemberRepository;
import com.homeshoping.homeshoping.request.Item.ItemCreate;
import com.homeshoping.homeshoping.request.cart.CartCreate;
import com.homeshoping.homeshoping.request.member.AddressCreate;
import com.homeshoping.homeshoping.request.member.MemberCreate;
import com.homeshoping.homeshoping.response.cart.ListResponse;
import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class CartServiceTest {

    @Autowired
    CartService cartService;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    ItemRepository itemRepository;

    @Test
    @DisplayName("cart에 여래개의 아이템을 담는 것을 테스트")
    @Rollback(value = false)
    @Transactional
    void createCartWithManyItemTest(){
        // given
        // 장바구니에 아이템 100개 넣어서 장바구니 생성
        createCartWithManyItem();
    }

    @Test
    @DisplayName("cart 에 담긴 아이템 가져오기 테스트")
    @Rollback(value = false)
    @Transactional
    void getItemInCartTest(){
        // given
        // 장바구니에 아이템 100개 담아서 장바구니를 생성함.
        Long memberId = createCartWithManyItem();

        // when
        // cart에 담긴 아이템 정보들 가져오기
        /**
         *  {
         *     "data": [
         *       {
         *         "seller": "강현택",
         *         "itemName": "savage100",
         *         "itemCount": 10,
         *         "itemPrice": 10000
         *       },
         *       {
         *         "seller": "강현택",
         *         "itemName": "savage99",
         *         "itemCount": 10,
         *         "itemPrice": 10000
         *       },
         *     ]
         *   }
         */
        ListResponse itemsInCart = cartService.getItemsInCart(memberId);

        // then
        // 정보 검증


    }

//    @Test
//    @DisplayName("cart 에 담긴 아이템들 가져오기 테스트")
//    @Rollback(value = false)
//    @Transactional
//    void getItemsInCartTest(){
//        // given
//        // 장바구니에 아이템 100개 넣어서 장바구니 생성
//        createCartWithManyItem();
//
//        // when
//        // cart에 담긴 아이템들 가져오기
//        List<CartResponse> cartItems = cartService.getItemsInCart(); // [ {} , {} , {} ]
//
//        // then
//        // 정보 검증
//        assertEquals(100 , cartItems.size());
//    }


    // 장바구니 생성 메서드 ( 장바구니에 아이템 100개 넣어서 )
    private Long createCartWithManyItem(){
        // member 1명 생성
        Member newMember = createNewMember();

        // 아이템 100개 생성
        for(int i = 1; i<=100; i++){
            // 제품 100개 등록 ( 앨범 )
            Album album = Album.builder()
                    .artist("에스파" + i)
                    .build();

            ItemCreate itemCreate = ItemCreate.builder()
                    .name("savage" + i)
                    .price(10000)
                    .stockQuantity(10000)
                    .itemType("Album")
                    .album(album)
                    .build();

            // 아이템 생성
            Item newItem = Item.createItem(itemCreate);

            // 아이템 저장
            itemRepository.save(newItem);

            // 장바구니 셋팅
            CartCreate cartCreate = CartCreate.builder()
                    .itemId(newItem.getId())
                    .memberId(newMember.getId())
                    .itemCount(10)
                    .build();

            // when
            // 장바구니 생성
            cartService.createCart(cartCreate);

        }
        return newMember.getId();
    }

    // 새로운 아이템( 앨범 ) 생성 메서드
    private Item createNewItem(){
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
        return newItem;
    }

    // 새로운 회원생성 메서드
    private Member createNewMember(){
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
        return newMember;
    }
}