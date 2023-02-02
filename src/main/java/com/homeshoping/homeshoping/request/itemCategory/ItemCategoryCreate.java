package com.homeshoping.homeshoping.request.itemCategory;

import com.homeshoping.homeshoping.entity.ItemCategory.ItemCategory;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ItemCategoryCreate {

    private String branch; // 대분류

    private String name; // 소분류

    private ItemCategoryCreate parentItemCategory;

    // DTO -> entity ( itemCategoryCreate -> itemCategory )
    public static ItemCategory toEntity(ItemCategoryCreate smallItemCategoryCreate){
        return ItemCategory.builder()
                .branch(smallItemCategoryCreate.getBranch())
                .name(smallItemCategoryCreate.getName())
                .parentItemCategory(
                        ItemCategory.builder()
                                .branch(smallItemCategoryCreate.getParentItemCategory().getBranch())
                                .name(smallItemCategoryCreate.getParentItemCategory().getName())
                                .build())
                .build();
    }

    @Builder
    public ItemCategoryCreate(String branch, String name, ItemCategoryCreate parentItemCategory) {
        this.branch = branch;
        this.name = name;
        this.parentItemCategory = parentItemCategory;
    }

    @Override
    public String toString() {
        return "ItemCategoryCreate{" +
                "branch='" + branch + '\'' +
                ", name='" + name + '\'' +
                ", parentItemCategory=" + parentItemCategory +
                '}';
    }
}
