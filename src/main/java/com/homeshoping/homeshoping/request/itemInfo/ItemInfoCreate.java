package com.homeshoping.homeshoping.request.itemInfo;

import com.homeshoping.homeshoping.entity.ItemCategory.ItemCategory;
import com.homeshoping.homeshoping.entity.itemInfo.ItemInfo;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class ItemInfoCreate {

    private String manufactureCountry; // 제조국 ( 반드시 필요! )

    private String material; // 소재 ( 반드시 필요! )

    private String color; // 색상 ( 반드시 필요!)

    private String size; // 치수 ( 반드시 필요! )

    private String maker; // 제조사 ( 반드시 필요!)

    private String washingMethod; // 세탁방법 ( 반드시 필요!)

    private LocalDate yearAndMonthofManufacture; // 제조연월

    private String manager; // A/S정보 및 담당자

    private String qualityStandard; // 품질보증기준

    @Builder
    public ItemInfoCreate(String manufactureCountry, String material, String color, String size, String maker, String washingMethod, LocalDate yearAndMonthofManufacture, String manager, String qualityStandard) {
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

    // DTO -> entity ( itemInfoCreate -> itemInfo )
    public static ItemInfo toEntity(ItemInfoCreate itemInfoCreate){
        return ItemInfo.builder()
                .manufactureCountry(itemInfoCreate.getManufactureCountry())
                .material(itemInfoCreate.getMaterial())
                .color(itemInfoCreate.getColor())
                .size(itemInfoCreate.getSize())
                .maker(itemInfoCreate.getMaker())
                .washingMethod(itemInfoCreate.getWashingMethod())
                .yearAndMonthofManufacture(itemInfoCreate.getYearAndMonthofManufacture())
                .manager(itemInfoCreate.getManager())
                .qualityStandard(itemInfoCreate.getQualityStandard())
                .build();
    }
    @Override
    public String toString() {
        return "ItemInfoCreate{" +
                "manufactureCountry='" + manufactureCountry + '\'' +
                ", material='" + material + '\'' +
                ", color='" + color + '\'' +
                ", size='" + size + '\'' +
                ", maker='" + maker + '\'' +
                ", washingMethod='" + washingMethod + '\'' +
                ", yearAndMonthofManufacture=" + yearAndMonthofManufacture +
                ", manager='" + manager + '\'' +
                ", qualityStandard='" + qualityStandard + '\'' +
                '}';
    }
}
