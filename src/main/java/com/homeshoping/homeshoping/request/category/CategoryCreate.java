package com.homeshoping.homeshoping.request.category;

import com.homeshoping.homeshoping.entity.category.Category;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
public class CategoryCreate{

    private String branch; // 카테고리간의 연관관계를 위한 branch 필드

    private String name; // 카테고리 이름.


    public Category toEntity(CategoryCreate categoryCreate){
        return Category.builder()
                .branch(categoryCreate.branch)
                .name(categoryCreate.name)
                .build();
    }

    @Builder
    public CategoryCreate(String branch, String name) {
        this.branch = branch;
        this.name = name;
    }
}
