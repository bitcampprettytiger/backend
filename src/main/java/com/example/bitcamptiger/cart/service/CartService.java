package com.example.bitcamptiger.cart.service;

import com.example.bitcamptiger.cart.dto.CartDTO;

import java.util.List;

public interface CartService {
    List<CartDTO> getCartList(Long memberId);

    void insertCart(CartDTO cartDTO);
}
