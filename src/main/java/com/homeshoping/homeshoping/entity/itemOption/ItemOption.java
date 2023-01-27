package com.homeshoping.homeshoping.entity.itemOption;

import com.homeshoping.homeshoping.entity.Item.Item;
import com.homeshoping.homeshoping.request.itemOption.ItemOptionCreate;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ItemOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "itemOption_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

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
    public ItemOption(Long id, Item item, String optionName, String optionalItem1, String optionalItem2, String optionalItem3, String optionalItem4, String optionalItem5, String optionalItem6, String optionalItem7, String optionalItem8, String optionalItem9, String optionalItem10) {
        this.id = id;
        this.item = item;
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

    // DTO -> entity
    public static ItemOption createItemOption(ItemOptionCreate itemOptionCreate){
        ItemOption itemOption = new ItemOption();

        itemOption.optionName = itemOptionCreate.getOptionName();
        itemOption.optionalItem1 = itemOptionCreate.getOptionalItem1();
        itemOption.optionalItem2 = itemOptionCreate.getOptionalItem2();
        itemOption.optionalItem3 = itemOptionCreate.getOptionalItem3();
        itemOption.optionalItem4 = itemOptionCreate.getOptionalItem4();
        itemOption.optionalItem5 = itemOptionCreate.getOptionalItem5();
        itemOption.optionalItem6 = itemOptionCreate.getOptionalItem6();
        itemOption.optionalItem7 = itemOptionCreate.getOptionalItem7();
        itemOption.optionalItem8 = itemOptionCreate.getOptionalItem8();
        itemOption.optionalItem9 = itemOptionCreate.getOptionalItem9();
        itemOption.optionalItem10 = itemOptionCreate.getOptionalItem10();

        return itemOption;
    }

    // 연관관계 메서드
    public void setItem(Item item) {
        this.item = item;
        item.getItemOptions().add(this);
    }

}
