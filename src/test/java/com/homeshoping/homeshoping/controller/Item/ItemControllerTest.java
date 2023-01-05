package com.homeshoping.homeshoping.controller.Item;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.homeshoping.homeshoping.entity.Item.Album;
import com.homeshoping.homeshoping.entity.Item.Item;
import com.homeshoping.homeshoping.repository.Item.ItemRepository;
import com.homeshoping.homeshoping.request.Item.ItemCreate;
import com.homeshoping.homeshoping.request.Item.ItemSearch;
import com.homeshoping.homeshoping.request.Item.ItemEdit;
import com.homeshoping.homeshoping.response.Item.ItemResponse;
import com.homeshoping.homeshoping.service.Item.ItemService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
class ItemControllerTest {

    @Autowired
    ItemController itemController;

    @Autowired
    ItemService itemService;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    // 상품 등록 메서드 ( with album )
    private Item saveItemWithAlbum(){
        Album album = Album.builder()
                .artist("에스파")
                .build();

        Item item = Item.builder()
                .name("savage")
                .price(10000)
                .stockQuantity(10000)
                .date(LocalDateTime.now())
                .itemType("Album")
                .album(album)
                .build();

        itemRepository.save(item);

        return item;
    }

    @Test
    @DisplayName("제품 등록 테스트(앨범)")
    void createProductTest() throws Exception {

        // given
        // 상품 등록
        Album album = Album.builder()
                .artist("에스파")
                .build();

        ItemCreate newProduct = ItemCreate.builder()
                .name("savage")
                .price(10000)
                .stockQuantity(10000)
                .date(LocalDateTime.now())
                .itemType("Album")
                .album(album)
                .build();

        // when
        // 객체 -> JSON
        String productJson = objectMapper.writeValueAsString(newProduct);

        // then
        // 검증
        mockMvc.perform(post("/item")
                .contentType(MediaType.APPLICATION_JSON)
                .content(productJson)
        )
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("등록한 제품 최신순으로 20개 가져오기 테스트")
    void readItemsTest() throws Exception {

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
                    .date(LocalDateTime.now())
                    .itemType("Album")
                    .album(album)
                    .build();

            itemService.itemRegistration(newProduct);
        }

        // item 리스트 가져오기 ( 최신순으로 20개 )
        ItemSearch itemSearch = new ItemSearch();  // page = 0 , size = 20
        List<ItemResponse> items = itemService.getAllRegistratedItem(itemSearch);


        // expected
        mockMvc.perform(get("/items")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("savage300"))
                .andExpect(jsonPath("$[0].price").value(10000))
                .andExpect(jsonPath("$[0].stockQuantity").value(10000))
                .andExpect(jsonPath("$[0].itemType").value("Album"))
                .andDo(print());
    }

    @Test
    @DisplayName("제품 변경 테스트(앨범)")
    void updateItemTest() throws Exception {

        // given
        // 제품 한개 등록 ( 앨범 )
        Item item = saveItemWithAlbum();

        // 변경된 제품
        Album editedAlbum = Album.builder()
                .artist("뉴진스")
                .build();

        ItemEdit itemEdit = ItemEdit.builder()
                .name("hypeboy")
                .price(10000)
                .stockQuantity(10000)
                .date(LocalDateTime.now())
                .itemType("Album")
                .album(editedAlbum)
                .build();

        // 변경된 제품 객체를 JSON형태로 변경
        String editedItem = objectMapper.writeValueAsString(itemEdit);

        // expected
        // 제품 삭제
        mockMvc.perform(patch("/item/{itemId}",item.getId())
                        .content(editedItem)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("제품 삭제 테스트(앨범)")
    void deleteItemsTest() throws Exception {

        // given
        // 제품 한개 등록 ( 앨범 )
        Item item = saveItemWithAlbum();

        // expected
        // 제품 삭제
        mockMvc.perform(delete("/item/{itemId}",item.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(print());
    }



































}