package com.homeshoping.homeshoping.response.ItemInfo;

import com.homeshoping.homeshoping.entity.itemInfo.ItemInfo;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class ItemInfoResponse {

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
    public ItemInfoResponse(String manufactureCountry, String material, String color, String size, String maker, String washingMethod, LocalDate yearAndMonthofManufacture, String manager, String qualityStandard) {
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

    // 엔티티 -> DTO
    public static ItemInfoResponse toDTO(ItemInfo itemInfo){
        return ItemInfoResponse.builder()
                .manufactureCountry(itemInfo.getManufactureCountry())
                .color(itemInfo.getColor())
                .size(itemInfo.getSize())
                .maker(itemInfo.getMaker())
                .washingMethod(itemInfo.getWashingMethod())
                .yearAndMonthofManufacture((itemInfo.getYearAndMonthofManufacture()))
                .manager(itemInfo.getManager())
                .qualityStandard(itemInfo.getQualityStandard())
                .build();
    }


}
