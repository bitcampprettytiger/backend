package com.example.bitcamptiger.order.service;

import com.example.bitcamptiger.order.dto.OrderDTO;
import com.example.bitcamptiger.order.entity.Orders;

import java.util.List;

public interface OrderService {
    //주문 생성
    Orders createOrder(OrderDTO orderDTO);

    //주문내역확인
    List<OrderDTO> getOrderList(Long memberId);

    //특정 주문내역 상세 확인
    OrderDTO getOrderDetail(Long orderId);


//    void createOrderFromCartItem(Member member, List<OrderMenu> orderedMenuSet);
}
