package com.example.bitcamptiger.cart.service;

import com.example.bitcamptiger.cart.dto.CartItemDTO;
import com.example.bitcamptiger.cart.entity.Cart;
import com.example.bitcamptiger.member.entity.Member;
import com.example.bitcamptiger.menu.entity.Menu;
import com.example.bitcamptiger.userOrder.dto.UserOrderDTO;

import java.util.List;

public interface CartService {


    //장바구니에 menu 추가하기
    Cart addCart(Member member, Menu menu, int cartQuantity);


    //장바구니 조회
    List<CartItemDTO> getCartList(Cart cart);

    //장바구니 menu 삭제
    void deleteCartItem(Long cartId, Long menuId);

    //장바구니 menu 전체 삭제
    void deleteCart(CartItemDTO cartItemDTO);

    List<CartItemDTO> getCartItemsByMemberId(Long id);
}
