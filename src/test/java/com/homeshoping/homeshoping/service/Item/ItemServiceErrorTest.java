package com.homeshoping.homeshoping.service.Item;

import com.homeshoping.homeshoping.Exception.ItemNotFound;
import com.homeshoping.homeshoping.entity.Item.Category.Album;
import com.homeshoping.homeshoping.entity.Item.Category.Food;
import com.homeshoping.homeshoping.repository.Item.ItemRepository;
import com.homeshoping.homeshoping.request.Item.ItemCreate;
import com.homeshoping.homeshoping.request.Item.ItemSearch;
import com.homeshoping.homeshoping.response.Item.ItemResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class ItemServiceErrorTest {

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    ItemService itemService;

    @BeforeEach
    void clean() {
        itemRepository.deleteAll();
    }

    @Test
    @DisplayName("등록한 상품을 찾지 못하면 ItemNotFound예외를 던진다.")
     void getInvalidRegistratedItemThrowsItemNotFound(){

        // given
        // 글을 하나 저장함.
        Food food = Food.builder()
                .madeIn("한국")
                .manufacturer("(주)하림")
                .expirationDate(LocalDateTime.parse("2007-12-03T10:15:30"))
                .build();

        ItemCreate newProduct = ItemCreate.builder()
                .name("김치말이국수")
                .price(10000)
                .stockQuantity(10000)
                .itemType("Food")
                .food(food)
                .build();

        itemService.itemRegistration(newProduct);

        // when + then
        // 존재하지 않는 글을 찾으려고 함. -> ItemNotFound 에러를 리턴
        assertThrows(ItemNotFound.class, ()->{
            itemService.getRegistratedItem(1L + 1L);
        });
    }

//    @Test
//    @DisplayName("20개의 아이템들을 가져오지 못하면 ItemNotFound예외를 던진다. ")
//    @Rollback(value = false)
//    void getAllInvalidRegistratedItemsThrowsItemNotFound(){
//
//        // given
//        ItemSearch itemSearch = new ItemSearch();
//
//        // when + then
//        // 존재하지 않는 글을 찾으려고 함.
//        assertThrows(ItemNotFound.class, ()->{
//            itemService.getAllRegistratedItem(itemSearch);
//        });
//    }

    @Test
    @DisplayName("페이징 테스트 - itemSearch객체의 멤버변수인 page는 0보다 작으면 안되고, 무조건 1 이상이여야 한다.")
    @Rollback(value = false)
    void pageMustBeGreaterThanOne(){

        // given
        // 제품 300개 등록
        for(int i = 1; i<=300; i++){
            Album album = Album.builder()
                    .artist("에스파")
                    .build();

            ItemCreate newProduct = ItemCreate.builder()
                    .name("savage" + i)
                    .price(10000)
                    .stockQuantity(10000)
                    .itemType("Album")
                    .album(album)
                    .build();

            itemService.itemRegistration(newProduct);
        }

        // 유효하지 않은 itemSearch 객체를 생성 ( page = 0 )
        ItemSearch itemSearch = ItemSearch.builder()
                .page(0)
                .size(20)
                .build();

        // when
        List<ItemResponse> items = itemService.getAllRegistratedItem(itemSearch);

        // then
        assertEquals("savage300",items.get(0).getName());
        assertEquals(20,items.size());
    }

    @Test
    @DisplayName("페이징 테스트 - itemSearch객체의 멤버변수인 size(limit)는 2000이하여야 된다.")
    @Rollback(value = false)
    void sizeMustBeLowerThan2000(){

        // given
        // 제품 300개 등록
        for(int i = 1; i<=3000; i++){
            Album album = Album.builder()
                    .artist("에스파")
                    .build();

            ItemCreate newProduct = ItemCreate.builder()
                    .name("savage" + i)
                    .price(10000)
                    .stockQuantity(10000)
                    .itemType("Album")
                    .album(album)
                    .build();

            itemService.itemRegistration(newProduct);
        }

        // 유효하지 않은 itemSearch 객체를 생성 ( page = 0 )
        ItemSearch itemSearch = ItemSearch.builder()
                .page(1)
                .size(3000)
                .build();

        // when
        List<ItemResponse> items = itemService.getAllRegistratedItem(itemSearch);

        // then
        assertEquals(2000,items.size());
    }




}
