package com.homeshoping.homeshoping.entity.Item;

import com.homeshoping.homeshoping.entity.Item.Category.Album;
import com.homeshoping.homeshoping.entity.Item.Category.Food;
import com.homeshoping.homeshoping.entity.category.Category;
import com.homeshoping.homeshoping.request.Item.ItemCreate;
import com.homeshoping.homeshoping.request.Item.ItemEdit;
import com.homeshoping.homeshoping.request.category.CategoryCreate;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;     // 상품 이름

    @Column(nullable = false)
    private int price;       // 상품 가격

    @Column(nullable = false)
    private int stockQuantity;  // 상품 재고

    @Lob
    @Column(nullable = false)
    private String itemInfo; // 상품 상세설명

    private String comment; // 판매자 코멘트

    @OneToOne(cascade = CascadeType.ALL , fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category; // 카테고리

    private LocalDateTime createdAt; // 상품 등록 날짜

    private LocalDateTime modifiedAt; // 상품 변경일

    private Size size;

    private
    @OneToOne(cascade = CascadeType.ALL , fetch = FetchType.LAZY)
    @JoinColumn(name="album_id")
    private Album album;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name="food_id")
    private Food food;

    @Builder
    public Item(Long id, String name, int price, int stockQuantity, Category category, LocalDateTime createdAt, LocalDateTime modifiedAt, Album album, Food food) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.category = category;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.album = album;
        this.food = food;
    }

    // Item 생성 메서드
    public static Item createItem(ItemCreate itemCreate){
        Item item = new Item();

        item.name = itemCreate.getName();
        item.price = itemCreate.getPrice();
        item.stockQuantity = itemCreate.getStockQuantity();
        item.category = itemCreate.getCategory();
        item.createdAt = itemCreate.getCreatedAt();
        item.modifiedAt = itemCreate.getModifiedAt();
        item.album = itemCreate.getAlbum();
        item.food = itemCreate.getFood();

        return item;
    }

    // 생성자 오버로딩 ( item 추가 )
    public Item(ItemCreate itemCreate) {
        this.name = itemCreate.getName();
        this.price = itemCreate.getPrice();
        this.stockQuantity = itemCreate.getStockQuantity();
        this.category= itemCreate.getCategory();
        this.createdAt = itemCreate.getCreatedAt();
        this.modifiedAt = itemCreate.getModifiedAt();
        this.album = itemCreate.getAlbum();
        this.food = itemCreate.getFood();
    }

    // 생성자 오버로딩 ( item 변경 )
    public Item(ItemEdit itemEdit){
        this.name = itemEdit.getName();
        this.price = itemEdit.getPrice();
        this.stockQuantity = itemEdit.getStockQuantity();
        this.category= itemEdit.getCategory();
        this.createdAt = itemEdit.getCreatedAt();
        this.modifiedAt = itemEdit.getModifiedAt();  // 수정날짜를 객체가 생성된 지금으로.
        this.album = itemEdit.getAlbum();
        this.food = itemEdit.getFood();
    }

    // === 비지니스 메서드 ===
    // 주문량 만큼 재고수량을 줄이는 메서드
    public void removeStockQuantity(int orderCount){
        this.stockQuantity = stockQuantity - orderCount;
    }


    // 상품 추가 메서드
//    public void create(ItemCreate itemCreate){
//        this.name = itemCreate.getName();
//        this.price = itemCreate.getPrice();
//        this.stockQuantity = itemCreate.getStockQuantity();
//        this.date = itemCreate.getDate();
//        this.itemType = itemCreate.getItemType();
//        this.album = itemCreate.getAlbum();
//        this.food = itemCreate.getFood();
//    }

    // === 비즈니스 메서드 ===
    // 상품 수정 메서드
//    public void update(ItemEdit itemEdit){
//        this.name = itemEdit.getName();
//        this.price = itemEdit.getPrice();
//        this.stockQuantity = itemEdit.getStockQuantity();
//        this.date = itemEdit.getDate();
//        this.itemType = itemEdit.getItemType();
//        this.album = itemEdit.getAlbum();
//        this.food = itemEdit.getFood();
//    }
}
