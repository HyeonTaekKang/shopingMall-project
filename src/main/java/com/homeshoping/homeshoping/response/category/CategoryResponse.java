package com.homeshoping.homeshoping.response.category;

import com.homeshoping.homeshoping.entity.ItemCategory.ItemCategory;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CategoryResponse {

    private String name; // 카테고리 이름

    @Builder
    public CategoryResponse(String name) {
        this.name = name;
    }


    // 엔티티 -> DTO
    public CategoryResponse(ItemCategory itemCategory){
        this.name = itemCategory.getName();
    }
}
