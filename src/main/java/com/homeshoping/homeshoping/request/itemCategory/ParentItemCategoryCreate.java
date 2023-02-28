package com.homeshoping.homeshoping.request.itemCategory;

import com.homeshoping.homeshoping.entity.ItemCategory.ItemCategory;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ParentItemCategoryCreate {

    private String branch; // 대분류

    private String name; // 소분류

    // DTO -> entity ( ParentItemCategoryCreate -> itemCategory )
    public ItemCategory toEntity(ParentItemCategoryCreate parentItemCategoryCreate){
        return ItemCategory.builder()
                .branch(parentItemCategoryCreate.getBranch())
                .name(parentItemCategoryCreate.getName())
                .build();
    }

    @Builder
    public ParentItemCategoryCreate(String branch, String name) {
        this.branch = branch;
        this.name = name;
    }
}
