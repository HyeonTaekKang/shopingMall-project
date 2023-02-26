package com.homeshoping.homeshoping.entity.Item;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class ItemImg {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String originalFileName;

    private String storedFileName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    public static ItemImg toItemFile(Item item , String originalFileName , String storedFileName){
        ItemImg itemImg = new ItemImg();
        itemImg.originalFileName = originalFileName;
        itemImg.storedFileName = storedFileName;
        itemImg.item = item;
        return itemImg;
    }
}
