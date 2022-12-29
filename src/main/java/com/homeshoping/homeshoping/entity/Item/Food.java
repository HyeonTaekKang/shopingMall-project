package com.homeshoping.homeshoping.entity.Item;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "food_id")
    private Long id;

    @OneToOne(mappedBy = "food",fetch = FetchType.LAZY)
    private Item item;

    @Column(nullable = false)
    private String madeIn; // 원산지

    @Column(nullable = false)
    private String manufacturer; // 생산자 or 제조사

    @Column(nullable = false)
    private LocalDateTime expirationDate; // 유통기한

    @Builder
    public Food(Long id, Item item, String madeIn, String manufacturer, LocalDateTime expirationDate) {
        this.id = id;
        this.item = item;
        this.madeIn = madeIn;
        this.manufacturer = manufacturer;
        this.expirationDate = expirationDate;
    }

    // == 연관관계 편의 메서드 ==
    // ( Item - Food )
//    // ( Item 클래스의 멤버변수인 albumList에 album 객체(자기자신)를 setting 하는 메서드 )
//    public void setItem(Item item){
//        this.item = item;
//        item.getFoodList().add(this);
//    }
}
