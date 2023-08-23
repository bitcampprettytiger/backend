package com.example.bitcamptiger.cart.repository;

import com.example.bitcamptiger.cart.entity.Cart;
import com.example.bitcamptiger.cart.entity.CartItem;
import com.example.bitcamptiger.member.entity.Member;
import com.example.bitcamptiger.vendor.entity.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long>,
        QuerydslPredicateExecutor<CartItem>, CartItemRepositoryCustom {

    //카트 아이디와 메뉴 아이디를 이용해서 메뉴가 장바구니에 들어있는지 조회
    CartItem findByCartIdAndMenuId(Long cartId, Long menuId);


    List<CartItem> findByCartMember(Member member);


    List<CartItem> findByCartMemberId(Long id);
}
