package com.example.bitcamptiger.cart.service;

import com.example.bitcamptiger.cart.dto.CartDTO;
import com.example.bitcamptiger.cart.dto.CartItemDTO;
import com.example.bitcamptiger.member.entity.CustomUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.util.List;

public interface CartService {
    List<CartDTO> getCartList(Long memberId);

    void insertCart(CartItemDTO cartItemDTO);
}
