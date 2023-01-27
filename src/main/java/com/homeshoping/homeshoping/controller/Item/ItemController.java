package com.homeshoping.homeshoping.controller.Item;

import com.homeshoping.homeshoping.request.Item.ItemCreate;
import com.homeshoping.homeshoping.request.Item.ItemSearch;
import com.homeshoping.homeshoping.request.Item.ItemEdit;
import com.homeshoping.homeshoping.response.Item.ItemResponse;
import com.homeshoping.homeshoping.response.category.CategoryListResponse;
import com.homeshoping.homeshoping.service.Item.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    // 제품 등록
    @PostMapping("/item")
    public void createItem(@RequestBody @Valid ItemCreate itemCreate){
        itemService.itemRegistration(itemCreate);
    }

    // 제품 1개만 가져오기
    @GetMapping("/item/{itemId}")
    public ItemResponse readItem(@PathVariable Long itemId){
        return itemService.getRegistratedItem(itemId);
    }

    // 제품 최신순으로 20개 가져오기
//    @GetMapping("/items")
//    public List<ItemResponse> readItems(@ModelAttribute ItemSearch itemSearch){
//        return itemService.getAllRegistratedItem(itemSearch);
//    }
//
//    // (대분류) 카테고리별로 제품 가져오기
//    @GetMapping("/items/{categoryBranch}")
//    public CategoryListResponse readItemsByCategoryBranch(@PathVariable String categoryBranch){
//        return itemService.findAllItemByCategoryBranch(categoryBranch);
//    }
//
//    // (소분류) 카테고리별로 제품 가져오기
//    @GetMapping("/category/{categoryName}")
//    public CategoryListResponse readItemsByCategoryName(@PathVariable String categoryName){
//        return itemService.findAllItemByCategoryName(categoryName);
//    }
//
//    // 제품 변경하기
//    @PatchMapping("/item/{itemId}")
//    public void updateItem(@PathVariable Long itemId , @RequestBody @Valid ItemEdit itemEdit){
//        itemService.editItem(itemId , itemEdit);
//    }
//
//    // 제품 삭제하기
//    @DeleteMapping("/item/{itemId}")
//    public void deleteItem(@PathVariable Long itemId){
//        itemService.deleteItem(itemId);
//    }


}
