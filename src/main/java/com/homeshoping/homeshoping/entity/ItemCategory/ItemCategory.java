package com.homeshoping.homeshoping.entity.ItemCategory;

import com.homeshoping.homeshoping.request.itemCategory.ItemCategoryCreate;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class ItemCategory {

    @Id @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name = "itemCategory_id")
    private Long id;

    private String branch;  // 대분류

    private String name; // 소분류

    @ManyToOne (fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn (name ="parent_cagegory_id")
    private ItemCategory parentItemCategory;

    @OneToMany (mappedBy = "parentItemCategory", cascade = CascadeType.ALL)
    private List<ItemCategory> subItemCategory = new ArrayList<>();

    // 연관관계 편의메서드
    public void setParentItemCategory(ItemCategory rootItemCategory) {
        parentItemCategory = rootItemCategory;

    }

    public static ItemCategory createCategory(ItemCategoryCreate itemCategoryCreate){
        ItemCategory itemCategory = new ItemCategory();

        itemCategory.branch = itemCategoryCreate.getBranch();
        itemCategory.name = itemCategoryCreate.getName();

        return itemCategory;
    }

    @Builder
    public ItemCategory(Long id, String branch, String name, ItemCategory parentItemCategory, List<ItemCategory> subItemCategory) {
        this.id = id;
        this.branch = branch;
        this.name = name;
        this.parentItemCategory = parentItemCategory;
        this.subItemCategory = subItemCategory;
    }
}
