package com.homeshoping.homeshoping.entity.cart;

import com.homeshoping.homeshoping.entity.Item.Item;
import com.homeshoping.homeshoping.entity.member.Member;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private int itemCount;  // 카트에 담긴 아이템의 수량

    private LocalDateTime reg_date; // 장바구니 생성 날짜

    public static Cart createCart(Item item , Member member , int itemCount){
        Cart cart = new Cart();

        cart.setItem(item);   // cart - item 연관관계 맵핑
        cart.setMember(member);  // cart - member 연관관계 맵핑

        cart.itemCount = itemCount;
        cart.reg_date = LocalDateTime.now();

        return cart;
    }

    // == 연관관계 편의메서드
    private void setItem(Item item){
        this.item = item;
    }

    private void setMember(Member member){
        this.member = member;
    }
}
