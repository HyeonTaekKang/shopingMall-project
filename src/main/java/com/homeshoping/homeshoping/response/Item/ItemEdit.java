package com.homeshoping.homeshoping.response.Item;

import com.homeshoping.homeshoping.entity.Item.Album;
import com.homeshoping.homeshoping.entity.Item.Food;
import com.homeshoping.homeshoping.entity.Item.Item;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ItemEdit {

    private Long id; // 상품 id

    @Column(nullable = false, length = 50)
    private String name;     // 이름

    @Column(nullable = false)
    private String price;       // 가격

    @Column(nullable = false)
    private String stockQuantity;  // 재고

    private LocalDateTime date; // 상품 등록 날짜

    @Column(nullable = false)
    private String itemType; // 상품 타입

    // Album
    private Album album;

    // Food
    private Food food;

    @Builder
    public ItemEdit(Long id, String name, String price, String stockQuantity, LocalDateTime date, String itemType, Album album, Food food) {
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
//    public ItemEdit(Item i) {
//        this.id = i.getId();
//        this.name = i.getName();
//        this.price = i.getPrice();
//        this.stockQuantity = i.getStockQuantity();
//        this.date = i.getDate();
//        this.itemType = i.getItemType();
//        this.album = i.getAlbum();
//        this.food = i.getFood();
//    }
}
