package com.homeshoping.homeshoping.repository.itemCategory;

import com.homeshoping.homeshoping.entity.ItemCategory.ItemCategory;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;


import static com.homeshoping.homeshoping.entity.ItemCategory.QItemCategory.*;

@RequiredArgsConstructor
public class ItemCategoryRepositoryImpl implements ItemCategoryRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public List<ItemCategory> getAllParentCategories() {
        return jpaQueryFactory.selectFrom(itemCategory)
                .where(itemCategory.name.eq("ROOT"))
                .fetch();
    }

    // 부모 카테고리 아래에 있는 자식 카테고리들을 모두 가져오는 메서드
    @Override
    public List<ItemCategory> getAllChildCategories(String branch) {
        return jpaQueryFactory.selectFrom(itemCategory)
                .where(itemCategory.branch.eq(branch))
                .fetch();
    }
}
