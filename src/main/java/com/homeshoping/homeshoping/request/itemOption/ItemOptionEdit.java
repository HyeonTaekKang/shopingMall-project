package com.homeshoping.homeshoping.request.itemOption;

import com.homeshoping.homeshoping.entity.itemOption.ItemOption;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ItemOptionEdit {

    private Long id;

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
    public ItemOptionEdit(Long id, String optionName, String optionalItem1, String optionalItem2, String optionalItem3, String optionalItem4, String optionalItem5, String optionalItem6, String optionalItem7, String optionalItem8, String optionalItem9, String optionalItem10) {
        this.id = id;
        this.optionName = optionName;
        this.optionalItem1 = optionalItem1;
        this.optionalItem2 = optionalItem2;
        this.optionalItem3 = optionalItem3;
        this.optionalItem4 = optionalItem4;
        this.optionalItem5 = optionalItem5;
        this.optionalItem6 = optionalItem6;
        this.optionalItem7 = optionalItem7;
        this.optionalItem8 = optionalItem8;
        this.optionalItem9 = optionalItem9;
        this.optionalItem10 = optionalItem10;
    }


    public ItemOption toEntity(){
        return ItemOption.builder()
                .optionName(optionName)
                .optionalItem1(optionalItem1)
                .optionalItem2(optionalItem2)
                .optionalItem3(optionalItem3)
                .optionalItem4(optionalItem4)
                .optionalItem5(optionalItem5)
                .optionalItem6(optionalItem6)
                .optionalItem7(optionalItem7)
                .optionalItem8(optionalItem8)
                .optionalItem9(optionalItem9)
                .optionalItem10(optionalItem10)
                .build();
    }
}
