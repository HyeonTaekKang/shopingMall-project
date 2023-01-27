package com.homeshoping.homeshoping.response.ItemCategory;

import com.homeshoping.homeshoping.entity.ItemCategory.ItemCategory;
import com.homeshoping.homeshoping.entity.itemInfo.ItemInfo;
import com.homeshoping.homeshoping.response.ItemInfo.ItemInfoResponse;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ItemCategoryResponse {

    private String branch; // 대분류

    private String name; // 소분류

    @Builder
    public ItemCategoryResponse(String branch, String name) {
        this.branch = branch;
        this.name = name;
    }

    // 엔티티 -> DTO
    public static ItemCategoryResponse toDTO(ItemCategory itemCategory){
        return ItemCategoryResponse.builder()
                .branch(itemCategory.getBranch())
                .name(itemCategory.getName())
                .build();
    }
}
