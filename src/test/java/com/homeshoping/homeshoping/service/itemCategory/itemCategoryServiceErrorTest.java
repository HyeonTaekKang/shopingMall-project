package com.homeshoping.homeshoping.service.itemCategory;

import com.homeshoping.homeshoping.Exception.CategoryAlreadyExists;
import com.homeshoping.homeshoping.request.itemCategory.ItemCategoryCreate;
import com.homeshoping.homeshoping.request.itemCategory.ParentItemCategoryCreate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class itemCategoryServiceErrorTest {

    @Autowired
    ItemCategoryService itemCategoryService;

    @Test
    @Rollback(value = false)
    @DisplayName("이미 존재하는 대분류를 또 생성하려고 한다면 CategoryAlreadyExists() 에러를 리턴한다.")
    void CategoryAlreadyExistsTest() {
        // 첫번쨰 (대분류) 생성
        ParentItemCategoryCreate parentItemCategory1 = ParentItemCategoryCreate.builder()
                .branch("TOP")
                .name("ROOT")
                .build();

        // 첫번째 (소분류) 생성.
        ItemCategoryCreate childCategory1 = ItemCategoryCreate.builder()
                .branch("TOP")
                .name("맨투맨")
                .parentItemCategory(parentItemCategory1)
                .build();

        // 첫번째 카테고리 생성.
        itemCategoryService.createCategory(childCategory1);

        // 두번쨰 (대분류) 생성.
        ParentItemCategoryCreate parentItemCategory2 = ParentItemCategoryCreate.builder()
                .branch("TOP")
                .name("ROOT")
                .build();

        // 두번쨰 (소분류) 생성.
        ItemCategoryCreate childCategory2 = ItemCategoryCreate.builder()
                .branch("TOP")
                .name("청바지")
                .parentItemCategory(parentItemCategory2)
                .build();

        // 두번째 소분류 카테고리를 생성하려고 하면 CategoryAlreadyExists 에러가 던져져야한다
        //  ===> 그 이유는 childCategory2 가 childCategory1 과 대분류가 같기 때문이다.
        Assertions.assertThrows(CategoryAlreadyExists.class, () -> {
            itemCategoryService.createCategory(childCategory2);
        });
    }
}
