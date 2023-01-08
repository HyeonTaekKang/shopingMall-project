package com.homeshoping.homeshoping.service.cart;

import com.homeshoping.homeshoping.Exception.ItemNotFound;
import com.homeshoping.homeshoping.Exception.MemberNotFound;
import com.homeshoping.homeshoping.entity.Item.Item;
import com.homeshoping.homeshoping.entity.cart.Cart;
import com.homeshoping.homeshoping.entity.member.Member;
import com.homeshoping.homeshoping.repository.Item.ItemRepository;
import com.homeshoping.homeshoping.repository.cart.CartRepository;
import com.homeshoping.homeshoping.repository.member.MemberRepository;
import com.homeshoping.homeshoping.request.cart.CartCreate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    @Transactional
    public void createCart(CartCreate cartCreate){

        // 회원이 주문한 아이템 찾아오기 ( item )
        Item item = itemRepository.findById(cartCreate.getItemId()).orElseThrow(ItemNotFound::new);

        // 주문한 회원의 정보 찾아오기 ( member )
        Member member = memberRepository.findById(cartCreate.getMemberId()).orElseThrow(MemberNotFound::new);

        // 장바구니 생성
        Cart cart = Cart.createCart(item, member , cartCreate.getItemCount());

        cartRepository.save(cart);

    }
}
