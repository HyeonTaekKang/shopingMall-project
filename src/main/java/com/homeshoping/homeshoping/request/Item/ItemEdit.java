package com.homeshoping.homeshoping.request.Item;

import com.homeshoping.homeshoping.entity.ItemCategory.ItemCategory;
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
    private int price;       // 가격

    @Column(nullable = false)
    private int stockQuantity;  // 재고

    @Column(nullable = false)
    private ItemCategory itemCategory;

    private LocalDateTime createdAt; // 상품 등록 날짜

    private LocalDateTime modifiedAt; // 상품 변경일


    @Builder
    public ItemEdit(Long id, String name, int price, int stockQuantity, ItemCategory itemCategory, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.itemCategory = itemCategory;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    // 생성자 오버로드 ( 엔티티 -> DTO )
//    public ItemEdit(Item i) {
//        this.id = i.getId();
//        this.name = i.getName();
//        this.price = i.getPrice();
//        this.stockQuantity = i.getStockQuantity();
//        this = i.getDate();
//        this.itemType = i.getItemType();
//        this.album = i.getAlbum();
//        this.food = i.getFood();
//    }
}
