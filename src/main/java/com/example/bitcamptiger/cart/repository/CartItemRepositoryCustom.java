package com.example.bitcamptiger.cart.repository;

import com.example.bitcamptiger.cart.entity.CartItem;

import java.util.List;

public interface CartItemRepositoryCustom {

    CartItem findByCartItem(Long cartId, Long menuId);
}
