package com.homeshoping.homeshoping.entity.Item;
import com.homeshoping.homeshoping.entity.ItemCategory.ItemCategory;
import com.homeshoping.homeshoping.entity.itemInfo.ItemInfo;
import com.homeshoping.homeshoping.entity.itemOption.ItemOption;
import com.homeshoping.homeshoping.request.Item.ItemCreate;
import com.homeshoping.homeshoping.request.Item.ItemEdit;
import com.homeshoping.homeshoping.request.itemCategory.ItemCategoryCreate;
import com.homeshoping.homeshoping.request.itemInfo.ItemInfoCreate;
import com.homeshoping.homeshoping.request.itemOption.ItemOptionCreate;
import com.homeshoping.homeshoping.request.itemOption.ItemOptionEdit;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    @OneToOne(fetch = FetchType.LAZY , cascade = CascadeType.PERSIST)
    @JoinColumn(name = "itemInfo_id" )
    private ItemInfo itemInfo;  // 상품상세정보

    @OneToMany(mappedBy = "item", cascade = CascadeType.PERSIST)
    private List<ItemOption> itemOptions =  new ArrayList<>(); // 상품 옵션

    @OneToOne( fetch = FetchType.LAZY ,cascade = CascadeType.PERSIST )
    @JoinColumn(name = "itemCategory_id")
    private ItemCategory itemCategory; // 상품 카테고리

    @Column(nullable = false)
    private int stockQuantity;  // 상품 재고

    @OneToMany(mappedBy = "item" , cascade = CascadeType.REMOVE , orphanRemoval = true)
    private List<ItemFile> itemFileList = new ArrayList<>();

    private LocalDateTime createdAt; // 상품 등록 날짜

    private LocalDateTime modifiedAt; // 상품 변경일


    // Item 생성 메서드 ( request 받은 Create DTO를 entity로 변환하는 메서드 )
    public static Item createItem(ItemCreate itemCreate){
        Item item = new Item();

        item.name = itemCreate.getName();
        item.price = itemCreate.getPrice();

        // 상품 info 연관관계 맵핑
        item.itemInfo = ItemInfoCreate.toEntity(itemCreate.getItemInfoCreate());

        // 상품 option 연관관계 맵핑
        List<ItemOption> itemOptionList = itemCreate.getItemOptionCreateList().stream().map(itemOptionCreate -> itemOptionCreate.toEntity(itemOptionCreate)).collect(Collectors.toList());
        for(int i=0; i<itemOptionList.size(); i++){
            item.addItemOption(itemOptionList.get(i));
        }

        // 상품 category 연관관계 맵핑
        item.itemCategory = ItemCategoryCreate.toEntity(itemCreate.getItemCategoryCreate());

        item.stockQuantity = itemCreate.getStockQuantity();
        item.createdAt = LocalDateTime.now();
        item.modifiedAt = itemCreate.getModifiedAt();

        return item;
    }

    // Item 변경 메서드 ( request 받은 Create DTO를 entity로 변환하는 메서드 )
    public void editItem(ItemEdit itemEdit){
//        Item.builder()
//                .name(itemEdit.getName())
//                .price(itemEdit.getPrice())
//                .itemInfo(itemEdit.getEditedItemInfo().toEntity())
//                .itemOptions(itemEdit.getEditedItemOptionList().stream().map(itemOptionEdit -> itemOptionEdit.toEntity()).collect(Collectors.toList()))
//                .itemCategory(itemEdit.getEditedItemCategory().toEntity())
//                .stockQuantity(itemEdit.getStockQuantity())
//                .createdAt(itemEdit.getCreatedAt())
//                .modifiedAt(LocalDateTime.now())
//                .build();
        this.name = itemEdit.getName();
        this.price = itemEdit.getPrice();

//        // 상품 info 연관관계 맵핑
//        this.itemInfo = itemEdit.getEditedItemInfo().toEntity();
//
//        // 상품 option 연관관계 맵핑
//        this.itemOptions = itemEdit.getEditedItemOptionList().stream().map(itemOptionEdit -> itemOptionEdit.toEntity()).collect(Collectors.toList());
//
        // 상품 category 연관관계 맵핑
        this.itemCategory = itemEdit.getEditedItemCategory().toEntity();

        this.stockQuantity = itemEdit.getStockQuantity();
        this.createdAt = itemEdit.getCreatedAt();
        this.modifiedAt = LocalDateTime.now();
    }
    @Builder
    public Item(Long id, String name, int price, ItemInfo itemInfo, List<ItemOption> itemOptions, ItemCategory itemCategory, int stockQuantity, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.itemInfo = itemInfo;
        this.itemOptions = itemOptions;
        this.itemCategory = itemCategory;
        this.stockQuantity = stockQuantity;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }




    // 생성자 오버로딩 ( item 추가 )
    public Item(ItemCreate itemCreate) {
        this.name = itemCreate.getName();
        this.price = itemCreate.getPrice();
        this.stockQuantity = itemCreate.getStockQuantity();
//        this.category= itemCreate.getCategory();
        this.createdAt = itemCreate.getCreatedAt();
        this.modifiedAt = itemCreate.getModifiedAt();
    }

    // 생성자 오버로딩 ( item 변경 )
//    public Item(ItemEdit itemEdit){
//        this.name = itemEdit.getName();
//        this.price = itemEdit.getPrice();
//        this.stockQuantity = itemEdit.getStockQuantity();
////        this.category= itemEdit.getItemCategory();
//        this.createdAt = itemEdit.getCreatedAt();
//        this.modifiedAt = itemEdit.getModifiedAt();  // 수정날짜를 객체가 생성된 지금으로.
//    }

    // 연관관계 편의 메서드
    private void addItemOption(ItemOption itemOption){
        itemOptions.add(itemOption);
        if(itemOption.getItem() != this){
            itemOption.setItem(this);
        }
    }

    private void setItemInfo(ItemInfo itemInfo){
        this.itemInfo = itemInfo;
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
