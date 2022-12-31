package com.homeshoping.homeshoping.controller.Item;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.homeshoping.homeshoping.entity.Item.Album;
import com.homeshoping.homeshoping.request.Item.ItemCreate;
import com.homeshoping.homeshoping.request.Item.ItemEdit;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ItemControllerErrorTest {

    @Autowired
    ItemController itemController;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("제품 등록 에러 테스트(앨범)  ---> 제목이 존재하지 않으면 에러정보를 담은 객체를 리턴한다.")
    public void createProductTest() throws Exception {

        Album album = Album.builder()
                .artist("에스파")
                .build();

        ItemCreate newProduct = ItemCreate.builder()
                .price("10000")
                .stockQuantity("1000")
                .date(LocalDateTime.now())
                .itemType("Album")
                .album(album)
                .build();

//        Product product = newProduct.toProductEntity();

        // 객체 -> JSON
        String productJson = objectMapper.writeValueAsString(newProduct);

        mockMvc.perform(post("/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productJson)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
                .andExpect(jsonPath("$.errorFieldNameAndErrorMessage.name").value("상품명을 입력해주세요!"))
                .andDo(print());
    }

    @Test
    @DisplayName("존재하지 않는 상품 한개 조회 --> ItemNotFound 에러객체를 리턴")
    public void getNotExistItemTest() throws Exception {

        mockMvc.perform(get("/item/{itemId}",1L)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNotFound())
                .andDo(print());

    }

    @Test
    @DisplayName("존재하지 않는 상품 변경 --> ItemNotFound 에러객체를 리턴")
    public void editNotExistItemTest() throws Exception {
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

        mockMvc.perform(patch("/item/{itemId}",10L)
                        .content(objectMapper.writeValueAsString(itemEdit))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNotFound())
                .andDo(print());

    }

    @Test
    @DisplayName("존재하지 않는 상품 삭제 --> ItemNotFound 에러객체를 리턴")
    public void deleteNotExistItemTest() throws Exception {

        mockMvc.perform(delete("/item/{itemId}",10L)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNotFound())
                .andDo(print());

    }




}
