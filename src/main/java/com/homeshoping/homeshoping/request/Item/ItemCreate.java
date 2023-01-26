package com.homeshoping.homeshoping.request.Item;

import com.homeshoping.homeshoping.entity.ItemCategory.ItemCategory;
import com.homeshoping.homeshoping.entity.itemInfo.ItemInfo;
import com.homeshoping.homeshoping.request.itemCategory.ItemCategoryCreate;
import com.homeshoping.homeshoping.request.itemInfo.ItemInfoCreate;
import com.homeshoping.homeshoping.request.itemOption.ItemOptionCreate;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
@NoArgsConstructor
public class ItemCreate {

    private Long id;

    @NotBlank(message = "상품명을 입력해주세요!")
    private String name;     // 상품명

    @NotNull(message = "상품의 가격을 입력해주세요!")
    private int price;       // 상품가격

    private ItemInfoCreate itemInfoCreate; // 상품 정보

    private List<ItemOptionCreate> itemOptionCreateList = new ArrayList<>(); // 아이템 옵션

    private ItemCategoryCreate itemCategoryCreate; // 상품 카테고리

    @NotNull(message = "상품의 재고를 입력해주세요!")
    private int stockQuantity;   // 상품재고

    private LocalDateTime createdAt; // 상품 등록 날짜

    private LocalDateTime modifiedAt; // 상품 변경일

    @Builder
    public ItemCreate(Long id, String name, int price, ItemInfoCreate itemInfoCreate, List<ItemOptionCreate> itemOptionCreateList, ItemCategoryCreate itemCategoryCreate, int stockQuantity, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.itemInfoCreate = itemInfoCreate;
        this.itemOptionCreateList = itemOptionCreateList;
        this.itemCategoryCreate = itemCategoryCreate;
        this.stockQuantity = stockQuantity;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    // DTO -> entity
    public ItemInfo toEntity(ItemInfoCreate itemInfoCreate){
        return ItemInfo.builder()
                .manufactureCountry(itemInfoCreate.getManufactureCountry())
                .material(itemInfoCreate.getMaterial())
                .color(itemInfoCreate.getColor())
                .size(itemInfoCreate.getSize())
                .maker(itemInfoCreate.getMaker())
                .washingMethod(itemInfoCreate.getWashingMethod())
                .yearAndMonthofManufacture(itemInfoCreate.getYearAndMonthofManufacture())
                .manager(itemInfoCreate.getManager())
                .qualityStandard(itemInfoCreate.getQualityStandard())
                .build();
    }

    public ItemCategory toEntity(ItemCategoryCreate smallItemCategoryCreate){
        return ItemCategory.builder()
                .branch(smallItemCategoryCreate.getBranch())
                .name(smallItemCategoryCreate.getName())
                .parentItemCategory(ItemCategory.builder()
                        .branch(smallItemCategoryCreate.getParentItemCategory().getBranch())
                        .name(smallItemCategoryCreate.getParentItemCategory().getName())
                        .build())
                .build();
    }

    @Override
    public String toString() {
        return "ItemCreate{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", itemInfoCreate=" + itemInfoCreate.toString() +
                ", itemOptionCreateList=" + itemOptionCreateList.toString() +
                ", itemCategoryCreate=" + itemCategoryCreate.toString() +
                ", stockQuantity=" + stockQuantity +
                ", createdAt=" + createdAt +
                ", modifiedAt=" + modifiedAt +
                '}';
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
