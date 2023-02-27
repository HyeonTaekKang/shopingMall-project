package com.homeshoping.homeshoping.repository.ItemOption;

import com.homeshoping.homeshoping.entity.itemOption.ItemOption;
import com.homeshoping.homeshoping.entity.itemOption.QItemOption;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.homeshoping.homeshoping.entity.itemOption.QItemOption.*;

@RequiredArgsConstructor
public class ItemOptionRepositoryImpl implements ItemOptionRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<ItemOption> getItemOptions(Long itemId) {
        return jpaQueryFactory.selectFrom(itemOption)
                .where(itemOption.item.id.eq(itemId))
                .fetch();
    }
}
