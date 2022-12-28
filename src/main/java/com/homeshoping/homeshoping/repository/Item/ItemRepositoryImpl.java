package com.homeshoping.homeshoping.repository.Item;

import com.homeshoping.homeshoping.entity.Item.Item;
import com.homeshoping.homeshoping.entity.Item.QAlbum;
import com.homeshoping.homeshoping.entity.Item.QFood;
import com.homeshoping.homeshoping.request.Item.ItemCreate;
import com.homeshoping.homeshoping.request.Item.ItemSearch;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

import static com.homeshoping.homeshoping.entity.Item.QAlbum.*;
import static com.homeshoping.homeshoping.entity.Item.QFood.*;
import static com.homeshoping.homeshoping.entity.Item.QItem.*;

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
                .orderBy(item.date.desc())
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
    @Override
    public Item selectItemWithAlbum(Long itemId){
        return jpaQueryFactory
                .selectFrom(item)
                .innerJoin(item.album,album).fetchJoin()
                .fetchOne();
    }

}
