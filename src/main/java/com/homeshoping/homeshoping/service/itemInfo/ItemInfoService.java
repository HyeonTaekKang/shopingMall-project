package com.homeshoping.homeshoping.service.itemInfo;

import com.homeshoping.homeshoping.entity.itemInfo.ItemInfo;
import com.homeshoping.homeshoping.repository.itemInfo.ItemInfoRepository;
import com.homeshoping.homeshoping.request.itemInfo.ItemInfoCreate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemInfoService {

    private final ItemInfoRepository itemInfoRepository;

    // 아이템 info 생성
    @Transactional
    public void createItemInfo(ItemInfoCreate itemInfoCreate){

        // DTO -> entity
        ItemInfo itemInfo = itemInfoCreate.toEntity(itemInfoCreate);

        itemInfoRepository.save(itemInfo);
    }
}
