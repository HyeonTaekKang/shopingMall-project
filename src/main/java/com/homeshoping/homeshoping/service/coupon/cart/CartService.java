package com.homeshoping.homeshoping.service.coupon.cart;

import com.homeshoping.homeshoping.Exception.ItemNotFound;
import com.homeshoping.homeshoping.Exception.MemberNotFound;
import com.homeshoping.homeshoping.entity.Item.Item;
import com.homeshoping.homeshoping.entity.cart.Cart;
import com.homeshoping.homeshoping.entity.member.Member;
import com.homeshoping.homeshoping.repository.Item.ItemRepository;
import com.homeshoping.homeshoping.repository.cart.CartRepository;
import com.homeshoping.homeshoping.repository.member.MemberRepository;
import com.homeshoping.homeshoping.request.cart.CartCreate;
import com.homeshoping.homeshoping.response.cart.CartResponse;
import com.homeshoping.homeshoping.response.cart.ListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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

    // cart 에 담긴 아이템들 가져오기
    public ListResponse getItemsInCart(Long memberId){

        // cart 에 담긴 아이템들을 리스트에 담아서 가져옴.
        List<Cart> cartItems = cartRepository.getCartItems(memberId);

        // 엔티티 -> 리스트<DTO>
        List<CartResponse> cartItemsDTO = cartItems.stream().map(i -> new CartResponse(i)).collect(Collectors.toList());

        // 리스트<DTO> 를 객체에 담아서 리턴.
        return new ListResponse(cartItemsDTO);
    }
}
