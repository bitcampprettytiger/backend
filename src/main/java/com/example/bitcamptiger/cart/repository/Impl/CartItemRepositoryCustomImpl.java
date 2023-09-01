package com.example.bitcamptiger.cart.repository.Impl;

import com.example.bitcamptiger.cart.entity.CartItem;
import com.example.bitcamptiger.cart.entity.QCart;
import com.example.bitcamptiger.cart.entity.QCartItem;
import com.example.bitcamptiger.cart.repository.CartItemRepositoryCustom;
import com.example.bitcamptiger.member.entity.Member;
import com.example.bitcamptiger.menu.entity.QMenu;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import java.util.List;

public class CartItemRepositoryCustomImpl implements CartItemRepositoryCustom {

    // querydsl 사용하기 위한 준비
    private final JPAQueryFactory queryFactory;

    //생성자 만들기
    //query를 던질 준비 완료
    public CartItemRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }


    //장바구니에 담긴 메뉴 선택 삭제
    @Override
    public CartItem findByCartItem(Member member, Long menuId) {

        BooleanBuilder builder = new BooleanBuilder();
//        builder.and(QCartItem.cartItem.cart.id.eq(cartId));
        builder.and(QCartItem.cartItem.cart.member.eq(member));
        builder.and(QCartItem.cartItem.menu.id.eq(menuId));

        CartItem cartItem = queryFactory.selectFrom(QCartItem.cartItem)
                .join(QCartItem.cartItem.cart, QCart.cart)
                .join(QCartItem.cartItem.menu, QMenu.menu)
                .where(builder)
                .fetchOne();


        return cartItem;
    }
}
