package com.homeshoping.homeshoping.controller.itemCategory;


import com.homeshoping.homeshoping.request.itemCategory.ItemCategoryCreate;
import com.homeshoping.homeshoping.service.itemCategory.ItemCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ItemCategoryController {

    private final ItemCategoryService itemCategoryService;

    // 상품 카테고리 생성 ( 대분류 + 소분류 )
    @PostMapping("/category")
    public void createItemCategory (@RequestBody ItemCategoryCreate itemCategoryCreate) {
        itemCategoryService.createCategory(itemCategoryCreate);
    }

//    @GetMapping("/category/{branch}")
//    public CategoryReturnDto getCategoryByBranch (@PathVariable String branch) {
//        return categoryService.getCategoryByBranch(branch);
//    }


}
