package com.homeshoping.homeshoping.repository.Item;

import com.homeshoping.homeshoping.entity.Item.Item;
import com.homeshoping.homeshoping.request.Item.ItemCreate;
import com.homeshoping.homeshoping.request.Item.ItemSearch;
import com.homeshoping.homeshoping.response.Item.ItemResponse;

import java.util.List;
import java.util.Optional;

public interface ItemRepositoryCustom {

    // 등록한 상품 최신순으로 20개씩 가져오기
    List<Item> getAllRegistratedItemWithPaging(ItemSearch itemSearch);

    // 매개변수로 받아온 대분류 카테고리에 포함되어있는 상품을 모두 가져오는 메서드
    List<Item> getAllItemByCategoryBranch(String categoryBranch);

    // 매개변수로 받아온 소분류 카테고리에 포함되어있는 상품을 모두 가져오는 메서드
    List<Item> getAllItemByCategoryName(String categoryName);

//    // Album 삭제
//    public void deleteAlbumById(Long id);
//
//    // Food 삭제
//    public void deleteFoodById(Long id);

    // item 과 album 같이 가져오기

}
