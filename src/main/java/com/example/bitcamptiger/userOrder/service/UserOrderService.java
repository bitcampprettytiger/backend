package com.example.bitcamptiger.userOrder.service;

import com.example.bitcamptiger.userOrder.dto.UserOrderDTO;

public interface UserOrderService {
    //주문 생성
    UserOrderDTO createOrder(UserOrderDTO orderDTO);

    //주문내역확인
    UserOrderDTO getOrderById(Long orderId);
}
