package com.homeshoping.homeshoping.service.order;

import com.homeshoping.homeshoping.Exception.MemberNotFound;
import com.homeshoping.homeshoping.entity.member.Member;
import com.homeshoping.homeshoping.entity.order.Order;
import com.homeshoping.homeshoping.repository.member.MemberRepository;
import com.homeshoping.homeshoping.repository.order.OrderRepository;
import com.homeshoping.homeshoping.request.member.MemberCreate;
import com.homeshoping.homeshoping.request.order.OrderCreate;
import com.homeshoping.homeshoping.response.order.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void create(Long memberId){

        // 회원정보
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new MemberNotFound());

        // order 생성
        Order order = Order.createOrder(member);

        // 생성한 order 저장
        orderRepository.save(order);
    }

}
