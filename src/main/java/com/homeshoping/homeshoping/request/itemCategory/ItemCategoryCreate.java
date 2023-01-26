package com.homeshoping.homeshoping.request.itemCategory;

import com.homeshoping.homeshoping.entity.ItemCategory.ItemCategory;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ItemCategoryCreate {

    private String branch; // 대분류

    private String name; // 소분류

    private ItemCategoryCreate parentItemCategory;

    public ItemCategory toEntity(ItemCategoryCreate itemCategoryCreate){
        return ItemCategory.builder()
                .branch(itemCategoryCreate.branch)
                .name(itemCategoryCreate.name)
                .build();
    }

    // DTO -> entity
    public ItemCategory toEntity(){
        return ItemCategory.createCategory(this);
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
