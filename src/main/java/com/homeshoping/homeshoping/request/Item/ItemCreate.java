package com.homeshoping.homeshoping.request.Item;


import com.homeshoping.homeshoping.entity.Item.Album;
import com.homeshoping.homeshoping.entity.Item.Food;
import com.homeshoping.homeshoping.entity.Item.Item;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@ToString
@NoArgsConstructor
public class ItemCreate {

    private Long id;

    @NotBlank(message = "상품명을 입력해주세요!")
    private String name;     // 상품명

    @NotBlank(message = "상품의 가격을 입력해주세요!")
    private String price;       // 상품가격

    @NotBlank(message = "상품의 재고를 입력해주세요!")
    private String stockQuantity;   // 상품재고

    private LocalDateTime date; // 상품 등록 날짜

    @NotBlank(message = "상품의 타입을 알려주세요!")
    private String itemType;

    // Album
    private Album album;

    // Food
    private Food food;

    @Builder
    public ItemCreate(Long id, String name, String price, String stockQuantity, LocalDateTime date, String itemType, Album album, Food food) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.date = date;
        this.itemType = itemType;
        this.album = album;
        this.food = food;
    }


    // == 비지니스 메서드 ==
    // DTO를 엔티티로 변환하여 리턴 ( itemCreate -> Item )
//    public Item toItemEntity(ItemCreate itemCreate){
//        return Item.builder()
//                .name(itemCreate.getName())
//                .price(itemCreate.getPrice())
//                .stockQuantity(itemCreate.getStockQuantity())
//                .date(itemCreate.getDate())
//                .itemType(itemCreate.getItemType())
//                .album(itemCreate.getAlbum())
//                .food(itemCreate.getFood())
//                .build();
//    }

    // DTO를 엔티티로 변환하여 리턴
//    public Item toItemEntity() {
//
//
//        switch (this.itemType) {
//            case "Album":
//                return Album.builder()
//                        .id(id)
//                        .name(name)
//                        .price(price)
//                        .stockQuantity(stockQuantity)
//                        .date(LocalDateTime.now())
//                        .itemType(itemType)
//                        .artist(album.getArtist())
//                        .build();
//
//            case "Food":
//                return Food.builder()
//                        .id(id)
//                        .name(name)
//                        .price(price)
//                        .stockQuantity(stockQuantity)
//                        .date(LocalDateTime.now())
//                        .itemType(itemType)
//                        .madeIn(food.getMadeIn())
//                        .manufacturer(food.getManufacturer())
//                        .expirationDate(food.getExpirationDate())
//                        .build();
//        }
//        return null;
//    }

}
