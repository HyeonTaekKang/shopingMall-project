package com.homeshoping.homeshoping.entity.itemInfo;


import com.homeshoping.homeshoping.entity.Item.Item;
import com.homeshoping.homeshoping.request.Item.ItemCreate;
import com.homeshoping.homeshoping.request.itemInfo.ItemInfoCreate;
import com.homeshoping.homeshoping.request.itemInfo.ItemInfoEdit;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ItemInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "itemInfo_id")
    private Long id;

    @OneToOne(mappedBy = "itemInfo",fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    private Item item;

    private String manufactureCountry; // 제조국 ( 반드시 필요! )

    private String material; // 소재 ( 반드시 필요! )

    private String color; // 색상 ( 반드시 필요!)

    private String size; // 치수 ( 반드시 필요! )

    private String maker; // 제조사 ( 반드시 필요!)

    private String washingMethod; // 세탁방법 ( 반드시 필요!)

    private LocalDate yearAndMonthofManufacture; // 제조연월

    private String manager; // A/S정보 및 담당자

    private String qualityStandard; // 품질보증기준

    // DTO -> entity
    public static ItemInfo createItemInfo(ItemInfoCreate itemInfoCreate){
        ItemInfo itemInfo = new ItemInfo();

        itemInfo.manufactureCountry = itemInfoCreate.getManufactureCountry();
        itemInfo.material = itemInfo.getMaterial();
        itemInfo.color = itemInfo.getColor();
        itemInfo.size = itemInfo.getSize();
        itemInfo.maker = itemInfo.getMaker();
        itemInfo.washingMethod = itemInfo.getWashingMethod();
        itemInfo.yearAndMonthofManufacture = itemInfo.getYearAndMonthofManufacture();
        itemInfo.manager = itemInfo.getManager();
        itemInfo.qualityStandard = itemInfo.getQualityStandard();

        return itemInfo;
    }

    // ItemInfo 수정 메서드
    public void editItemInfo(ItemInfoEdit itemInfoEdit){
        this.manufactureCountry = itemInfoEdit.getManufactureCountry();
        this.material = itemInfoEdit.getMaterial();
        this.color = itemInfoEdit.getColor();
        this.size = itemInfoEdit.getSize();
        this.maker = itemInfoEdit.getMaker();
        this.washingMethod = itemInfoEdit.getWashingMethod();
        this.yearAndMonthofManufacture = itemInfoEdit.getYearAndMonthofManufacture();
        this.manager = itemInfoEdit.getManager();
        this.qualityStandard = itemInfoEdit.getQualityStandard();
    }

    @Builder
    public ItemInfo(Long id, Item item, String manufactureCountry, String material, String color, String size, String maker, String washingMethod, LocalDate yearAndMonthofManufacture, String manager, String qualityStandard) {
        this.id = id;
        this.item = item;
        this.manufactureCountry = manufactureCountry;
        this.material = material;
        this.color = color;
        this.size = size;
        this.maker = maker;
        this.washingMethod = washingMethod;
        this.yearAndMonthofManufacture = yearAndMonthofManufacture;
        this.manager = manager;
        this.qualityStandard = qualityStandard;
    }
}
