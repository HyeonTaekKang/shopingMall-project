package com.homeshoping.homeshoping.repository.itemCategory;

import com.homeshoping.homeshoping.entity.ItemCategory.ItemCategory;

import java.util.List;

public interface ItemCategoryRepositoryCustom {

    // 존재하는 부모 카테고리들 전부 가져오기
    List<ItemCategory> getAllParentCategories();

    // 부모 카테고리 아래에 있는 자식 카테고리들을 모두 가져오는 메서드
    List<ItemCategory> getAllChildCategories(String branch);
}
