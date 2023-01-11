package com.homeshoping.homeshoping.response.Item;

import com.homeshoping.homeshoping.entity.Item.Category.Album;
import com.homeshoping.homeshoping.entity.Item.Category.Food;
import com.homeshoping.homeshoping.entity.Item.Item;
import lombok.Builder;
import lombok.Getter;


import java.time.LocalDateTime;

@Getter
public class ItemResponse {

    private final Long id; // 상품 id
    private final String name;     // 이름
    private final int price;       // 가격
    private final int stockQuantity;  // 재고
    private final String itemType; // 상품 타입
    private final LocalDateTime createdAt; // 상품 등록 날짜
    private final LocalDateTime modifiedAt; // 상품 변경일

    // Album
    private Album album;

    // Food
    private Food food;

    @Builder
    public ItemResponse(Long id, String name, int price, int stockQuantity, String itemType, LocalDateTime createdAt, LocalDateTime modifiedAt, Album album, Food food) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.itemType = itemType;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.album = album;
        this.food = food;
    }

    // 생성자 오버로딩 ( 엔티티 -> DTO )
    public ItemResponse(Item i) {
        this.id = i.getId();
        this.name = i.getName();
        this.price = i.getPrice();
        this.stockQuantity = i.getStockQuantity();
        this.itemType = i.getItemType();
        this.createdAt = i.getCreatedAt();
        this.modifiedAt = i.getModifiedAt();
        this.album = i.getAlbum();
        this.food = i.getFood();
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
