package com.homeshoping.homeshoping.response.category;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CategoryListResponse<T> {

        private T data;

//    private Long categoryId;
//    private String branch;
//    private String code;
//    private String name;
//    private String parentCategoryName;
//    private Integer level;
//    private Map<String, CategoryDTO> children;
//
//    @Builder
//    public CategoryResponse(Long categoryId, String branch, String code, String name, String parentCategoryName, Integer level, Map<String, CategoryDTO> children) {
//        this.categoryId = categoryId;
//        this.branch = branch;
//        this.code = code;
//        this.name = name;
//        this.parentCategoryName = parentCategoryName;
//        this.level = level;
//        this.children = children;
//    }

        @Override
        public String toString() {
                return "CategoryListResponse{" +
                        "data=" + data +
                        '}';
        }
}
