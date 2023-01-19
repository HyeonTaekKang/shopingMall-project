package com.homeshoping.homeshoping.repository.Item;

import com.homeshoping.homeshoping.entity.Item.Item;

import com.homeshoping.homeshoping.entity.category.QCategory;
import com.homeshoping.homeshoping.request.Item.ItemCreate;
import com.homeshoping.homeshoping.request.Item.ItemSearch;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

import static com.homeshoping.homeshoping.entity.Item.QItem.*;
import static com.homeshoping.homeshoping.entity.category.QCategory.*;

@RequiredArgsConstructor
public class ItemRepositoryImpl implements ItemRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    // 등록한 상품 최신순으로 20개씩 가져오기
    @Override
    public List<Item> getAllRegistratedItemWithPaging(ItemSearch itemSearch) {
        return jpaQueryFactory
                .selectFrom(item)
                .offset(itemSearch.getOffset())
                .limit(itemSearch.getLimit())
                .orderBy(item.createdAt.desc())
                .fetch();
    }

    // 대분류 카테고리별로 상품 가져오기
    @Override
    public List<Item> getAllItemByCategoryBranch(String categoryBranch){
        return jpaQueryFactory
                .selectFrom(item)
                .join(item.category, category).fetchJoin()
                .where(category.branch.eq(categoryBranch))
                .orderBy(item.createdAt.desc())
                .fetch();
    }

    // 소분류 카테고리별로 상품 가져오기
    @Override
    public List<Item> getAllItemByCategoryName(String categoryName){
        return jpaQueryFactory
                .selectFrom(item)
                .join(item.category, category).fetchJoin()
                .where(category.name.eq(categoryName))
                .orderBy(item.createdAt.desc())
                .fetch();
    }

//    // Album 삭제
//    @Override
//    public void deleteAlbumById(Long id){
//        jpaQueryFactory
//                .delete(album)
//                .where(album.id.eq(id))
//                .execute();
//    }
//
//    // Food 삭제
//    @Override
//    public void deleteFoodById(Long id){
//        jpaQueryFactory
//                .delete(food)
//                .where(food.id.eq(id))
//                .execute();
//    }

    // item 과 album 같이 select해오기


}
