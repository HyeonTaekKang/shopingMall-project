package com.homeshoping.homeshoping.response.ItemOption;

import com.homeshoping.homeshoping.entity.itemOption.ItemOption;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ItemOptionResponse {

    private String optionName; // 옵션 이름

    private String optionalItem1; // 옵션 항목 1

    private String optionalItem2; // 옵션 항목 2

    private String optionalItem3; // 옵션 항목 3

    private String optionalItem4; // 옵션 항목 4

    private String optionalItem5; // 옵션 항목 5

    private String optionalItem6; // 옵션 항목 6

    private String optionalItem7; // 옵션 항목 7

    private String optionalItem8; // 옵션 항목 8

    private String optionalItem9; // 옵션 항목 9

    private String optionalItem10; // 옵션 항목 10

    @Builder
    public ItemOptionResponse(ItemOption itemOption) {
        this.optionName = itemOption.getOptionName();
        this.optionalItem1 = itemOption.getOptionalItem1();
        this.optionalItem2 = itemOption.getOptionalItem2();
        this.optionalItem3 = itemOption.getOptionalItem3();
        this.optionalItem4 = itemOption.getOptionalItem4();
        this.optionalItem5 = itemOption.getOptionalItem5();
        this.optionalItem6 =itemOption.getOptionalItem6();
        this.optionalItem7 = itemOption.getOptionalItem7();
        this.optionalItem8 = itemOption.getOptionalItem8();
        this.optionalItem9 = itemOption.getOptionalItem9();
        this.optionalItem10 = itemOption.getOptionalItem10();
    }
}
