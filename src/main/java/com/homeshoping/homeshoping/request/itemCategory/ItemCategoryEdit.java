package com.homeshoping.homeshoping.request.itemCategory;

import com.homeshoping.homeshoping.entity.ItemCategory.ItemCategory;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ItemCategoryEdit {

    private String branch; // 대분류

    private String name; // 소분류

    private ItemCategoryEdit parentItemCategory;

    @Builder
    public ItemCategoryEdit(String branch, String name, ItemCategoryEdit parentItemCategory) {
        this.branch = branch;
        this.name = name;
        this.parentItemCategory = parentItemCategory;
    }

    // DTO -> entity ( itemCategoryEdit -> itemCategory )
    public ItemCategory toEntity(){
        return ItemCategory.builder()
                .branch(branch)
                .name(getName())
                .parentItemCategory(
                        ItemCategory.builder()
                                .branch(parentItemCategory.getBranch())
                                .name(parentItemCategory.getName())
                                .build())
                .build();
    }
}
