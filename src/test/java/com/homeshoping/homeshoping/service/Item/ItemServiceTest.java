package com.homeshoping.homeshoping.service.Item;

import com.homeshoping.homeshoping.Exception.ItemNotFound;
import com.homeshoping.homeshoping.entity.Item.Album;
import com.homeshoping.homeshoping.entity.Item.Food;
import com.homeshoping.homeshoping.entity.Item.Item;
import com.homeshoping.homeshoping.repository.Item.ItemRepository;
import com.homeshoping.homeshoping.request.Item.ItemCreate;
import com.homeshoping.homeshoping.request.Item.ItemSearch;
import com.homeshoping.homeshoping.response.Item.ItemEdit;
import com.homeshoping.homeshoping.response.Item.ItemResponse;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ItemServiceTest {

    @Autowired
    ItemService itemService;

    @Autowired
    ItemRepository itemRepository;

    @PersistenceContext
    EntityManager em;

    @BeforeEach
    void clean() {
        itemRepository.deleteAll();
    }

    @Test
    @DisplayName("상품 등록 테스트(앨범)")
    @Rollback(value = false)
    void itemRegistrationTest1(){

        // then
        // 상품 생성
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

        // when
        // 상품 등록
        itemService.itemRegistration(itemCreate);

        // then
        // 상품 검증
        assertEquals("savage",itemCreate.getName());
        assertEquals("10000",itemCreate.getPrice());
        assertEquals("1000",itemCreate.getStockQuantity());
        assertEquals("Album",itemCreate.getItemType());
        assertEquals("에스파",itemCreate.getAlbum().getArtist());
    }


    @Test
    @DisplayName("상품 등록 테스트(음식)")
    @Rollback(value = false)
    void itemRegistrationTest2(){

        Food food = Food.builder()
                .madeIn("한국")
                .manufacturer("(주)하림")
                .expirationDate(LocalDateTime.parse("2007-12-03T10:15:30"))
                .build();

        ItemCreate itemCreate = ItemCreate.builder()
                .name("김치말이국수")
                .price("10000")
                .stockQuantity("1000")
                .date(LocalDateTime.now())
                .itemType("Food")
                .food(food)
                .build();

        itemService.itemRegistration(itemCreate);

        // then
        // 상품 검증
        assertEquals("김치말이국수",itemCreate.getName());
        assertEquals("10000",itemCreate.getPrice());
        assertEquals("1000",itemCreate.getStockQuantity());
        assertEquals("Food",itemCreate.getItemType());
        assertEquals("한국",itemCreate.getFood().getMadeIn());
        assertEquals("(주)하림",itemCreate.getFood().getManufacturer());
    }

    @Test
    @DisplayName("등록된 제품 하나 가져오기 테스트(앨범)")
    @Rollback(value = false)
    void getRegistratedItemTest(){

        // given
        // 제품 등록
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

        // when
        // 제품 가져오기
        ItemResponse foundedItem = itemService.getRegistratedItem(1L);

        // then
        // 가져온 제품 검증
        assertEquals("savage",foundedItem.getName());
        assertEquals("10000",foundedItem.getPrice());
        assertEquals("1000",foundedItem.getStockQuantity());
        assertEquals("Album",foundedItem.getItemType());
    }

    @Test
    @DisplayName("등록한 상품을 최신순으로 20개 가져오기 테스트(제일 처음 페이지)")
    @Rollback(value = false)
    void getAllRegistratedItemFirstPageTest(){

        // given
        // 제품 300개 등록
        for(int i = 1; i<=300; i++){
            Album album = Album.builder()
                    .artist("에스파")
                    .build();

            ItemCreate itemCreate = ItemCreate.builder()
                    .name("savage" + i)
                    .price("10000")
                    .stockQuantity("1000")
                    .date(LocalDateTime.now())
                    .itemType("Album")
                    .album(album)
                    .build();

            itemService.itemRegistration(itemCreate);
        }

        ItemSearch itemSearch = new ItemSearch();

        // when
        // 제~일 최신제품 20개 가져오기
        List<ItemResponse> items = itemService.getAllRegistratedItem(itemSearch); // [{},{},{}]

        // then
        // 가져온 제품 검증
        assertEquals("savage300",items.get(0).getName());
        assertEquals(20,items.size());
    }

    @Test
    @DisplayName("등록한 상품을 최신순으로 20개 가져오기 테스트(두번쨰 페이지)")
    @Rollback(value = false)
    void getAllRegistratedItemSecondPageTest(){

        // given
        // 제품 300개 등록
        for(int i = 1; i<=300; i++){
            Album album = Album.builder()
                    .artist("에스파")
                    .build();

            ItemCreate itemCreate = ItemCreate.builder()
                    .name("savage" + i)
                    .price("10000")
                    .stockQuantity("1000")
                    .date(LocalDateTime.now())
                    .itemType("Album")
                    .album(album)
                    .build();

            itemService.itemRegistration(itemCreate);
        }

        // 2번째 페이지
        ItemSearch itemSearch = ItemSearch.builder()
                .size(20)
                .page(2)
                .build();

        // when
        // 2번째 페이지의 상품 20개 가져오기
        List<ItemResponse> items = itemService.getAllRegistratedItem(itemSearch); // [{},{},{}]

        // then
        // 가져온 제품 검증
        assertEquals("savage280",items.get(0).getName());
        assertEquals(20,items.size());
    }

    @Test
    @DisplayName("상품 변경 테스트(앨범)")
    void editItemTest(){

        // given
        // 제품 한개 등록 ( 앨범 )
        Album album = Album.builder()
                .artist("에스파")
                .build();

        Item item = Item.builder()
                .name("savage")
                .price("10000")
                .stockQuantity("1000")
                .date(LocalDateTime.now())
                .itemType("Album")
                .album(album)
                .build();

        itemRepository.save(item);

        // 변경된 제품
        Album editedAlbum = Album.builder()
                .artist("뉴진스")
                .build();

        ItemEdit itemEdit = ItemEdit.builder()
                .name("hypeboy")
                .price("10000")
                .stockQuantity("1000")
                .date(LocalDateTime.now())
                .itemType("Album")
                .album(editedAlbum)
                .build();

        // when
        // 제품 변경
        itemService.editItem(item.getId(),itemEdit);

        // then
        // 변경전 제품의 id 로 제품을 찾아와서
        Item updatedItem = itemRepository.findById(item.getId()).orElseThrow(ItemNotFound::new);

        // 변경이 제대로 되었는지 검증
        assertEquals("hypeboy",updatedItem.getName());
    }

    @Test
    @DisplayName("상품 삭제 테스트(앨범)")
    void deleteItemTest(){

        // given
        // 제품 한개 등록 ( 앨범 )
        Album album = Album.builder()
                .artist("에스파")
                .build();

        Item item = Item.builder()
                .name("savage")
                .price("10000")
                .stockQuantity("1000")
                .date(LocalDateTime.now())
                .itemType("Album")
                .album(album)
                .build();

        itemRepository.save(item);

        // when
        // 제품 삭제
        itemService.deleteItem(item.getId()); // [{},{},{}]

        // then
        assertEquals(0,itemRepository.count());
    }
}