package com.homeshoping.homeshoping.entity.category;

import com.homeshoping.homeshoping.entity.Item.Item;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Category {

    @Id @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    private String branch;  // 대분류

    private String name; // 소분류

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn (name ="parent_cagegory_id")
    private Category parentCategory;

    @OneToMany (mappedBy = "parentCategory", cascade = CascadeType.ALL)
    private List<Category> subCategory = new ArrayList<>();

    // 연관관계 편의메서드
    public void setParentCategory(Category rootCategory) {
        parentCategory = rootCategory;

    }

    @Builder
    public Category(Long id, String branch, String name, Category parentCategory, List<Category> subCategory) {
        this.id = id;
        this.branch = branch;
        this.name = name;
        this.parentCategory = parentCategory;
        this.subCategory = subCategory;
    }
}
