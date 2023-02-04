package com.homeshoping.homeshoping.request.Item;

import com.homeshoping.homeshoping.entity.ItemCategory.ItemCategory;
import com.homeshoping.homeshoping.entity.itemInfo.ItemInfo;
import com.homeshoping.homeshoping.request.itemCategory.ItemCategoryCreate;
import com.homeshoping.homeshoping.request.itemCategory.ItemCategoryEdit;
import com.homeshoping.homeshoping.request.itemInfo.ItemInfoCreate;
import com.homeshoping.homeshoping.request.itemInfo.ItemInfoEdit;
import com.homeshoping.homeshoping.request.itemOption.ItemOptionCreate;
import com.homeshoping.homeshoping.request.itemOption.ItemOptionEdit;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class ItemEdit {

    private Long id;

    @NotBlank(message = "상품명을 입력해주세요!")
    private String name;     // 수정된 상품명

    @NotNull(message = "상품의 가격을 입력해주세요!")
    private int price;       // 수정된 상품가격

    private ItemInfoEdit editedItemInfo; // 수정된 상품 정보

    private List<ItemOptionEdit> editedItemOptionList = new ArrayList<>(); // 수정된 아이템 옵션

    private ItemCategoryEdit editedItemCategory; // 수정된 상품 카테고리

    @NotNull(message = "상품의 재고를 입력해주세요!")
    private int stockQuantity;   // 수정된 상품재고

    private LocalDateTime createdAt; // 수정된 상품 등록 날짜

    private LocalDateTime modifiedAt; // 수정된 상품 변경일

    @Builder
    public ItemEdit(Long id, String name, int price, ItemInfoEdit editedItemInfo, List<ItemOptionEdit> editedItemOptionList, ItemCategoryEdit editedItemCategory, int stockQuantity, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.editedItemInfo = editedItemInfo;
        this.editedItemOptionList = editedItemOptionList;
        this.editedItemCategory = editedItemCategory;
        this.stockQuantity = stockQuantity;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
    // DTO -> entity ( itemInfoEdit -> itemInfo )
//    public ItemInfo toEntity(ItemInfoEdit itemInfoEdit){
//        return ItemInfo.builder()
//                .manufactureCountry(itemInfoEdit.getManufactureCountry())
//                .material(itemInfoEdit.getMaterial())
//                .color(itemInfoEdit.getColor())
//                .size(itemInfoEdit.getSize())
//                .maker(itemInfoEdit.getMaker())
//                .washingMethod(itemInfoEdit.getWashingMethod())
//                .yearAndMonthofManufacture(itemInfoEdit.getYearAndMonthofManufacture())
//                .manager(itemInfoEdit.getManager())
//                .qualityStandard(itemInfoEdit.getQualityStandard())
//                .build();
//    }

//    // DTO -> entity ( itemCategoryEdit -> itemCategory )
//    public ItemCategory toEntity(ItemCategoryEdit smallItemCategoryEdit){
//        return ItemCategory.builder()
//                .branch(smallItemCategoryEdit.getBranch())
//                .name(smallItemCategoryEdit.getName())
//                .parentItemCategory(
//                        ItemCategory.builder()
//                        .branch(smallItemCategoryEdit.getParentItemCategory().getBranch())
//                        .name(smallItemCategoryEdit.getParentItemCategory().getName())
//                        .build())
//                .build();
//    }




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
