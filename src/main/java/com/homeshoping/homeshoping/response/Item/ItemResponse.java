package com.homeshoping.homeshoping.response.Item;

import com.homeshoping.homeshoping.entity.Item.Album;
import com.homeshoping.homeshoping.entity.Item.Food;
import com.homeshoping.homeshoping.entity.Item.Item;
import com.homeshoping.homeshoping.request.Item.ItemCreate;
import lombok.Builder;
import lombok.Getter;


import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class ItemResponse {

    private final Long id; // 상품 id
    private final String name;     // 이름
    private final String price;       // 가격
    private final String stockQuantity;  // 재고
    private final LocalDateTime date; // 상품 등록 날짜
    private final String itemType; // 상품 타입

    // Album
    private Album album;

    // Food
    private Food food;

    @Builder
    public ItemResponse(Long id, String name, String price, String stockQuantity, LocalDateTime date, String itemType, Album album, Food food) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.date = date;
        this.itemType = itemType;
        this.album = album;
        this.food = food;
    }

    // 생성자 오버로드 ( 엔티티 -> DTO )
    public ItemResponse(Item i) {
        this.id = i.getId();
        this.name = i.getName();
        this.price = i.getPrice();
        this.stockQuantity = i.getStockQuantity();
        this.date = i.getDate();
        this.itemType = i.getItemType();
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
