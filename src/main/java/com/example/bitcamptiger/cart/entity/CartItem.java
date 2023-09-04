package com.example.bitcamptiger.cart.entity;

import com.example.bitcamptiger.menu.entity.Menu;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_item_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    @JsonBackReference  //순환참조 문제를 해결하기 위해 참조속성 명시
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "menu_id")
    private Menu menu;

    //카드에 담긴 메뉴의 수량
    @Column
    private int cartQuantity;


    //장바구니에 담을 상품 엔티티를 생성하는 메소드
    //장바구니에 담을 수량을 증가시키는 메소드
    public static CartItem createCartItem(Cart cart, Menu menu, int cartQuantity){
        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setMenu(menu);
        cartItem.setCartQuantity(cartQuantity);
        return cartItem;
    }

    //장바구니에 기존에 담겨있는 상품인데, 해당 상품을 추가로 담을 때
    public void addCount(int cartQuantity){
        this.cartQuantity += cartQuantity;
    }

    public void updateCount(int cartQuantity){
        this.cartQuantity = cartQuantity;
    }

}
