package com.homeshoping.homeshoping.repository.Item;

import com.homeshoping.homeshoping.entity.Item.Item;

import com.homeshoping.homeshoping.entity.ItemCategory.QItemCategory;
import com.homeshoping.homeshoping.entity.itemInfo.QItemInfo;
import com.homeshoping.homeshoping.entity.itemOption.QItemOption;
import com.homeshoping.homeshoping.request.Item.ItemSearch;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.homeshoping.homeshoping.entity.Item.QItem.*;
import static com.homeshoping.homeshoping.entity.ItemCategory.QItemCategory.*;
import static com.homeshoping.homeshoping.entity.itemInfo.QItemInfo.*;
import static com.homeshoping.homeshoping.entity.itemOption.QItemOption.*;

@RequiredArgsConstructor
public class ItemRepositoryImpl implements ItemRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    // item의 id로 등록한 상품 정보 1개 가져오기
    @Override
    public Item getItem(Long itemId) {
        return jpaQueryFactory
                .selectFrom(item)
                .where(item.id.eq(itemId))
                .innerJoin(item.itemCategory, itemCategory).fetchJoin()
                .innerJoin(item.itemInfo, itemInfo).fetchJoin()
                .fetchOne();
    }

    // 등록한 상품 최신순으로 20개씩 가져오기
    @Override
    public List<Item> getLatestItems(ItemSearch itemSearch) {
        return jpaQueryFactory
                .selectFrom(item)
                .innerJoin(item.itemCategory, itemCategory).fetchJoin()
                .innerJoin(item.itemInfo, itemInfo).fetchJoin()
                .offset(itemSearch.getOffset())
                .limit(itemSearch.getLimit())
                .orderBy(item.createdAt.desc())
                .fetch();
    }
//
//    // 대분류 카테고리별로 상품 가져오기
//    @Override
//    public List<Item> getAllItemByCategoryBranch(String categoryBranch){
//        return jpaQueryFactory
//                .selectFrom(item)
//                .join(item.itemCategory, itemCategory).fetchJoin()
//                .where(itemCategory.branch.eq(categoryBranch))
//                .orderBy(item.createdAt.desc())
//                .fetch();
//    }
//
//    // 소분류 카테고리별로 상품 가져오기
//    @Override
//    public List<Item> getAllItemByCategoryName(String categoryName){
//        return jpaQueryFactory
//                .selectFrom(item)
//                .join(item.itemCategory, itemCategory).fetchJoin()
//                .where(itemCategory.name.eq(categoryName))
//                .orderBy(item.createdAt.desc())
//                .fetch();
//    }

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
