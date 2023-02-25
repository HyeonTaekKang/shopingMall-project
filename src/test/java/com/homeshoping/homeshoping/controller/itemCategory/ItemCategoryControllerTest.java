package com.homeshoping.homeshoping.controller.itemCategory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.homeshoping.homeshoping.request.itemCategory.ItemCategoryCreate;
import com.homeshoping.homeshoping.request.itemCategory.ParentItemCategoryCreate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ItemCategoryControllerTest {

    @Autowired
    ItemCategoryController itemCategoryController;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("제품 카테고리 생성 테스트")
    @Rollback(value = false)
    @Transactional
    void createItemCategoryTest() throws Exception {

        // given
        // (대분류) 아이템 category 생성. ( 대분류(branch) = TOP , 소분류(name) = ROOT )
        ParentItemCategoryCreate parentItemCategory = createParentItemCategory();

        // (소분류) 아이템 category 에 위에서 만든 부모 아이템 카테고리 셋팅
        ItemCategoryCreate childItemCategory = ItemCategoryCreate.builder()
                .branch("TOP")
                .name("맨투맨")
                .parentItemCategory(parentItemCategory)
                .build();

        // 객체 -> JSON
        String newItemCategoryJson = objectMapper.writeValueAsString(childItemCategory);

        // expected
        // 검증
        mockMvc.perform(post("/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newItemCategoryJson)
                )
                .andExpect(status().isOk())
                .andDo(print());
    }







    // ( 대분류 ) ItemCategory 리턴
    private ParentItemCategoryCreate createParentItemCategory() {

        // 대분류 생성
        ParentItemCategoryCreate parentItemCategory = ParentItemCategoryCreate.builder()
                .branch("TOP")
                .name("ROOT")
                .build();

        return parentItemCategory;
    }
}