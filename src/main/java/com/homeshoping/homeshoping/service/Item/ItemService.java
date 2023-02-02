package com.homeshoping.homeshoping.service.Item;

import com.homeshoping.homeshoping.Exception.ItemNotFound;
import com.homeshoping.homeshoping.entity.Item.Item;
import com.homeshoping.homeshoping.repository.Item.ItemRepository;
import com.homeshoping.homeshoping.request.Item.ItemCreate;
import com.homeshoping.homeshoping.request.Item.ItemSearch;
import com.homeshoping.homeshoping.request.Item.ItemEdit;

import com.homeshoping.homeshoping.response.Item.ItemResponse;
import com.homeshoping.homeshoping.response.category.CategoryListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    // 상품 등록하기 ( create )
    @Transactional
    public Long itemRegistration(ItemCreate itemCreate){

        // 상품 DTO -> 상품 Entity
        Item item = Item.createItem(itemCreate);

        // 상품 저장
        itemRepository.save(item);
        return item.getId();
    }

    // 등록한 상품 하나만 가져오기 ( read one )
    public ItemResponse getRegistratedItem(Long itemId){

        // 상품 하나 찾아오고,
        Item item = itemRepository.getItem(itemId);

        // 엔티티 -> DTO로 변경해서 리턴
        return ItemResponse.createItemResponse(item);
    }

    // 등록한 상품 최신순으로 20개씩 가져오기 (read 20 )
    public List<ItemResponse> getLatestItems(ItemSearch itemSearch){

        // 등록한 상품 20개를 최신순으로 리스트형태로 가져오고,
        List<Item> items = itemRepository.getLatestItems(itemSearch); // [{},{}] -> Item

        // 리스트안에 있는 item 객체를 itemResponse객체로 변경한다음, 그 변경한 객체를 리스트 안에 담아서 리턴
        return items.stream().map(item -> ItemResponse.createItemResponse(item)).collect(Collectors.toList());
    }

    // ( 대분류 ) 카테고리별로 상품 가져오기
    public CategoryListResponse findAllItemByCategoryBranch(String categoryBranch){

        // 같은 categoryBranch인 Item들을 리스트에 담아옴
        List<Item> items = itemRepository.getAllItemByCategoryBranch(categoryBranch); //[{} , {}]

        // 엔티티 -> DTO
        // 리스트안에 있는 item 객체를 itemResponse객체로 변경한다음, 그 변경한 객체를 리스트 안에 담기.
        List<ItemResponse> itemDto = items.stream().map(item -> ItemResponse.createItemResponse(item)).collect(Collectors.toList());

        return new CategoryListResponse(itemDto);
    }

    // ( 소분류 ) 카테고리별로 상품 가져오기
    public CategoryListResponse findAllItemByCategoryName(String categoryName){

        // 같은 categoryName인 Item들을 리스트에 담아옴
        List<Item> items = itemRepository.getAllItemByCategoryName(categoryName); //[{} , {}]

        // 엔티티 -> DTO
        // 리스트안에 있는 item 객체를 itemResponse객체로 변경한다음, 그 변경한 객체를 리스트 안에 담아서 리턴
        List<ItemResponse> itemDto = items.stream().map(item -> ItemResponse.createItemResponse(item)).collect(Collectors.toList());

        return new CategoryListResponse(itemDto);
    }
//
//
    // 상품 변경하기
    @Transactional  // (변경할 상품id , 변경된 상품)
    public void editItem(Long itemId, ItemEdit itemEdit){

        // 변경할 상품을 상품id로 가져오기.
        Item item = itemRepository.getItem(itemId);

        // 상품 변경
        item.editItem(itemEdit);


    }
//
//    // 등록한 상품 삭제하기 ( delete )
//    @Transactional
//    public void deleteItem(Long id){
//
//        // 삭제할 게시물을 찾아와서
//        Item item = itemRepository.findById(id).orElseThrow(() -> new ItemNotFound());
//        // 삭제
//        itemRepository.delete(item);
//    }
}
