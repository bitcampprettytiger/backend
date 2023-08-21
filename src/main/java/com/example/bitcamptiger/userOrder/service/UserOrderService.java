package com.example.bitcamptiger.userOrder.service;

import com.example.bitcamptiger.member.entity.Member;
import com.example.bitcamptiger.menu.entity.Menu;
import com.example.bitcamptiger.userOrder.dto.UserOrderDTO;

public interface UserOrderService {
    //주문 생성
    UserOrderDTO createOrder(UserOrderDTO orderDTO);

    //주문내역확인
    UserOrderDTO getOrderById(Long orderId);

    UserOrderDTO createOrderFromCartItem(Member member, Menu menu, Integer cartQuantity);
}
