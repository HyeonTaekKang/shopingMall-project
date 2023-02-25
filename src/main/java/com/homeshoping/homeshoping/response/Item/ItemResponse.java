package com.homeshoping.homeshoping.response.Item;

import com.homeshoping.homeshoping.entity.Item.Item;
import com.homeshoping.homeshoping.entity.itemOption.ItemOption;
import com.homeshoping.homeshoping.repository.Item.ItemRepository;
import com.homeshoping.homeshoping.response.ItemCategory.ItemCategoryResponse;
import com.homeshoping.homeshoping.response.ItemInfo.ItemInfoResponse;
import com.homeshoping.homeshoping.response.ItemOption.ItemOptionResponse;
import com.homeshoping.homeshoping.response.category.CategoryResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class ItemResponse {

    private Long id; // 상품 id

    private String name;     // 이름

    private int price;       // 가격

    private ItemInfoResponse itemInfoResponse; // 상품상세정보

    private List<ItemOptionResponse> itemOptionResponseList = new ArrayList<>(); // 상품 옵션

    private ItemCategoryResponse itemCategoryResponse; // 상품 카테고리

    private int stockQuantity;  // 재고


    // entity -> DTO
    public static ItemResponse createItemResponse(Item item){
        ItemResponse itemResponse = new ItemResponse();

        itemResponse.id = item.getId();
        itemResponse.name = item.getName();
        itemResponse.price = item.getPrice();

        itemResponse.itemInfoResponse = ItemInfoResponse.toDTO(item.getItemInfo());

        itemResponse.itemOptionResponseList = item.getItemOptions().stream()
                .map(itemOption -> new ItemOptionResponse(itemOption)).collect(Collectors.toList());

        itemResponse.itemCategoryResponse = ItemCategoryResponse.toDTO(item.getItemCategory());

        itemResponse.stockQuantity = item.getStockQuantity();

        return itemResponse;
    }



    @Override
    public String toString() {
        return "ItemResponse{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", itemInfoResponse=" + itemInfoResponse +
                ", itemOptionResponseList=" + itemOptionResponseList +
                ", itemCategoryResponse=" + itemCategoryResponse +
                ", stockQuantity=" + stockQuantity +
                '}';
    }

    // == 비지니스 메서드 ==
    // 엔티티를 DTO로 변환하여 리턴 ( Item -> ItemResponse )
//    public ItemResponse toItemResponse(Item item){
//        return ItemResponse.builder()
//                .id(item.getId())
//                .name(item.getName())
//                .price(item.getPrice())
//                .stockQuantity(item.getStockQuantity())
//                .date(item.getDate())
//                .itemType(item.getItemType())
//                .album(item.getAlbum())
//                .food(item.getFood())
//                .build();
//    }

}
