package com.homeshoping.homeshoping.repository.order;

import com.homeshoping.homeshoping.entity.order.Order;
import com.homeshoping.homeshoping.response.order.OrderResponse;

public interface OrderRepositoryCustom {

    // 해당 회원이 주문한 주문의 정보를 최신순으로 가져오는 메서드
    public OrderResponse getOrder(Long memberId);
}
