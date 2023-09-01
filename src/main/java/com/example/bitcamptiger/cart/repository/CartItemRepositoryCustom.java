package com.example.bitcamptiger.cart.repository;

import com.example.bitcamptiger.cart.entity.CartItem;
import com.example.bitcamptiger.member.entity.Member;

import java.util.List;

public interface CartItemRepositoryCustom {

//    CartItem findByCartItem(Long cartId, Long menuId);

    CartItem findByCartItem(Member member, Long menuId);
}
