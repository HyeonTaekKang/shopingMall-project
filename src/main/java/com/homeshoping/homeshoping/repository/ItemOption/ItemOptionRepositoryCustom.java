package com.homeshoping.homeshoping.repository.ItemOption;

import com.homeshoping.homeshoping.entity.itemOption.ItemOption;

import java.util.List;

public interface ItemOptionRepositoryCustom {

    // 상품의 id로 상품의 옵션들을 가져오는 메서드.
    List<ItemOption> getItemOptions(Long itemId);
}
