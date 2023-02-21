package com.homeshoping.homeshoping.controller.itemCategory;


import com.homeshoping.homeshoping.request.itemCategory.ItemCategoryCreate;
import com.homeshoping.homeshoping.service.itemCategory.ItemCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ItemCategoryController {

    private final ItemCategoryService itemCategoryService;

    @PostMapping("/category")
    public void saveCategory (@RequestBody ItemCategoryCreate itemCategoryCreate) {
        itemCategoryService.createCategory(itemCategoryCreate);
    }

//    @GetMapping("/category/{branch}")
//    public CategoryReturnDto getCategoryByBranch (@PathVariable String branch) {
//        return categoryService.getCategoryByBranch(branch);
//    }


}
