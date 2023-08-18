package com.example.bitcamptiger.cart.repository;

import com.example.bitcamptiger.cart.entity.CartItem;
import com.example.bitcamptiger.member.entity.Member;
import com.example.bitcamptiger.menu.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {


    CartItem findByMenuAndCart_Member(Menu menu, Member member);
}
