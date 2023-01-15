package com.homeshoping.homeshoping.dto.category;

import com.homeshoping.homeshoping.entity.category.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * {
 *   "ROOT": {
 *         "categoryId": 1,
 *         "branch": "coupang",   // branch = 카테고리들의  연관관계를 표현
 *         "code": "ROOT",   // code = 카테고리의 깊이를 표현
 *         "name": "ROOT",   // name = 카테고리의 이름을 표현
 *         "parentCategoryName": "대분류",  // parentCategoryName = 부모카테고리의 이름을 표현 ( 이름이 "대분류"이면 최상위 카테고리 )
 *         "level": 0,  // depth에 따라 level별 분류하도록 구현
 *         "children": {
 *             "1": {
 *                 "categoryId": 2,
 *                 "branch": "coupang",
 *                 "code": "1",
 *                 "name": "clothes",
 *                 "parentCategoryName": "ROOT",
 *                 "level": 1,
 *                 "children": {}
 *             }
 *         }
 *     }
 *   }
 */

@Getter
@Setter
@NoArgsConstructor
public class CategoryDTO {

    private Long categoryId;
    private String branch;
    private String code;
    private String name;
    private String parentCategoryName;
    private Integer level;
    private Map<String, CategoryDTO> children;


    // entity -> DTO
    public CategoryDTO (Category entity) {
        this.categoryId = entity.getId();
        this.branch = entity.getBranch();
        this.code = entity.getCode();
        this.name = entity.getName();
        this.level = entity.getLevel();

        // parentCategoryName 셋팅
        if(entity.getParentCategory() == null) {  // 엔티티의 부모 카테고리가 null 이면 DTO의 parentCategoryName = "대분류"

            this.parentCategoryName = "대분류";

        } else {  // 엔티티의 부모 카테고리가 존재하면 DTO의 parentCategoryName 은 entity의 부모카테고리의 이름.

            this.parentCategoryName = entity.getParentCategory().getName();
        }

        // children 셋팅
        //  getSubCategory가 null 이 아니면 엔티티의 서브카테고리 리스트를 map형태로 변경.
        this.children = entity.getSubCategory() == null ? null :
                entity.getSubCategory().stream().collect(Collectors.toMap(
                        category-> category.getCode() ,category -> new CategoryDTO(category)
        ));
    }

    // DTO -> entity
    public Category toEntity () {
        return Category.builder()
                .branch(branch)
                .code(code)
                .level(level)
                .name(name)
                .build();
    }

    @Override
    public String toString() {
        return "CategoryDTO{" +
                "categoryId=" + categoryId +
                ", branch='" + branch + '\'' +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", parentCategoryName='" + parentCategoryName + '\'' +
                ", level=" + level +
                ", children=" + children +
                '}';
    }
}
