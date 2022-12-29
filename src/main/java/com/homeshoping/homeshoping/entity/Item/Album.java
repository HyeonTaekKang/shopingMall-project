package com.homeshoping.homeshoping.entity.Item;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Album  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "album_id")
    private Long id;

    @OneToOne(mappedBy = "album",fetch = FetchType.LAZY)
    private Item item;

    @Column(nullable = false)
    private String artist; // 아티스트

    @Builder
    public Album(Long id, Item item, String artist) {
        this.id = id;
        this.item = item;
        this.artist = artist;
    }

    // == 연관관계 편의 메서드 ==
    // ( Item - Album )
    // ( Item 클래스의 멤버변수인 albumList에 album 객체(자기자신)를 setting 하는 메서드 )
//    public void setItem(Item item){
//        this.item = item;
//        item.getAlbumList().add(this);
//    }


}
